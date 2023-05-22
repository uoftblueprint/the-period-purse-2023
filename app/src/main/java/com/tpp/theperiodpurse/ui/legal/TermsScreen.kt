package com.tpp.theperiodpurse.ui.legal

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.education.EducationBackground
import com.tpp.theperiodpurse.ui.education.teal
import com.tpp.theperiodpurse.ui.onboarding.scaledSp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TermsScreen(navController: NavHostController) {
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
                            .clickable(interactionSource = interactionSource,
                                indication = null)
                            { navController.popBackStack() }
                            .size(20.dp),
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "Back Button",
                        tint = Color(teal)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        fontSize = 20.scaledSp(),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        text = "Terms and Conditions"
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
                    fontSize = 32.scaledSp(),
                    fontWeight = FontWeight.Bold,
                    text = "Terms and Conditions"
                )

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray,
                    thickness = 2.dp
                )

                Body(
                    "These terms and conditions (the \"Terms and Conditions\") govern the use " +
                            "of www.theperiodpurse.com (the \"Site\"). This Site is owned and operated by " +
                            "The Period Purse. This Site is a news or media website. \n" +
                            "By using this Site, you indicate that you have read and understand these Terms " +
                            "and Conditions and agree to abide by them at all times."
                )


                Header("Intellectual Property")

                Body(
                    "All content published and made available on our Site is the property of The " +
                            "Period Purse and the Site's creators. This includes, but is not limited to " +
                            "images, text, logos, documents, downloadable files and anything that " +
                            "contributes to the composition of our Site."
                )


                Header("Accounts")

                Body(
                    "When you create an account on our Site, you agree to the following:  \n" +
                            "1. You are solely responsible for your account and the security and privacy of " +
                            "your account, including passwords or sensitive information attached to that " +
                            "account; and \n" +
                            "2. All personal information you provide to us through your account is up to " +
                            "date, accurate, and truthful and that you will update your personal information " +
                            "if it changes. \n" +
                            "We reserve the right to suspend or terminate your account if you are using our " +
                            "Site illegally or if you violate these Terms and Conditions."
                )


                Header("Limitation of Liability")

                Body(
                    "The Period Purse and our directors, officers, agents, employees, " +
                            "subsidiaries, and affiliates will not be liable for any actions, claims, " +
                            "losses, damages, liabilities and expenses including legal fees from your " +
                            "use of the Site."
                )


                Header("Indemnity")

                Body(
                    "Except where prohibited by law, by using this Site you indemnify and " +
                            "hold harmless The Period Purse and our directors, officers, agents, " +
                            "employees, subsidiaries, and affiliates from any actions, claims, losses, " +
                            "damages, liabilities and expenses including legal fees arising out of your " +
                            "use of our Site or your violation of these Terms and Conditions."
                )


                Header("Applicable Law")

                Body(
                    "These Terms and Conditions are governed by the laws of Canada. " +
                            "The Period Purse headquarters is in Ontario. "
                )


                Header("Severability")

                Body(
                    "If at any time any of the provisions set forth in these Terms and Conditions " +
                            "are found to be inconsistent or invalid under applicable laws, those " +
                            "provisions will be deemed void and will be removed from these Terms and " +
                            "Conditions. All other provisions will not be affected by the removal and " +
                            "the rest of these Terms and Conditions will still be considered valid.  "
                )


                Header("Changes")

                Body(
                    "These Terms and Conditions may be amended from time to time in order to " +
                            "maintain compliance with the law and to reflect any changes to the way we " +
                            "operate our Site and the way we expect users to behave on our Site. We will " +
                            "notify users by email of changes to these Terms and Conditions or post a " +
                            "notice on our Site."
                )


                Header("Contact Details")

                Body(
                    "Please contact us if you have any questions or concerns. " +
                            "Our contact details are as follows:\n"
                )

                Body(
                    "hello@theperiodpurse.com \n\n" +
                            "1460 The Queensway, Toronto, Ontario, M8S 1S7\n\n" +
                            "You can also contact us through the feedback form available on our Site.\n\n"
                )

                Body("Effective Date: 14th day of April, 2022 \n")

                Spacer(modifier = Modifier.size(36.dp))
            }
        }
    )
}


@Preview
@Composable
fun PreviewTermsConditionsScreen() {
    TermsScreen(rememberNavController())
}