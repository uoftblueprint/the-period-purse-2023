package com.example.theperiodpurse.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.OnboardUIState
import com.example.theperiodpurse.ui.onboarding.backbutton

/**
 * This composable expects [onboardUIState] that represents the onboarding state, [onCancelButtonClicked] lambda
 * that triggers canceling the order and passes the final order to [onSendButtonClicked] lambda
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SummaryScreen(
    onboardUiState: OnboardUIState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean
) {
    val resources = LocalContext.current.resources

    val numberOfDays = resources.getQuantityString(
        R.plurals.period_length,
        onboardUiState.days,
        onboardUiState.days,
    )

    val configuration = LocalConfiguration.current

    val screenwidth = configuration.screenWidthDp;

    val screenheight = configuration.screenHeightDp;


    backbutton(navigateUp, canNavigateBack)

    Column(
        modifier = modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Spacer(modifier = Modifier.height((screenheight * (0.13)).dp))


        Image(
            painter = painterResource(R.drawable.pad_3_2x),
            contentDescription = null,
            modifier = Modifier.size((screenheight * 0.25).dp),
        )

        Text(
            text = "You're all set!",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )




        Spacer(modifier = Modifier.height((screenheight * 0.08).dp))

        if (onboardUiState.days != 0) {
            Column(Modifier.padding(start = 25.dp)) {


                Text(
                    text = stringResource(R.string.average_period_length),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(97, 153, 154),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = numberOfDays, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    fontSize = 17.sp
                )


            }
            Divider(
                thickness = 1.dp,
                color = Color(97, 153, 154),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (!onboardUiState.date.contains("Choose date to")) {

            Column(Modifier.padding(start = 25.dp)) {
                Text(
                    stringResource(R.string.last_period),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(97, 153, 154),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Text(
                    text = onboardUiState.date,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    fontSize = 17.sp
                )


            }

            Divider(
                thickness = 1.dp,
                color = Color(97, 153, 154),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(Modifier.padding(start = 25.dp)) {
            Text(
                stringResource(R.string.symptoms_to_log),
                modifier = Modifier.fillMaxWidth(),
                color = Color(97, 153, 154),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            val symptoms = listOf(
                "Mood",
                "Exercise",
                "Cramps",
                "Sleep",

                )


            Row() {
                var dontprint = false

                Column(Modifier.padding(top = 10.dp, bottom = 10.dp, end = 5.dp)) {
                    Image(
                        painter = painterResource(R.drawable.opacity_black_24dp),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)

                    )

                }

                symptoms.forEach { symptom ->
                    onboardUiState.symptomsOptions.forEach { select ->
                        if (symptom == select || symptom == "Flow") {
                            Column(modifier = Modifier.padding(all = 10.dp)) {

                                var painter =
                                    painterResource(R.drawable.self_improvement_black_24dp)


                                when (symptom) {
                                    "Mood" -> painter =
                                        painterResource(R.drawable.sentiment_neutral_black_24dp)
                                    "Exercise" -> painter =
                                        painterResource(R.drawable.self_improvement_black_24dp)
                                    "Cramps" -> painter =
                                        painterResource(R.drawable.sick_black_24dp)
                                    "Sleep" -> painter =
                                        painterResource(R.drawable.nightlight_black_24dp)
                                    else -> { // Note the block
                                        dontprint = true
                                    }
                                }

                                if (!dontprint) {
                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(25.dp)


                                    )

                                }


                            }

                        }

                    }
                }
            }

        }

        Divider(thickness = 1.dp, color = Color(97, 153, 154), modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height((screenheight * 0.05).dp))



        Button(
            modifier = Modifier
                .padding(horizontal = (screenheight * 0.02).dp)
                .fillMaxWidth(),
            onClick = { onSendButtonClicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))

        ) {
            Text(stringResource(R.string.lets_go), color = Color.White, fontSize = 25.sp)
        }

    }
}
