package com.example.theperiodpurse.ui.onboarding


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R


@Composable
fun WelcomeScreen(onNextButtonClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // Logo Image
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier.width(500.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Welcome text
        Text(text = stringResource(R.string.welcome), style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))

        // Quick Start button
        QuickStartButton(
            onClick = { onNextButtonClicked() }
        )

        // Sign in with Apple button
        AppleSignInButton(
            onClick = { onNextButtonClicked() }
        )
        Text("By continuing, you accept the", textAlign = TextAlign.Center)
        Text("Terms and Conditions and Privacy Policy.", textAlign = TextAlign.Center)

    }
}

@Composable
fun QuickStartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp),
//        color = Color(52, 235, 161)
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))
    ) {
        Text("Quick Start", color = Color.White)
    }
}

@Composable
fun AppleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Text("Sign In with Apple", color = Color.Black)
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onNextButtonClicked = { })

}