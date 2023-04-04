package com.tpp.theperiodpurse.ui.legal

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.education.EducationBackground
import com.tpp.theperiodpurse.ui.education.teal

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PrivacyScreen(navController: NavHostController) {
    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()
    EducationBackground()

    Scaffold(
        backgroundColor = Color.Transparent,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource, indication = null
                            ) { navController.popBackStack() }
                            .size(20.dp),
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "Back Button",
                        tint = Color(teal)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        fontSize = 20.sp,
                        fontWeight = Bold,
                        color = Color.Black,
                        text = "Privacy Policy"
                    )

                    Spacer(modifier = Modifier.weight(1f))

                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    fontSize = 32.sp,
                    fontWeight = Bold,
                    text = "Privacy Policy"
                )

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray,
                    thickness = 2.dp
                )

                Header("Privacy Statement")

                Body(
                    "The Period Purse strives to achieve menstrual equity by providing " +
                            "people who menstruate with access to free menstrual products, and " +
                            "to reduce the stigma surrounding periods through public education " +
                            "and advocacy.\n\n" + "With the help of UofT Blueprint from the " +
                            "University of Toronto, we have developed this app to enable you to " +
                            "keep track of your periods and provide you with useful information. " +
                            "\n\n" + "We understand that recording intimate information about " +
                            "your body in an app can be scary. Who can access this information?" +
                            " How will it be used? Is it well protected?\n\n" + "This is why we " +
                            "build this app with your privacy first in mind. Contrary to other " +
                            "period tracking apps, with your Period Purse app, no one but you " +
                            "can access any " + "of the information you provide. This document " +
                            "provides you with more information " + "about how this works, " +
                            "and answers some commonly asked questions."
                )
                ReachOut(
                    "You can also always reach out at hello@periodpurse.com " +
                            "if you cannot find what you are looking for."
                )


                Header("What do we do with your information?")

                Body(
                    "Nothing! Nor the Period Purse, nor any other organization, can access " +
                            "your personal information. "
                )


                Header("Did you get my consent?")

                Body(
                    "We did not ask for your consent, because we do not want your data. " +
                            "Your data is yours and yours only."
                )


                Header("Do you share my information with anyone?")

                Body(
                    "We do not disclose your data to any third party. We could not if we wanted to " +
                            "since we do not have it!\n\n" +
                            "All the data that you add to the app to keep track of your periods and your " +
                            "health is stored locally, on your phone, with one exception. We wanted you to " +
                            "be able to transfer your data from one device to the other in case you change " +
                            "phone, so that you do not lose your history. To make sure this would not " +
                            "jeopardize your privacy, we created an optional feature that enables you to " +
                            "connect your Android account to your app and download all your data on your " +
                            "Cloud. Upon request, data goes straight from the app to your Google account. " +
                            "You can then download it from your new phone.\n\n" +
                            "Once your data is on your Cloud, the Cloud privacy policy applies. We invite " +
                            "you to read it if you have any question on how Apple protects your " +
                            "information.\n\n" +
                            "The app also includes some links to social media pages. If you click those " +
                            "links, these pages’ privacy policy applies.\n"
                )


                Header("Is my information protected?")

                Body(
                    "We built this app so that you would feel comfortable tracking your " +
                            "periods on your phone. Not accessing it is the best way for us " +
                            "to protect it. "
                )


                Header("I am underage. Can I use this app?")

                Body(
                    "From a privacy standpoint, you can use this app whichever your age is, " +
                            "because your data stays with you."
                )

                Header("Changes to this privacy policy.")

                Body("If any of the above were to change, we will update this document to " +
                        "inform you.")

                Header("Questions and contact information")
                ReachOut("Any question? Just email us: hello@periodpurse.com.")

                Spacer(modifier = Modifier.size(36.dp))
            }
        }
    )
}

@Composable
fun Body(str: String) {
    Text(
        fontSize = 14.sp,
        lineHeight = 16.sp,
        text = str
    )
}


@Composable
fun Header(str: String) {
    Column {
        Spacer(modifier = Modifier.size(12.dp))

        Text(modifier = Modifier.padding(vertical = 2.dp),
            fontSize = 14.sp,
            fontWeight = Bold,
            lineHeight = 24.sp,
            text = str
        )
    }

}

/**
 * Annotated Text String for including link to email for contacting TPP.
 */
@Composable
fun ReachOut(string: String) {
    val uriHandler = LocalUriHandler.current
    val startIndex = string.indexOf("hello@periodpurse.com")
    val endIndex = startIndex + 21

    val annotatedString: AnnotatedString = buildAnnotatedString {
        append(string)

        addStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )

        addStringAnnotation(
            tag = "email",
            annotation = "mailto:hello@periodpurse.com",
            start = startIndex,
            end = endIndex
        )
     }

    ClickableText(
        style = TextStyle(fontSize = 14.sp),
        text = annotatedString,
        onClick = { annotatedString
            .getStringAnnotations(tag = "email", it, it).firstOrNull()?.let {
                    stringAnnotation -> uriHandler.openUri(stringAnnotation.item)
            }
        }
    )
}


@Preview
@Composable
fun PreviewPrivacyScreen() {
    PrivacyScreen(rememberNavController())
}