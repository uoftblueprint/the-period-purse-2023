package com.example.theperiodpurse.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.MainActivity
import com.example.theperiodpurse.R


@Composable
fun LoadingScreen(modifier: Modifier = Modifier, mainActivity: MainActivity) {

    val configuration = LocalConfiguration.current

    val screenwidth = configuration.screenWidthDp;

    val screenheight = configuration.screenHeightDp;

    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )


    Box(
        modifier = Modifier
            .padding((screenheight * 0.02).dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        
    ) {

        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier
                .size((screenheight * 0.45).dp)
                .align(Alignment.Center),
        )
        
    }
    
    
}

@Preview
@Composable
fun previewLoading(){
    LoadingScreen(mainActivity = MainActivity())
}