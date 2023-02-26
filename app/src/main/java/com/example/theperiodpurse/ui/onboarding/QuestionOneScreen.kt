package com.example.theperiodpurse.ui.onboarding


import android.graphics.Color.rgb
import android.graphics.Paint
import android.os.Build
import android.util.DisplayMetrics
import android.view.RoundedCorner
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuestionOneScreen(onNextButtonClicked: () -> Unit,
                      onSelectionChanged: (String) -> Unit = {},
                      onCancelButtonClicked: () -> Unit = {},
                      modifier: Modifier = Modifier, mainActivity: MainActivity) {
    var periodCycle by remember { mutableStateOf("") }
    var entered by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val configuration = LocalConfiguration.current

    val screenwidth = configuration.screenWidthDp;

    val screenheight = configuration.screenHeightDp;
    backbutton1()



    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        val ratio = 0.5
        val ratioimage = 0.2
        val height = (screenheight*ratio)
        val imageheight = (screenheight*ratioimage)

        Spacer(Modifier.height((screenheight*(0.02)).dp))

        Box(modifier = Modifier.width(screenwidth.dp).height(height.dp)

            )
        {
            background_shape()

            Image(
                painter = painterResource(R.drawable.last_period_length),
                contentDescription = null,
                modifier = Modifier.size(imageheight.dp).align(Alignment.Center),
            )
            val barheight1 = (screenheight*(0.09))

            Image(
                painter = painterResource(R.drawable.onboard_bar1),
                contentDescription = null,
                modifier = Modifier.size(barheight1.dp).align(Alignment.BottomCenter),
            )

        }








        Text(
            text = stringResource(R.string.question_one),
            fontSize = 28.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height((screenheight*(0.02)).dp))

        Text(
            text = stringResource(R.string.description_one),
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width((screenwidth*(0.6)).dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height((screenheight*(0.02)).dp))

        EditDaysField(
            label = R.string.tap_to_input,
            value = periodCycle,
            onValueChange = { periodCycle = it },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(); onSelectionChanged(periodCycle); entered = true
                },
            ),
            entered = entered
        )
        Spacer(Modifier.height((screenheight*(0.03)).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = onNextButtonClicked,
                modifier = modifier.padding(start=(screenwidth*(0.1)).dp).weight(1f),
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
                modifier =modifier.padding(end=(screenwidth*(0.1)).dp).weight(1f),
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
            .clickable(enabled = true, onClick = {

            }),
    ) {
        Image(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = null
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


    TextField(
        shape = RoundedCornerShape(20),
        colors = TextFieldDefaults.textFieldColors(backgroundColor=Color.White),
        value = value,
        onValueChange = onValueChange,
        placeholder = {Text("Tap to input", modifier = Modifier.padding(start=10.dp))},




        modifier = Modifier
            .width(175.dp)
            .height(60.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20))
            .border(BorderStroke(2.dp, SolidColor(Color.Transparent)), shape = RoundedCornerShape(20)),
        trailingIcon = { if (entered){
            Text(text = "days", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(end=20.dp))

        }
        else {
            Image(
                painter = painterResource(R.drawable.onboard_keyboard),
                contentDescription = null,
                modifier = Modifier.padding(start = 0.dp, end=20.dp),
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
fun background_shape(){

    val configuration = LocalConfiguration.current

    Image(
        painter = painterResource(R.drawable.background_shape__1_),
        contentDescription = null,
        modifier = Modifier.size(1000.dp) )




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
