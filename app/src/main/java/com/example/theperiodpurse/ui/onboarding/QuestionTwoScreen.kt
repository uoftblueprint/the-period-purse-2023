package com.example.theperiodpurse.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.R
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.theperiodpurse.data.OnboardUIState
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun QuestionTwoScreen(
    onboardUiState: OnboardUIState,
    onNextButtonClicked: () -> Unit,
    onSelectionChanged: (String) -> Unit = {},
    options: List<String>,
    modifier: Modifier = Modifier
) {
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mContext = LocalContext.current

    val mDate = remember { mutableStateOf("Select Date") }
    val mDateTo = remember { mutableStateOf("") }
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    var entered by remember { mutableStateOf(false) }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = setDateTo(mDayOfMonth, mMonth, mYear, 0)
            mDateTo.value = setDateTo(mDayOfMonth, mMonth, mYear, onboardUiState.days)
        }, mYear, mMonth, mDay
    )




    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.question_two),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(R.string.description_one),
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))


        Divider(thickness = 1.dp, modifier = modifier.padding(bottom = 16.dp))
        Button(onClick = {
            mDatePickerDialog.show()
            entered = true
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White) ) {
            Text(text = "${mDate.value}", color = Color.Black)

        }

        Spacer(Modifier.height(24.dp))
        Divider(thickness = 1.dp, modifier = modifier.padding(bottom = 16.dp))




        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onNextButtonClicked,
                modifier = modifier.weight(1f),

            ) {
                Text(stringResource(R.string.skip))
            }
            Button(
                onClick = onNextButtonClicked,
                enabled = entered,
                modifier = modifier.weight(1f),
            ) {
                Text(stringResource(R.string.next))
                onSelectionChanged(mDate.value+"|"+mDateTo.value)
                // ADD SIGN IN FUNCTION CALL HERE

            }

        }

    }

}

fun setDateTo(day: Int, month: Int, year: Int, range: Int): String {
    val date = Calendar.getInstance()
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    date.set(year, month, day);
    date.add(Calendar.DATE, range)
    return formatter.format(date.time)

}


