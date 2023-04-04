package com.tpp.theperiodpurse.ui.onboarding
import android.accounts.Account
import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.http.ByteArrayContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.*
import com.tpp.theperiodpurse.data.Date
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val dateRepository: DateRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(OnboardUIState(dateOptions = dateOptions()))
    val uiState: StateFlow<OnboardUIState> = _uiState.asStateFlow()

    var isOnboarded: LiveData<Boolean?> = userRepository.isOnboarded
    var isDeleted: LiveData<Boolean?> = userRepository.isDeleted
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
                async {
                    userRepository.isOnboarded()
                    isOnboarded = userRepository.isOnboarded
                }

            }
        }
    }
    fun checkDeletedStatus(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                async {
                    userRepository.isDeleted(context = context)
                    isDeleted = userRepository.isDeleted
                }

            }
        }
    }


    fun checkGoogleDrive(account: Account, context: Context, ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                async {
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

                    val query = "mimeType='application/x-sqlite3' and trashed=false and 'appDataFolder' in parents and name='user_database.db'"
                    isDrive.postValue(drive.files().list().setQ(query).execute())

                }

            }
        }
    }

    fun downloadBackup(account: Account, context: Context){
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                        var instance = ApplicationRoomDatabase.getDatabase(context)
                        instance.close()
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

                        val query =
                            "mimeType='application/x-sqlite3' and trashed=false and 'appDataFolder' in parents and name='user_database.db'"
                        val fileList = drive.files().list().setQ(query).execute()

                        val fileId = fileList.files[0].id
                        val outputStream =
                            FileOutputStream(context.getDatabasePath("user_database.db"))
                        drive.files().get(fileId).executeMediaAndDownloadTo(outputStream)
                        ApplicationRoomDatabase.getDatabase(context)
                        isDownloaded.postValue(true)
                }
            }
    }

    fun backupDatabase(account: Account, context: Context, ) {
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
//                val metadata = File()
//                    .setParents(listOf("appDataFolder"))
//                    .setMimeType("text/plain")
//                    .setName("example.txt")
//                val emptyContent = ByteArrayContent("text/plain", byteArrayOf())
//                drive.files().create(metadata, emptyContent)
//                    .setFields("id")
//                    .execute()

                val query =
                    "mimeType='application/x-sqlite3' and trashed=false and 'appDataFolder' in parents and name='user_database.db'"
                val fileList = drive.files().list().setQ(query).execute()
                val fileId = if (fileList.files.isNotEmpty()) {
                    fileList.files[0].id
                } else {
                    null
                }

                val outputStream = ByteArrayOutputStream()
                val file = ApplicationRoomDatabase.DatabaseToFile(context)
                val inputStream = FileInputStream(file)

                inputStream.copyTo(outputStream)

                if (fileId != null) {
                    drive.files().update(fileId, null, ByteArrayContent(null, outputStream.toByteArray())).execute()
                } else {
                    val metadata = File()
                        .setParents(listOf("appDataFolder"))
                        .setMimeType("application/x-sqlite3")
                        .setName("user_database.db")
                    drive.files().create(metadata, ByteArrayContent(null, outputStream.toByteArray()))
                        .setFields("id")
                        .execute()

                }
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

    /**
     * Reset the onboard state
     */
    fun resetOrder() {
        _uiState.value = OnboardUIState(dateOptions = dateOptions())
    }


    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */

    private fun dateOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 30 previous dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, -1)
        }
        dateOptions.reverse()
        return dateOptions
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
                   averagePeriodLength: Int, averageCycleLength: Int, daysSinceLastPeriod: Int) {
        saveUser(createUser(symptomsToTrack, periodHistory, averagePeriodLength, averageCycleLength,
            daysSinceLastPeriod))
        viewModelScope.launch {
            val user = withContext(Dispatchers.Main) { userRepository.getUser(1) }
            _uiState.value = _uiState.value.copy(user = user)
        }
    }

    private fun createDate(date: java.util.Date, flow: FlowSeverity, mood: Mood,
                           exerciseLength: Duration?, exerciseType: Exercise,
                           crampSeverity: CrampSeverity, sleep: Duration?): Date {
        return Date(
            date = date,
            flow = flow,
            mood = mood,
            exerciseLength = exerciseLength,
            exerciseType = exerciseType,
            crampSeverity = crampSeverity,
            sleep = sleep,
            notes = ""
        )
    }

    private fun saveDate(date: Date) {
        dateRepository.addDate(date)
    }

    fun addNewDate(date: java.util.Date, flow: FlowSeverity, mood: Mood,
                   exerciseLength: Duration?, exerciseType: Exercise,
                   crampSeverity: CrampSeverity, sleep: Duration?) {
        saveDate(createDate(date, flow, mood, exerciseLength, exerciseType, crampSeverity, sleep))
    }


}