package com.tpp.theperiodpurse.ui.education

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.onboarding.scaledSp
import com.tpp.theperiodpurse.ui.theme.Teal

@Composable
fun EducationDYKScreen(navController: NavHostController) {
    val interactionSource = remember { MutableInteractionSource() }
    EducationBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(modifier = Modifier
            .clickable(
                interactionSource = interactionSource, indication = null
            ) { navController.navigate(EducationNavigation.Learn.name) }
            .size(20.dp)
            .align(Alignment.Start),
            painter = painterResource(R.drawable.arrow),
            contentDescription = stringResource(R.string.back_button_label),
            tint = Teal
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.height(150.dp),
            painter = painterResource(R.drawable.dykpad),
            contentDescription = stringResource(R.string.paddy)
        )
        Text(
            modifier = Modifier.padding(24.dp),
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.did_you_know),
            fontSize = 25.scaledSp(),
        )
        Text(
            modifier = Modifier.padding(horizontal = 72.dp, vertical = 0.dp),
            textAlign = TextAlign.Center,
            lineHeight = 20.scaledSp(),
            fontSize = 20.scaledSp(),
            text = stringResource(R.string.board_game_long)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun PreviewEducationDYKScreen() {
    EducationDYKScreen(rememberNavController())
}