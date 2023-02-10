package com.example.theperiodpurse.ui.education

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Card(
            modifier = Modifier.height(IntrinsicSize.Min)
                .padding(8.dp),
            backgroundColor = Color.Gray,
            onClick = { navController.navigate(Destination.DYK.route) },
        ) {
            Text("Did you know?")
        }

        Text(text="Tap to learn more about period products.")
//    Grid of period products
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(products) {
                Card(
                    modifier = Modifier.padding(8.dp)
                        .height(IntrinsicSize.Min)
                        .weight(1f),
                    shape = RoundedCornerShape(12.dp), elevation = 10.dp,
                    backgroundColor = Color.Green,
                    onClick = { navController.navigate(Destination.Info.createRoute(it)) },
                ) {
                    Text(
                        modifier = Modifier.weight(1f)
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = it
                    )
                }
            }
        }

        ClickableText(
            text = AnnotatedString("Terms and Conditions"),
            onClick = { navController.navigate(Destination.Terms.route) }
        )

        ClickableText(
            text = AnnotatedString("Privacy Policy"),
            onClick = { navController.navigate(Destination.Privacy.route) }
        )
//    Period Purse Info

//    Social Media Buttons

//    T&C, Privacy Policy
    }

}
