package com.example.theperiodpurse.ui.education

import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.RowScopeInstance.weight
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EducationPrivacyScreen() {
    EducationBackground()

    Column(
        modifier = Modifier
            .padding(28.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            fontSize = 32.sp,
            fontWeight = Bold,
            text = "Privacy Policy"
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray, thickness = 2.dp)

        Header("Privacy Statement")

        Text(text = "The Period Purse strives to achieve menstrual equity by providing people " +
                "who menstruate with access to free menstrual products, and to reduce the stigma " +
                "surrounding periods through public education and advocacy.\n" +
                "With the help of UofT Blueprint from the University of Toronto, we have developed " +
                "this app to enable you to keep track of your periods and provide you with useful " +
                "information. \n" +
                "We understand that recording intimate information about your body in an app can " +
                "be scary. Who can access this information? How will it be used? Is it well " +
                "protected?\n" +
                "This is why we build this app with your privacy first in mind. Contrary to other " +
                "period tracking apps, with your Period Purse app, no one but you can access any " +
                "of the information you provide. This document provides you with more information " +
                "about how this works, and answers some commonly asked questions."
        )
        ReachOut("You can also always reach out at hello@periodpurse.com " +
                "if you cannot find what you are looking for.")


        Header("What do we do with your information?")

        Text("Nothing! Nor the Period Purse, nor any other organization, can access your " +
                "personal information. ")


        Header("Did you get my consent?")

        Text("We did not ask for your consent, because we do not want your data. Your data " +
                "is yours and yours only.")


        Header("Do you share my information with anyone?")

        Text("We do not disclose your data to any third party. We could not if we wanted to " +
                "since we do not have it!\n" +
                "All the data that you add to the app to keep track of your periods and your " +
                "health is stored locally, on your phone, with one exception. We wanted you to " +
                "be able to transfer your data from one device to the other in case you change " +
                "phone, so that you do not lose your history. To make sure this would not " +
                "jeopardize your privacy, we created an optional feature that enables you to " +
                "connect your Android account to your app and download all your data on your " +
                "Cloud. Upon request, data goes straight from the app to your Google account. " +
                "You can then download it from your new phone. \n" +
                "Once your data is on your Cloud, the Cloud privacy policy applies. We invite " +
                "you to read it if you have any question on how Apple protects your " +
                "information.\n" +
                "The app also includes some links to social media pages. If you click those " +
                "links, these pages’ privacy policy applies. \n")


        Header("Is my information protected?")

        Text("We built this app so that you would feel comfortable tracking your periods on" +
                " your phone. Not accessing it is the best way for us to protect it. ")


        Header("I am underage. Can I use this app?")

        Text("From a privacy standpoint, you can use this app whichever your age is, " +
                "because your data stays with you.")

        Header("Changes to this privacy policy.")

        Text("If any of the above were to change, we will update this document to inform you.")

        Header("Questions and contact information")
        ReachOut("Any question? Just email us: hello@periodpurse.com.")
    }
}


@Composable
fun Header(str: String) {
    Text(
        fontWeight = Bold,
        text = str
    )
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
    EducationPrivacyScreen()
}