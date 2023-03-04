package com.example.theperiodpurse.ui.onboarding


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.MainActivity
import com.example.theperiodpurse.R


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuestionOneScreen(
    onNextButtonClicked: () -> Unit,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier, mainActivity: MainActivity, navigateUp: () -> Unit,
    canNavigateBack: Boolean
) {
    var periodCycle by remember { mutableStateOf("") }
    var entered by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val configuration = LocalConfiguration.current

    val screenwidth = configuration.screenWidthDp;

    val screenheight = configuration.screenHeightDp;
    backbutton(navigateUp, canNavigateBack)
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        val ratio = 0.5
        val ratioimage = 0.2
        val height = (screenheight*ratio)
        val imageheight = (screenheight*ratioimage)

        Spacer(Modifier.height((screenheight*(0.02)).dp))

        Box(modifier = Modifier
            .width(screenwidth.dp)
            .height(height.dp)

            )
        {
            background_shape()

            Image(
                painter = painterResource(R.drawable.last_period_length),
                contentDescription = null,
                modifier = Modifier
                    .size(imageheight.dp)
                    .align(Alignment.Center),
            )
            val barheight1 = (screenheight*(0.09))

            Image(
                painter = painterResource(R.drawable.onboard_bar1),
                contentDescription = null,
                modifier = Modifier
                    .size(barheight1.dp)
                    .align(Alignment.BottomCenter),
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
                .width((screenwidth * (0.6)).dp),
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
                modifier = modifier
                    .padding(start = (screenwidth * (0.1)).dp)
                    .weight(1f),
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
                modifier = modifier
                    .padding(end = (screenwidth * (0.1)).dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 150))
            ) {
                Text(stringResource(R.string.next),color = Color.White, fontSize = 20.sp)
            }
        }
    }


}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun backbutton(navigateUp: () -> Unit, canNavigateBack: Boolean) {

    TopAppBar(
        title = { "" },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Image(
                        painter = painterResource(R.drawable.back_icon),
                        contentDescription = null
                    )
                }
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp

    )

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
            .border(
                BorderStroke(2.dp, SolidColor(Color.Transparent)),
                shape = RoundedCornerShape(20)
            ),
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
fun background_shape() {

    Image(
        painter = painterResource(R.drawable.background_shape__1_),
        contentDescription = null,
        modifier = Modifier.size(1000.dp)
    )


}

