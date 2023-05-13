package com.tpp.theperiodpurse.ui.viewmodel
import android.accounts.Account
import android.content.Context
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.ByteArrayContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.*
import com.tpp.theperiodpurse.data.entity.Date
import com.tpp.theperiodpurse.data.entity.User
import com.tpp.theperiodpurse.data.model.Symptom
import com.tpp.theperiodpurse.data.repository.DateRepository
import com.tpp.theperiodpurse.data.repository.UserRepository
import com.tpp.theperiodpurse.data.OnboardUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val dateRepository: DateRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(OnboardUIState())
    val uiState: StateFlow<OnboardUIState> = _uiState.asStateFlow()

    var isOnboarded: LiveData<Boolean?> = userRepository.isOnboarded
    var isDeleted: MutableLiveData<Boolean?> = MutableLiveData(null)
    var isDrive: MutableLiveData<FileList?> = MutableLiveData(null)
    var isDownloaded: MutableLiveData<Boolean?> = MutableLiveData(null)
    var isBackedUp: MutableLiveData<Boolean?> = MutableLiveData(null)

    fun checkGoogleLogin(context: Context): Boolean{
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return (account!=null)
    }

    fun checkOnboardedStatus() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.isOnboarded()
                isOnboarded = userRepository.isOnboarded
            }
        }
    }
    fun checkDeletedStatus(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val instance = ApplicationRoomDatabase.getDatabase(context)
                instance.clearAllTables()
                instance.close()
                isDeleted.postValue(true)
            }
        }
    }


    fun checkGoogleDrive(account: Account, context: Context, ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val credential = GoogleAccountCredential.usingOAuth2(
                    context, listOf(DriveScopes.DRIVE_FILE,
                        DriveScopes.DRIVE_APPDATA)
                )
                credential.selectedAccount = account
                val drive = Drive
                    .Builder(
                        AndroidHttp.newCompatibleTransport(),
                        JacksonFactory.getDefaultInstance(),
                        credential
                    )
                    .setApplicationName(context.getString(R.string.app_name))
                    .build()


                isDrive.postValue(drive.files().list()
                    .setQ("name = 'user_database.db' and trashed = false")
                    .setSpaces("appDataFolder").execute())
            }
        }
    }

    fun downloadBackup(account: Account, context: Context){
            viewModelScope.launch {
                withContext(Dispatchers.IO) {

                    val credential = GoogleAccountCredential.usingOAuth2(
                        context,
                        listOf(
                            DriveScopes.DRIVE_FILE,
                            DriveScopes.DRIVE_APPDATA
                        )
                    )
                    credential.selectedAccount = account
                    val drive = Drive
                        .Builder(
                            AndroidHttp.newCompatibleTransport(),
                            JacksonFactory.getDefaultInstance(),
                            credential
                        )
                        .setApplicationName(context.getString(R.string.app_name))
                        .build()
                    val fileList = drive.files().list()
                        .setQ("name = 'user_database.db' and trashed = false")
                        .setSpaces("appDataFolder").execute()

                    val fileId = fileList.files[0].id

                    val outputStream = ByteArrayOutputStream()

                    drive.files().get(fileId).executeMediaAndDownloadTo(outputStream)

                    val data = outputStream.toByteArray()

                    val dbFilePath = context.getDatabasePath("user_database.db").path

                    FileOutputStream(dbFilePath).apply {
                        write(data)
                        flush()
                        close()
                    }

                    val instance = ApplicationRoomDatabase.getDatabase(context)
                    instance.close()



                    isDownloaded.postValue(true)
                }
            }
    }

    fun backupDatabase(account: Account, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val credential = GoogleAccountCredential.usingOAuth2(
                    context,
                    listOf(DriveScopes.DRIVE_APPDATA,
                        DriveScopes.DRIVE_FILE)
                )
                credential.selectedAccount = account
                val drive = Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential
                ).setApplicationName(context.getString(R.string.app_name)).build()

               val fileList = drive.files().list()
                   .setQ("name = 'user_database.db' and trashed = false")
                   .setSpaces("appDataFolder").execute()
                if (fileList.files.isNotEmpty()) {
                    val fileId = fileList.files[0].id
                    drive.files().delete(fileId).execute()
                } else {
                    null
                }
                val instance = ApplicationRoomDatabase.getDatabase(context)
                instance.close()


                val dbFile = java.io.File(context.getDatabasePath("user_database.db").absolutePath)
                val outputStream = ByteArrayOutputStream()
                val inputStream = FileInputStream(dbFile)
                inputStream.copyTo(outputStream)
                val metadata = File()
                        .setParents(listOf("appDataFolder"))
                        .setMimeType("application/x-sqlite3")
                        .setName("user_database.db")
                drive.files().create(metadata, ByteArrayContent(null, outputStream.toByteArray()))
                        .setFields("id")
                        .execute()

                ApplicationRoomDatabase.getDatabase(context)

                inputStream.close()
                outputStream.close()

                isBackedUp.postValue(true)
            }
        }
    }





    /**
     * Set the quantity [averageDays] for average period length for this onboarding session
     */
    fun setQuantity(numberDays: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                days = numberDays,

                )
        }
    }



    /**
     * Set the [logSymptoms] to track for onboarding session.
     */
    fun setSymptoms(logSymptoms: String) {
        var listOfSymptoms: MutableList<String> = mutableListOf()
        var tempList = logSymptoms.split("|")
        tempList.forEach { option ->
            if (option != "") {
                listOfSymptoms.add(option)
            }
        }

        _uiState.update { currentState ->
            currentState.copy(symptomsOptions = listOfSymptoms)
        }
    }

    /**
     * Set the [lastDate] of last period for current onboarding session
     */
    fun setDate(startDate: String) {
        var dates= startDate.split("|")
        _uiState.update { currentState ->
            currentState.copy(
                date = dates[0] + " to " + dates[1],
            )
        }
    }


    private fun createUser(symptomsToTrack: ArrayList<Symptom>, periodHistory: ArrayList<Date>,
                           averagePeriodLength: Int, averageCycleLength: Int,
                           daysSinceLastPeriod: Int): User {
        return User(
            symptomsToTrack = symptomsToTrack,
            periodHistory = periodHistory,
            averagePeriodLength = averagePeriodLength,
            averageCycleLength = averageCycleLength,
            daysSinceLastPeriod = daysSinceLastPeriod
        )
    }

    private fun saveUser(user: User) {
        userRepository.addUser(user)
    }

    fun addNewUser(symptomsToTrack: ArrayList<Symptom>, periodHistory: ArrayList<Date>,
                   averagePeriodLength: Int, averageCycleLength: Int, daysSinceLastPeriod: Int, context: Context) {
        saveUser(createUser(symptomsToTrack, periodHistory, averagePeriodLength, averageCycleLength,
            daysSinceLastPeriod))
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                ApplicationRoomDatabase.getDatabase(context)
            }
            val user = withContext(Dispatchers.Main) { userRepository.getUser(1) }
            _uiState.value = _uiState.value.copy(user = user)
        }
    }



}