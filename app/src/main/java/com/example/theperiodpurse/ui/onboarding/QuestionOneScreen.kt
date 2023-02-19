package com.example.theperiodpurse.ui.onboarding


import android.graphics.Color.rgb
import android.os.Build
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Keyboard

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.MainActivity
import com.example.theperiodpurse.R
import java.time.format.TextStyle


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuestionOneScreen(onNextButtonClicked: () -> Unit,
                      onSelectionChanged: (String) -> Unit = {},
                      onCancelButtonClicked: () -> Unit = {},
                      modifier: Modifier = Modifier, mainActivity: MainActivity) {
    var periodCycle by remember { mutableStateOf("") }
    var entered by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    backbutton1()

    drawconcave()


    progress()










    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Image(
            painter = painterResource(R.drawable.last_period_length),
            contentDescription = null,
            modifier = Modifier.size(200.dp),
        )

        Spacer(modifier = Modifier.height(70.dp))



        Text(
            text = stringResource(R.string.question_one),
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.description_one),
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp),
                    textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        EditDaysField(
            label = R.string.tap_to_input,
            value = periodCycle,
            onValueChange = { periodCycle = it },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() ; onSelectionChanged(periodCycle) ; entered = true },),
            entered = entered
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = onNextButtonClicked,
                modifier = modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                ),

            ) {
                Text(stringResource(R.string.skip),color = Color(97, 153, 150), fontSize = 20.sp)
            }

            Button(
                onClick = onNextButtonClicked,
                enabled = entered,
                modifier =modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 150))
            ) {
                Text(stringResource(R.string.next),color = Color.White, fontSize = 20.sp)
            }
        }




    }


}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun backbutton1() {
    Column(
        modifier = Modifier
            .padding(
                vertical = 30.dp,
                horizontal = 20.dp
            )
            ,
    ) {
        Image(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = null
            ,
        )
    }

}


@Composable
fun EditNumberField(
    @StringRes label: Int,
    value: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
){


    TextField(
        value = value,
        onValueChange = onValueChange,

        label = { Text(stringResource(label)) },
        modifier = Modifier.fillMaxWidth(),

        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions

    )
}

@Composable
fun EditDaysField(
    @StringRes label: Int,
    value: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
    entered: Boolean,
){


    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {Text("Tap to input")},

        shape = RoundedCornerShape(15),


        modifier = Modifier
            .width(175.dp)
            .height(60.dp)
            .background(color = Color.White),
        trailingIcon = { if (entered){
            Text(text = "days", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(end=20.dp))

        }
        else {
            Image(
                painter = painterResource(R.drawable.onboard_keyboard),
                contentDescription = null,
            )
        }

        },
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),

        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions

    )
}

@Composable
fun backgroundshape(){


    val configuration = LocalConfiguration.current

    Canvas(modifier = Modifier
        .size(100.dp)
        .padding(PaddingValues(top = 125.dp))){

        drawRect(
            color = Color(rgb(142, 212, 193)),
            size = Size(width = configuration.screenWidthDp.dp.toPx(), height = 230.dp.toPx())

        )



    }
}

@Composable
fun questionmark(){
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier
            .padding(PaddingValues(top = 200.dp))
            .width(configuration.screenWidthDp.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Image(
            painter = painterResource(R.drawable.redquestion),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
        )
    }
}

@Composable
fun progress(){
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
                    .size(40.dp)
                    .padding(PaddingValues(top = 20.dp))
            ) {

                drawRoundRect(
                    color = Color(rgb(142, 212, 193)),
                    cornerRadius = CornerRadius(60f, 60f),
                    size = Size(width = 30.dp.toPx(), height = 10.dp.toPx())

                )


            }
            Canvas(
                modifier = Modifier
                    .size(20.dp)
                    .padding(PaddingValues(top = 20.dp))
            ) {

                drawRoundRect(
                    color = Color(rgb(142, 212, 193)).copy(alpha = 0.5f),
                    cornerRadius = CornerRadius(60f, 60f),
                    size = Size(width = 10.dp.toPx(), height =10.dp.toPx())

                )


            }
            Canvas(
                modifier = Modifier
                    .size(30.dp)
                    .padding(PaddingValues(top = 20.dp))
            ) {

                drawRoundRect(
                    color = Color(rgb(142, 212, 193)).copy(alpha = 0.5f),
                    cornerRadius = CornerRadius(60f, 60f),
                    size = Size(width = 10.dp.toPx(), height = 10.dp.toPx())

                )


            }


        }
    }
}

@Composable
fun drawconcave() {

    Canvas(
        modifier = Modifier
            .size(400.dp)
            .padding(PaddingValues(top = 45.dp)))
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

        drawPath(path = path, color = Color(rgb(142, 212, 193)))

        drawPath(path = path2, color = Color(rgb(142, 212, 193)))
    }
}




@Preview
@Composable
fun TestPreview(){
    drawconcave()

}
