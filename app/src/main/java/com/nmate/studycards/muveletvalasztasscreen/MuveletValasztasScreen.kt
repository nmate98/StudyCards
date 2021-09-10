package com.nmate.studycards.muveletvalasztasscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.nmate.studycards.R

@Composable
fun MuveletValasztasScreen(navController: NavHostController) {
    Column() {
        Button(onClick = {navController.navigate("Letrehozas")}){
            Text(stringResource(R.string.kartya_letrehozasa))
        }
        Button(onClick = {navController.navigate("TagValaszt")}){
            Text(stringResource(R.string.kartya_modositasa))
        }
    }
}