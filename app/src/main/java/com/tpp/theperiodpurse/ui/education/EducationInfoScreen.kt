package com.tpp.theperiodpurse.ui.education

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.ui.datasource.Product
import com.tpp.theperiodpurse.ui.datasource.ProductsList
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.onboarding.scaledSp


@Composable
fun EducationInfoScreen(
    navController: NavHostController,
    elementId: String,
) {
    val interactionSource = remember { MutableInteractionSource() }

    var product = Product()
    ProductsList.forEach {
        if (it.ProductName == elementId) {
            product = it
        }
    }

    EducationBackground()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .clickable(interactionSource = interactionSource,
                    indication = null)
                { navController.navigate(EducationNavigation.Learn.name) }
                .size(20.dp)
                .align(Alignment.Start),
            painter = painterResource(R.drawable.arrow),
            contentDescription = "Back Button",
            tint = Color(teal)
        )

        Spacer(modifier = Modifier.weight(1f))

        /*
        Product Image
         */
        Image(
            modifier = Modifier
                .height(200.dp)
                .align(Alignment.CenterHorizontally)
                .padding(12.dp),
            painter = painterResource(
                id = product.imageID
            ),
            contentDescription = "$elementId Image"
        )

        /*
        Product Name
         */
        Text(fontWeight = Bold,
            fontSize = 32.scaledSp(),
            text = product.ProductName
        )

        /*
        Product Description
         */
        Text(modifier = Modifier
            .padding(12.dp),
            textAlign = TextAlign.Center,
            text = product.description,
            fontSize = 18.scaledSp()

        )

        Spacer(modifier = Modifier.weight(45f))

    }
}

@Preview
@Composable
fun EducationInfoPreview() {
    EducationInfoScreen(rememberNavController(), "Pads")
}