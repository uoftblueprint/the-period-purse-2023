package com.tpp.theperiodpurse.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.tpp.theperiodpurse.OnboardingScreen
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.state.OnboardUIState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuestionTwoScreen(
    onboardUiState: OnboardUIState,
    onSelectionChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    navController: NavHostController
) {
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mContext = LocalContext.current
    var entered by rememberSaveable { mutableStateOf(false) }
    val mDate = rememberSaveable { mutableStateOf("Choose date") }
    val mDateTo = rememberSaveable { mutableStateOf("") }
    if (onboardUiState.date.contains("/")){
        mDate.value = onboardUiState.date.split(" to ")[0]
        mDateTo.value = onboardUiState.date.split(" to ")[1]
        entered = true
    }
    val configuration = LocalConfiguration.current
    val screenwidth = configuration.screenWidthDp;
    val screenheight = configuration.screenHeightDp;
    backbutton(navigateUp, canNavigateBack)
    Box(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()) {
        Column(
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
                fontSize = (screenheight * (0.035)).sp,
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
                    navController.navigate(OnboardingScreen.DateRangePicker.name)
                    entered = true
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = modifier
                    .width(175.dp)
                    .semantics { contentDescription = "datepick" },
                shape = RoundedCornerShape(20)
            ) {
                if (mDate.value.contains("Choose date")){
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
                        Text(text = mDate.value, color = Color.Gray, fontSize = 14.sp)
                        Image(
                            painter = painterResource(R.drawable.onboard_calendar),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 10.dp, end=0.dp),
                        )
                    }
                }
                else {
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 12.dp,
                                bottom = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = mDate.value, color = Color.Black, fontSize = 18.sp)
                    }
                }
            }
            Spacer(Modifier.height((screenwidth * (0.02)).dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = (screenheight * (0.03)).dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = {
                    onboardUiState.date = "Choose date"
                    onboardUiState.dateOptions = listOf()
                    navController.navigate(OnboardingScreen.QuestionThree.name)
                },
                modifier = modifier
                    .padding(start = (screenwidth * (0.1)).dp)
                    .weight(1f)
                    .semantics { contentDescription = "Skip" },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)

            ) {
                Text(stringResource(R.string.skip), color = Color(97, 153, 154), fontSize = 20.sp)
            }
            Button(
                onClick = {
                    onSelectionChanged(mDate.value + "|" + mDateTo.value)
                    navController.navigate(OnboardingScreen.QuestionThree.name)
                },
                enabled = entered,
                modifier = modifier
                    .padding(end = (screenwidth * (0.1)).dp)
                    .weight(1f)
                    .semantics { contentDescription = "Next" },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))
            ) {
                Text(stringResource(R.string.next), color = Color.White, fontSize = 20.sp)
            }
        }
    }
}