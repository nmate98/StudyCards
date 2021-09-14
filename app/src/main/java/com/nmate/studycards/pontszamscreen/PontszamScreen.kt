package com.nmate.studycards.pontszamscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nmate.studycards.R

@Composable
fun PontszamScreen(navController: NavHostController, osszes: Int, helyes: Int) {
    BackHandler(true) {
        navController.popBackStack("MainMenu", true)
        navController.navigate("MainMenu")
    }
    Box(Modifier.fillMaxSize()){
        Column(Modifier.align(Alignment.Center)) {
            Text(stringResource(R.string.teljesitmenyed), Modifier.padding(16.dp).fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 20.sp)
            Text("$helyes/$osszes ${helyes * 100 / osszes}%", Modifier.padding(16.dp).fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 20.sp)
            TextButton(onClick = {
                navController.popBackStack("MainMenu", true)
                navController.navigate("MainMenu")
            }, Modifier.padding(16.dp).fillMaxWidth()) {
                Text(stringResource(R.string.kesz), fontSize = 20.sp)
            }
        }
    }

}