package com.example.theperiodpurse.ui.education

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.data.Product
import com.example.theperiodpurse.data.ProductsList

@Composable
fun EducationInfoScreen(
    elementId: String,
) {
    var product = Product()
    ProductsList.forEach {
        if (it.ProductName == elementId) {
            product = it
        }
    }

    EducationBackground()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceEvenly
    ) {
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
            text = product.ProductName
        )

        /*
        Product Description
         */
        Text(modifier = Modifier
            .padding(12.dp),
            textAlign = TextAlign.Center,
            text = product.description
        )

    }
}

@Preview
@Composable
fun EducationInfoPreview() {
    EducationInfoScreen("Menstrual Cup")
}