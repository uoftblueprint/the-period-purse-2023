package com.example.theperiodpurse.ui.onboarding


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.MainActivity
import com.example.theperiodpurse.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WelcomeScreen(signIn: () -> Unit, onNextButtonClicked: () -> Unit) {

    val configuration = LocalConfiguration.current

    val screenheight = configuration.screenHeightDp;

    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Column(
        modifier = Modifier
            .padding((screenheight * 0.02).dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        Spacer(modifier = Modifier.height((screenheight*0.05).dp))

        // Logo Image
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier.size((screenheight*0.25).dp)
        )
        Spacer(modifier = Modifier.height((screenheight*0.05).dp))

        // Welcome text
        Text(text = stringResource(R.string.welcome), style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height((screenheight*0.13).dp))

        // Quick Start button
        QuickStartButton(
            onClick = onNextButtonClicked
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Sign in with Google Button
        GoogleSignInButton {

            signIn()
        }
        Spacer(modifier = Modifier.height((screenheight*0.006).dp))

        val annotatedLinkString = buildAnnotatedString {
            val str = "Terms and Conditions and Privacy Policy"
            var startIndex = str.indexOf("Terms and Conditions")
            var endIndex = startIndex + 20
            addStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                ), start = startIndex, end = endIndex
            )
            startIndex = str.indexOf("Privacy Policy")
            endIndex = startIndex + 14
            addStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                ), start = startIndex, end = endIndex
            )
            append(str)
        }

        Text("By continuing, you accept the", textAlign = TextAlign.Center, )
        Text(text = annotatedLinkString, textAlign = TextAlign.Center)
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoogleSignInButton(
    signInClicked: () -> Unit
) {
    Surface(onClick = signInClicked,
        modifier = Modifier
            .widthIn(min = 350.dp)
            .height(50.dp),
        shape= RoundedCornerShape(15),
        border = BorderStroke(width = 1.dp, color=Color.LightGray),
        color = MaterialTheme.colors.surface
    ) {
        Row (modifier = Modifier
            .padding(
                start = 12.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google Button",
                tint=Color.Unspecified,

                )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sign in with Google",
                color = Color.Black, fontSize = 20.sp,
            )
        }
    }
}
@Composable
fun GoogleSignOutButton(
    signOutClicked: () -> Unit
) {

    Button(onClick = signOutClicked) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.CenterVertically),
            text = "Sign Out Of Google",
            fontSize = MaterialTheme.typography.h6.fontSize,
        )
    }
}
@Composable
fun QuickStartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .widthIn(min = 350.dp)
            .height(50.dp).semantics { contentDescription = "Next" },
        shape= RoundedCornerShape(15),
//        color = Color(52, 235, 161)
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))
    ) {
        Text("Quick Start", color = Color.White, fontSize = 20.sp,)
    }
}
