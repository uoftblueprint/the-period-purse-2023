package com.example.theperiodpurse.ui.education

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.R


val products = listOf("Period Underwear", "Menstrual Cup", "Pads", "Cloth Pads", "Tampons",
    "Menstrual Disc")


/**
 * Education Home Screen for the Learn/Info Tab on the App.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EducationScreen(
    navController: NavHostController,
) {
    EducationBackground()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            state = rememberLazyGridState()
        ) {
            item (span = { GridItemSpan(2) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(IntrinsicSize.Min)
                            .width(IntrinsicSize.Max)
                            .padding(12.dp),

                        shape = RoundedCornerShape(12.dp),
                        elevation = 8.dp,
                        backgroundColor = Color.Gray,
                        onClick = { navController.navigate(Destination.DYK.route) },
                    ) {
                        Row() {
                            Column() {
                                Text(modifier = Modifier.padding(12.dp),
                                    text = "Did you know?")
                                Text(modifier = Modifier.padding(12.dp),
                                    text = "Text")
                            }
                            Image(modifier = Modifier.padding(12.dp),
                                painter = painterResource(R.drawable.app_logo),
                                contentDescription = null
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Left,
                        text = "Tap to learn more about period products."
                    )
                }
            }

            items(products,
            span = { GridItemSpan(1) }) {
                PeriodProducts(navController, it)
            }

            item (
                span = {GridItemSpan(2)}
            ) {
                Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    TPPCard(uriHandler)

                    /*
                    TODO: Add Social Media Icons
                    */

                    Text(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
                        .align(Alignment.CenterHorizontally),
                        text = "© 2023 The Period Purse. All rights reserved.",
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )

                    /*
                    Terms & Conditions, and Privacy Policy
                     */
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(horizontal = 12.dp)
                            .align(Alignment.CenterHorizontally)
                            .border(BorderStroke(2.dp, Color.Red)),
                        horizontalArrangement = Arrangement.Center,
                        ) {
                        ClickableText(
                            text = AnnotatedString("Terms and Conditions"),
                            onClick = { navController.navigate(Destination.Terms.route) },
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline,
                                color = Color.Blue,
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(" and ")
                        ClickableText(
                            text = AnnotatedString("Privacy Policy"),
                            onClick = { navController.navigate(Destination.Privacy.route) },
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline,
                                color = Color.Blue,
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    Spacer(modifier = Modifier.size(32.dp))

                }
            }
        }
    }
}


/**
 * Grid View for different Period Products.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PeriodProducts(navController: NavHostController, it: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(12.dp), elevation = 8.dp,
        backgroundColor = Color.Gray,
        onClick = { navController.navigate(Destination.Info.createRoute(it)) },
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.app_logo),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = it
            )
        }
    }
}


/**
 * TPP Info Card
 */
@Composable
fun TPPCard(uriHandler: UriHandler) {
    Card(
        modifier = Modifier
            .padding(horizontal = 36.dp, vertical = 8.dp)
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier.wrapContentSize(Alignment.Center)
        ) {
            Text(modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = Bold,
                text = "Learn more about The Period Purse")

            Text(modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
                textAlign = TextAlign.Center,
                text = "The Period Purse strives to achieve menstrual equity by providing " +
                        "people who menstruate with access to free menstrual products, and to " +
                        "reduce the stigma surrounding period through public education and " +
                        "advocacy.")

            Button(modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Green,
                    contentColor = Color.Black
                ),
                onClick = { uriHandler.openUri("https://www.google.ca/") }) {
                Text(text = "Visit the website")
            }
        }
    }
}


/**
 * Preview for Education Home Page
 */
@Preview
@Composable
fun EducationScreenPreview() {
    EducationScreen(rememberNavController())
}