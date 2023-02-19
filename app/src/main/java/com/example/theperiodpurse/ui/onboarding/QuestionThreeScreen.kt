package com.example.theperiodpurse.ui

import android.graphics.Color.rgb
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.ui.onboarding.backbutton1
import com.example.theperiodpurse.ui.onboarding.drawconcave

/**
 * Composable that displays the list of items as [RadioButton] options,
 * [onSelectionChanged] lambda that notifies the parent composable when a new value is selected,
 * [onCancelButtonClicked] lambda that cancels the order when user clicks cancel and
 * [onNextButtonClicked] lambda that triggers the navigation to next screen
 */


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuestionThreeScreen(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    backbutton1()

    drawconcave3()

    progress2()


    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,



    ) {

        Spacer(modifier = Modifier.height(65.dp))

        Image(
            painter = painterResource(R.drawable.menstruation_calendar__1_),
            contentDescription = null,
            modifier = Modifier.size(200 .dp),
        )

        Spacer(modifier = Modifier.height(60.dp))



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
            fontSize = 15.sp,
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

        Spacer(modifier = Modifier.height(10.dp))

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
            ).padding(horizontal = 13.dp),
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
        Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextButton(modifier = Modifier.weight(1f), onClick = onNextButtonClicked, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                    Text(stringResource(R.string.skip),color = Color(97, 153, 154), fontSize = 20.sp)
                }
                Button(
                    modifier = Modifier.weight(1f),
                    // the button is enabled when the user makes a selection
                    enabled = selectedValue.isNotEmpty(),
                    onClick = onNextButtonClicked,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))
                ) {
                    Text(stringResource(R.string.next),color = Color.White, fontSize = 20.sp)
                }
            }

    }
}


@Preview
@Composable
fun SelectOptionPreview() {
    val context = LocalContext.current
    QuestionThreeScreen(


        options = symptoms.map { id -> context.resources.getString(id) }
    )
}

@Composable
fun drawconcave3() {

    Canvas(
        modifier = Modifier
            .size(400.dp))
    {

        val path = Path().apply {
            moveTo(0f, 300f)
            lineTo(1600f, 300f)
            lineTo(1600f, 1100f)

            arcTo(Rect(offset = Offset(0F, 1100F), size = Size(width = 1600f, height =300F )),0f, -180f, false)
            lineTo(0f, 700f)



            close()
        }

        val path2 = Path().apply {
            moveTo(0f, 300f)
            lineTo(1600f, 300f)
            lineTo(1600f, 0f)

            arcTo(Rect(offset = Offset(0F, 0F), size = Size(width = 1600f, height =300F )),0f, 180f, false)
            lineTo(0f, 700f)



            close()
        }

        drawPath(path = path, color = Color(android.graphics.Color.rgb(142, 212, 193)))

        drawPath(path = path2, color = Color(android.graphics.Color.rgb(142, 212, 193)))
    }
}

@Composable
fun progress2(){
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier
            .padding(PaddingValues(top = 130.dp, start = 10.dp))
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
                    .size(20.dp)
                    .padding(PaddingValues(top = 20.dp))
            ) {

                drawRoundRect(
                    color = Color(android.graphics.Color.rgb(142, 212, 193)).copy(alpha = 0.5f),
                    cornerRadius = CornerRadius(60f, 60f),
                    size = Size(width = 10.dp.toPx(), height =10.dp.toPx())

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
                    size = Size(width = 30.dp.toPx(), height = 10.dp.toPx())

                )


            }


        }
    }
}


