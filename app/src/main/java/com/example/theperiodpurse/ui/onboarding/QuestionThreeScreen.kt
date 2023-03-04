package com.example.theperiodpurse.ui

import android.graphics.Color.rgb
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.DataSource.symptoms
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.ui.onboarding.backbutton1
import com.example.theperiodpurse.ui.onboarding.background_shape

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuestionThreeScreen(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    val configuration = LocalConfiguration.current

    val screenwidth = configuration.screenWidthDp;

    val screenheight = configuration.screenHeightDp;






    Column(
        modifier = Modifier.fillMaxHeight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,



    ) {

        val ratio = 0.45
        val ratioimage = 0.20
        val height = (screenheight*ratio)
        val imageheight = (screenheight*ratioimage)
        Box(modifier = Modifier.width(screenwidth.dp).height(height.dp)

        )
        {
            background_shape()

            Image(
                painter = painterResource(R.drawable.menstruation_calendar__1_),
                contentDescription = null,
                modifier = Modifier.size(imageheight.dp).align(Alignment.Center),
            )
            val barheight1 = (screenheight*(0.09))

            Image(
                painter = painterResource(R.drawable.onboard_bar3),
                contentDescription = null,
                modifier = Modifier.size(barheight1.dp).align(Alignment.BottomCenter),
            )

        }
        Text(
            text = stringResource(R.string.question_three),
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(300.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )


        Text(
            text = stringResource(R.string.description_three),
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(110.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15))
                .background(Color(rgb(188, 235, 214))),

        ){
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,){

                    Box(modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(30))
                        .background(Color(rgb(220, 242, 240)))){

                        Image(
                            painter = painterResource(R.drawable.opacity_black_24dp),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.Center),
                            colorFilter = ColorFilter.tint(Color.Gray)

                        )

                    }


                    Text(
                        text = stringResource(R.string.flow),
                        fontSize = 15.sp,
                        color = Color.Gray

                        )

                }

                Text(
                    text = stringResource(R.string.caption_three),
                    fontSize = 15.sp,

                    )

            }

        }

        Spacer(modifier = Modifier.height((screenheight*(0.005)).dp))


        Row(){
            Column(modifier = Modifier.selectable(
                selected = selectedValue.contains("Mood"),
                onClick = {
                    val item = "Mood"

                    if (selectedValue.contains(item)) {

                        selectedValue = selectedValue.replace(item, "")


                    } else if (!selectedValue.contains(item)) {
                        selectedValue = selectedValue + "|" + item

                    }
                    onSelectionChanged(selectedValue)
                }
            ).padding(horizontal = (screenheight*0.02).dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,) {
                Box(modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(30))
                    .then(if (!selectedValue.contains("Mood")) Modifier.background(Color.White) else Modifier.background(Color(rgb(142, 212, 193))))){

                        Image(
                            painter = painterResource(R.drawable.sentiment_neutral_black_24dp),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.Center)

                        )

                    }

                        Text(
                            text = stringResource(R.string.mood),
                            fontSize = 15.sp,

                            )
                    }






            Column(modifier = Modifier.selectable(
                selected = selectedValue.contains("Exercise"),
                onClick = {
                    val item = "Exercise"

                    if (selectedValue.contains(item)) {

                        selectedValue = selectedValue.replace(item, "")


                    } else if (!selectedValue.contains(item)) {
                        selectedValue = selectedValue + "|" + item

                    }
                    onSelectionChanged(selectedValue)
                }).padding(horizontal = 13.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Box(modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(30))
                    .then(if (!selectedValue.contains("Exercise")) Modifier.background(Color.White) else Modifier.background(Color(rgb(142, 212, 193))))){

                    Image(
                        painter = painterResource(R.drawable.self_improvement_black_24dp),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.Center),

                    )

                }


                Text(
                    text = stringResource(R.string.exercise),
                    fontSize = 15.sp,

                )

            }

            Column(modifier = Modifier.selectable(
                selected = selectedValue.contains("Cramps"),
                onClick = {
                    val item = "Cramps"

                    if (selectedValue.contains(item)) {

                        selectedValue = selectedValue.replace(item, "")


                    } else if (!selectedValue.contains(item)) {
                        selectedValue = selectedValue + "|" + item

                    }
                    onSelectionChanged(selectedValue)
                }).padding(horizontal = 13.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Box(modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(30))
                    .then(if (!selectedValue.contains("Cramps")) Modifier.background(Color.White) else Modifier.background(Color(rgb(142, 212, 193))))){

                    Image(
                        painter = painterResource(R.drawable.sick_black_24dp),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.Center),

                    )

                }


                Text(
                    text = stringResource(R.string.cramps),
                    fontSize = 15.sp,

                )

            }



            Column(modifier = Modifier.selectable(
                selected = selectedValue.contains("Sleep"),
                onClick = {
                    val item = "Sleep"

                    if (selectedValue.contains(item)) {

                        selectedValue = selectedValue.replace(item, "")


                    } else if (!selectedValue.contains(item)) {
                        selectedValue = selectedValue + "|" + item

                    }
                    onSelectionChanged(selectedValue)
                }).padding(horizontal = 13.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Box(modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(30))
                    .then(if (!selectedValue.contains("Sleep")) Modifier.background(Color.White) else Modifier.background(Color(rgb(142, 212, 193))))){

                    Image(
                        painter = painterResource(R.drawable.nightlight_black_24dp),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.Center),

                    )

                }


                Text(
                    text = stringResource(R.string.sleep),
                    fontSize = 15.sp,

                )

            }




        }
        Spacer(modifier = Modifier.height((screenheight*(0.01)).dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextButton(modifier = Modifier.padding(start=(screenwidth*(0.1)).dp).weight(1f), onClick = onNextButtonClicked, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                    Text(stringResource(R.string.skip),color = Color(97, 153, 154), fontSize = 20.sp)
                }
                Button(
                    modifier = Modifier.padding(end=(screenwidth*(0.1)).dp).weight(1f),
                    // the button is enabled when the user makes a selection
                    enabled = selectedValue.isNotEmpty(),
                    onClick = onNextButtonClicked,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))
                ) {
                    Text(stringResource(R.string.next),color = Color.White, fontSize = 20.sp)
                }
            }

    }

    backbutton1(navigateUp)
}


