package com.nmate.studycards.pontszamscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.nmate.studycards.R


@Composable
fun PontszamScreen(navController: NavHostController, osszes: Int, helyes: Int) {
    BackHandler(true) {
        navController.popBackStack("MainMenu", true)
        navController.navigate("MainMenu")
    }
    Column() {
        Text(stringResource(R.string.teljesitmenyed))
        Text("$helyes/$osszes ${helyes * 100 / osszes}%")
        Button(onClick = {
            navController.popBackStack("MainMenu", true)
            navController.navigate("MainMenu")
        }) {
            Text(stringResource(R.string.kesz))
        }
    }
}