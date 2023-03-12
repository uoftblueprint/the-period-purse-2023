package com.tpp.theperiodpurse.ui.setting


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tpp.theperiodpurse.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*




class NotificationsScreen : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationsLayout(LocalContext.current)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationsLayout(context: Context){
    var pickedTime by remember { mutableStateOf(LocalTime.NOON) }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    val timeDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = stringResource(id = R.string.remind_me_to_log_symptoms),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp))
        Text(text = "We'll remind you to log your symptoms.",
            modifier = Modifier.padding(start = 15.dp),
            fontWeight = FontWeight.Light)
        Divider(color = Color.Gray, thickness = 0.7.dp)
        Expandable()
        Divider(color = Color.Gray, thickness = 0.7.dp)
        TimePicker(timeDialogState, formattedTime)
        Divider(color = Color.Gray, thickness = 0.7.dp)

    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                Toast.makeText(
                    context,
                    "Clicked ok",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Pick a time",
        ) {
            pickedTime = it
        }
    }

}

@Composable
private fun TimePicker(
    timeDialogState: MaterialDialogState,
    formattedTime: String
) {
    val repeatExpanded by remember {
        mutableStateOf(false)
    }

    val extraPadding by animateDpAsState(
        targetValue = if (repeatExpanded) 48.dp else 0.dp,
    )

    Column {
        Row(
            modifier = Modifier
                //        .padding(24.dp)
                .background(Color.White.copy(alpha = 0.8f))
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Reminder Time", modifier = Modifier.padding(start = 5.dp))
                Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    elevation = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .background(Color.Transparent),
                    onClick = {
                        timeDialogState.show()
                    }) {
                    Text(
                        text = formattedTime,
                        style = TextStyle(color = Color(0xFF74C5B7)),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun Expandable(){
    var repeatExpanded by remember {
        mutableStateOf(false)
    }

    val extraPadding by animateDpAsState(
        targetValue = if (repeatExpanded) 48.dp else 0.dp,
    )

    Column {
        Row(
            modifier = Modifier
                //        .padding(24.dp)
                .background(Color.White)
                .fillMaxWidth()
        ) {
            val radioOptions = listOf("Every Day", "Every Week", "Every month")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2]) }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Repeat", modifier = Modifier.padding(start = 5.dp))
                if (repeatExpanded) {
                    RadioButtons(radioOptions, selectedOption, onOptionSelected)
                }

            }
            Text(text = selectedOption, modifier =
            if(!repeatExpanded) Modifier.align(Alignment.CenterVertically) else Modifier.align(
                Alignment.Top
            ))
            IconButton(onClick = { repeatExpanded = !repeatExpanded }) {
                Icon(
                    imageVector = if (repeatExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (repeatExpanded) "Show less text" else "Show more text"
                )
            }
        }
    }



}

@Composable
fun RadioButtons(radioOptions: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) }
                        )
                        .padding(horizontal = 15.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption), modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}






