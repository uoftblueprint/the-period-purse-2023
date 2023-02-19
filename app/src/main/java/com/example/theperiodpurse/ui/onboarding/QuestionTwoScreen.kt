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
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.theperiodpurse.data.OnboardUIState
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
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

    val mDate = remember { mutableStateOf("Choose date") }
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

    mDatePickerDialog.getDatePicker().setMaxDate(Date().getTime())


    backbutton1()

    drawconcave()

    progress1()




    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(85.dp))

        Image(
            painter = painterResource(R.drawable.flow_with_heart),
            contentDescription = null,
            modifier = Modifier.size(200 .dp).padding(top=40.dp),
        )

        Spacer(modifier = Modifier.height(80.dp))



        Text(
            text = stringResource(R.string.question_two),
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.description_two),
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))



        Button(onClick = {
            mDatePickerDialog.show()
            entered = true
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = modifier
                .width(175.dp)) {

            if (mDate.value != "Choose date"){

                Row (modifier = Modifier
                    .padding(
                        start = 12.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Text(text = "${mDate.value}", color = Color.Black,  fontSize = 18.sp)
                }

            }
            else {

                Row (modifier = Modifier
                    .padding(
                        start = 12.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {

                    Text(
                        text = "${mDate.value}",
                        color = Color.Black,
                        textAlign = TextAlign.Center,

                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Image(
                        painter = painterResource(R.drawable.onboard_calendar),
                        contentDescription = null,
                    )

                }

            }



        }

        Spacer(Modifier.height(24.dp))





        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = onNextButtonClicked,
                modifier = modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)

            ) {
                Text(stringResource(R.string.skip),color = Color(97, 153, 154), fontSize = 20.sp)
            }
            Button(
                onClick = onNextButtonClicked,
                enabled = entered,
                modifier = modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))
            ) {
                Text(stringResource(R.string.next),color = Color.White, fontSize = 20.sp)
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

@Composable
fun progress1(){
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier
            .padding(PaddingValues(top = 175.dp, start = 10.dp))
            .width(configuration.screenWidthDp.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .padding(PaddingValues(top = 200.dp))


        ) {
            Canvas(
                modifier = Modifier
                    .size(20.dp)
                    .padding(PaddingValues(top = 20.dp))
            ) {

                drawRoundRect(
                    color = Color(android.graphics.Color.rgb(142, 212, 193)).copy(alpha = 0.5f),
                    cornerRadius = CornerRadius(60f, 60f),
                    size = Size(width = 10.dp.toPx(), height = 10.dp.toPx())

                )


            }
            Canvas(
                modifier = Modifier
                    .size(40.dp)
                    .padding(PaddingValues(top = 20.dp))
            ) {

                drawRoundRect(
                    color = Color(android.graphics.Color.rgb(142, 212, 193)),
                    cornerRadius = CornerRadius(60f, 60f),
                    size = Size(width = 30.dp.toPx(), height =10.dp.toPx())

                )


            }
            Canvas(
                modifier = Modifier
                    .size(30.dp)
                    .padding(PaddingValues(top = 20.dp))
            ) {

                drawRoundRect(
                    color = Color(android.graphics.Color.rgb(142, 212, 193)).copy(alpha = 0.5f),
                    cornerRadius = CornerRadius(60f, 60f),
                    size = Size(width = 10.dp.toPx(), height = 10.dp.toPx())

                )


            }


        }
    }
}

