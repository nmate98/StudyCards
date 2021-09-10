package com.nmate.studycards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.util.*

@Composable
fun MainMenu(navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {
        Box(modifier = Modifier
            .weight(0.1f)
            .fillMaxWidth()) {
        }
        Box(modifier = Modifier
            .weight(0.5f)
            .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(stringResource(id = R.string.mainmenutitle),
                fontSize = 72.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()) {
            Button(onClick = {navController.navigate("TagValaszt")},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(0f)),
                elevation = ButtonDefaults.elevation(0.dp)) {
                Text(stringResource(id = R.string.kezd_button))
            }
            Button(onClick = {navController.navigate("Letrehozas")},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(0f)),
                elevation = ButtonDefaults.elevation(0.dp)) {
                Text(stringResource(id = R.string.kartya_letrehozasa))
            }
        }
    }
}