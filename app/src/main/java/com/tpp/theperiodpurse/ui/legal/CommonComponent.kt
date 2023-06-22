package com.tpp.theperiodpurse.ui.legal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tpp.theperiodpurse.ui.onboarding.scaledSp

@Composable
fun Body(str: String) {
    Text(
        fontSize = 14.scaledSp(),
        lineHeight = 16.scaledSp(),
        text = str
    )
}


@Composable
fun Header(str: String) {
    Column {
        Spacer(modifier = Modifier.size(12.dp))

        Text(
            modifier = Modifier.padding(vertical = 2.dp),
            fontSize = 14.scaledSp(),
            fontWeight = FontWeight.Bold,
            lineHeight = 24.scaledSp(),
            text = str
        )
    }
}

@Composable
fun Title(str:String) {
    Text(
        fontSize = 32.scaledSp(),
        fontWeight = FontWeight.Bold,
        text = str
    )
}