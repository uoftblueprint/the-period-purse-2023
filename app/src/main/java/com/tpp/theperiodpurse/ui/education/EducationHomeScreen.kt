package com.tpp.theperiodpurse.ui.education

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.datasource.Product
import com.tpp.theperiodpurse.ui.datasource.ProductsList
import com.tpp.theperiodpurse.ui.legal.TermsAndPrivacyFooter
import com.tpp.theperiodpurse.ui.onboarding.scaledSp

const val gray = 0xFF6D6E71
const val teal = 0xFF72C6B7
const val pink = 0xFFFFA3A4

@Composable
fun EducationScreenLayout(
    outController: NavHostController = rememberNavController(),
    navController: NavHostController,
) {
    val uriHandler = LocalUriHandler.current

    EducationBackground()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            state = rememberLazyGridState()
        ) {
            item(span = { GridItemSpan(2) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        DYKCard(navController)
                    }

                    Text(
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Left,
                        color = Color(gray),
                        fontWeight = FontWeight.W800,
                        fontSize = 15.scaledSp(),
                        text = "Tap to learn more about period products."
                    )
                }
            }

            items(ProductsList, span = { GridItemSpan(1) }) {
                PeriodProducts(navController, it)
            }

            item(span = { GridItemSpan(2) }) {
                Column {
                    TPPCard(uriHandler)

                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        SocialMedia(uriHandler)
                    }

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "© 2023 The Period Purse. All rights reserved.",
                        textAlign = TextAlign.Center,
                        fontSize = 15.scaledSp(),
                        color = Color.DarkGray
                    )

                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        TermsAndPrivacyFooter(outController)
                    }

                    Spacer(modifier = Modifier.size(64.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DYKCard(navController: NavHostController) {
    Card(
        modifier = Modifier.padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 10.dp,
        backgroundColor = Color(teal),
        onClick = { navController.navigate(EducationNavigation.DYK.name) },
    ) {
        Row {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(vertical = 12.dp, horizontal = 24.dp),
                    textAlign = TextAlign.Left,
                    color = Color.White,
                    fontWeight = FontWeight.W800,
                    text = "Did you know?"
                )

                Text(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 24.dp),
                    textAlign = TextAlign.Left,
                    fontSize = 13.scaledSp(),
                    maxLines = 2,
                    text = "There is a board game that educates youth ..."
                )
            }

            Image(
                modifier = Modifier
                    .height(120.dp)
                    .padding(12.dp),
                painter = painterResource(R.drawable.dykpad),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PeriodProducts(navController: NavHostController, it: Product) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .height(IntrinsicSize.Min)
            .aspectRatio(1f),
        shape = RoundedCornerShape(12.dp),
        elevation = 10.dp,
        backgroundColor = Color(pink),
        onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                key = "elementId", value = it.ProductName
            )
            navController.navigate(EducationNavigation.ProductInfo.name)
        },
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(2.dp),
                painter = painterResource(id = it.imageID),
                contentDescription = "$it Image"
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontWeight = Bold,
                fontSize = 18.scaledSp(),
                text = it.ProductName
            )
        }
    }
}

@Composable
fun TPPCard(uriHandler: UriHandler) {
    Card(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 28.dp)
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(12.dp),
        elevation = 10.dp,
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier.wrapContentSize(Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = Bold,
                text = "Learn more about The Period Purse",
                fontSize = 15.scaledSp()
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
                textAlign = TextAlign.Center,
                text = "The Period Purse strives to achieve menstrual equity by providing " + "people who menstruate with access to free menstrual products, and to " + "reduce the stigma surrounding period through public education and " + "advocacy.",
                fontSize = 13.scaledSp()
            )

            Button(modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(teal), contentColor = Color.Black
                ),
                onClick = { uriHandler.openUri("https://www.theperiodpurse.com/") }) {
                Text(text = "Visit the website", fontSize = 15.scaledSp())
            }
        }
    }
}

@Composable
fun SocialMedia(uriHandler: UriHandler) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = Modifier
            .clickable { uriHandler.openUri("https://www.instagram.com/theperiodpurse/") }
            .padding(horizontal = 8.dp)
            .size(24.dp),
            painter = painterResource(R.drawable.instagram),
            contentDescription = "Instagram",
            tint = Color(teal))

        Icon(modifier = Modifier
            .clickable { uriHandler.openUri("https://www.tiktok.com/@theperiodpurse") }
            .padding(horizontal = 8.dp)
            .size(24.dp),
            painter = painterResource(R.drawable.tiktok),
            contentDescription = "Tik Tok",
            tint = Color(teal))

        Icon(modifier = Modifier
            .clickable {
                uriHandler.openUri(
                    "https://www.youtube.com/channel/" + "UC2YgDU_9XxbjJsGGvXwxwyA"
                )
            }
            .padding(horizontal = 8.dp)
            .size(24.dp),
            painter = painterResource(R.drawable.youtube),
            contentDescription = "YouTube",
            tint = Color(teal))

        Icon(modifier = Modifier
            .clickable { uriHandler.openUri("https://twitter.com/ThePeriodPurse") }
            .padding(horizontal = 8.dp)
            .size(24.dp),
            painter = painterResource(R.drawable.twitter),
            contentDescription = "Twitter",
            tint = Color(teal))

        Icon(modifier = Modifier
            .clickable { uriHandler.openUri("https://www.facebook.com/theperiodpurse") }
            .padding(horizontal = 8.dp)
            .size(24.dp),
            painter = painterResource(R.drawable.facebook),
            contentDescription = "Facebook",
            tint = Color(teal))
    }
}

@Preview
@Composable
fun EducationScreenPreview() {
    EducationScreenLayout(rememberNavController(), rememberNavController())
}