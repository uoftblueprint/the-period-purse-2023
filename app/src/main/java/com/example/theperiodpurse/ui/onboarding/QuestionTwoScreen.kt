package com.example.theperiodpurse.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.R
import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
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

    val configuration = LocalConfiguration.current

    val screenwidth = configuration.screenWidthDp;

    val screenheight = configuration.screenHeightDp;

    mDatePickerDialog.getDatePicker().setMaxDate(Date().getTime())

    backbutton1(navigateUp)

    Column(
        modifier = Modifier.fillMaxHeight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val ratio = 0.5
        val ratioimage = 0.17
        val height = (screenheight * ratio)
        val imageheight = (screenheight * ratioimage)
        Box(
            modifier = Modifier
                .width(screenwidth.dp)
                .height(height.dp)

        )
        {
            background_shape()

            Image(
                painter = painterResource(R.drawable.flow_with_heart),
                contentDescription = null,
                modifier = Modifier
                    .size(imageheight.dp)
                    .align(Alignment.Center),
            )
            val barheight1 = (screenheight * (0.09))

            Image(
                painter = painterResource(R.drawable.onboard_bar2),
                contentDescription = null,
                modifier = Modifier
                    .size(barheight1.dp)
                    .align(Alignment.BottomCenter),
            )

        }

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
                .width((screenwidth * (0.6)).dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height((screenheight * (0.02)).dp))



        Button(
            onClick = {
                mDatePickerDialog.show()
                entered = true
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = modifier
                .width(175.dp),
            shape = RoundedCornerShape(20)
        ) {

            if (mDate.value != "Choose date") {

                Row(
                    modifier = Modifier
                        .padding(
                            start = 12.dp,
                            end = 16.dp,
                            top = 12.dp,
                            bottom = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "${mDate.value}", color = Color.Black, fontSize = 18.sp)
                }

            } else {

                Row(
                    modifier = Modifier
                        .padding(
                            start = 12.dp,
                            end = 16.dp,
                            top = 12.dp,
                            bottom = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

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

        Spacer(Modifier.height((screenwidth * (0.02)).dp))





        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = onNextButtonClicked,
                modifier = modifier
                    .padding(start = (screenwidth * (0.1)).dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)

            ) {
                Text(stringResource(R.string.skip), color = Color(97, 153, 154), fontSize = 20.sp)
            }
            Button(
                onClick = onNextButtonClicked,
                enabled = entered,
                modifier = modifier
                    .padding(end = (screenwidth * (0.1)).dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))
            ) {
                Text(stringResource(R.string.next), color = Color.White, fontSize = 20.sp)
                onSelectionChanged(mDate.value + "|" + mDateTo.value)
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


