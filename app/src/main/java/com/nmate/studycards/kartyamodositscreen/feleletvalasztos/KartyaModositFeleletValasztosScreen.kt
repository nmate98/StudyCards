package com.nmate.studycards.kartyamodositscreen.feleletvalasztos

import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nmate.studycards.R
import com.nmate.studycards.adatbazis.Adatbazis
import com.nmate.studycards.dialogs.KartyaModositTagValaszt
import com.nmate.studycards.dialogs.kilepesDialog
import com.nmate.studycards.kartyamodositscreen.KartyaModositScreenViewModel
import com.nmate.studycards.kartyamodositscreen.KartyaModositScreenViewModelFactory
import com.nmate.studycards.modellek.Tag

@ExperimentalComposeUiApi
@Composable
fun KartyaModositFeleletValasztosScreen(navController: NavHostController, id: Long) {
    val db = Adatbazis.getInstance(LocalContext.current.applicationContext as Application).Dao
    val factory = KartyaModositScreenViewModelFactory(db, id)
    val viewmodel: KartyaModositScreenViewModel = viewModel(factory = factory)
    Screen(navController, viewmodel)

}

@ExperimentalComposeUiApi
@Composable
fun Screen(navController: NavHostController, viewmodel: KartyaModositScreenViewModel) {
    val tagValaszt = remember { mutableStateOf(false) }
    val kartya by viewmodel.kartya.observeAsState()
    val valaszok = kartya!!.valasz!!.split("**vege**")
    val tagek: List<Tag> by viewmodel.tagek.observeAsState(listOf())
    val alertDialog = remember { mutableStateOf(false) }

    BackHandler(true) {
        alertDialog.value = true
    }

    if (alertDialog.value) {
        kilepesDialog(alertDialog, navController)
    }

    if (tagValaszt.value) {
        KartyaModositTagValaszt(tagValaszt, viewmodel)
    }

    Column() {
        Card(Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(16.dp), elevation = 8.dp) {
            Box(Modifier.padding(16.dp)) {
                TextField(value = kartya!!.kerdes!!,
                    onValueChange = { viewmodel.setKerdes(it) },
                    placeholder = {
                        Text(
                            stringResource(R.string.kerdes))
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White.copy(
                        0f)))
            }
        }

        Row(Modifier.fillMaxHeight(0.1f)) {
            Box(Modifier
                .weight(0.5f)
                .fillMaxSize()) {
                TextButton(onClick = {
                    tagValaszt.value = true
                },

                    modifier = Modifier.align(Alignment.Center)) {
                    Text(stringResource(R.string.valassz_tageket))
                }
            }
            Box(Modifier
                .weight(0.5f)
                .fillMaxSize()) {
                Text(stringResource(R.string.kivalasztott_cimke,
                    viewmodel.kivalasztottTagek.value!!.size), Modifier.align(Alignment.Center))
            }
        }

        Column(Modifier
            .fillMaxHeight(0.85f)
            .fillMaxWidth()) {

            Box(Modifier
                .padding(8.dp, 8.dp, 8.dp, 2.dp)
                .fillMaxWidth()) {
                valaszKartya(valaszok[0], viewmodel, 0)
            }
            Box(Modifier
                .padding(8.dp, 2.dp, 8.dp, 2.dp)
                .fillMaxWidth()) {
                valaszKartya(valaszok[1], viewmodel, 1)
            }
            Box(Modifier
                .padding(8.dp, 2.dp, 8.dp, 2.dp)
                .fillMaxWidth()) {
                valaszKartya(valaszok[2], viewmodel, 2)
            }
            Box(Modifier
                .padding(8.dp, 2.dp, 8.dp, 8.dp)
                .fillMaxWidth()) {
                valaszKartya(valaszok[3], viewmodel, 3)
            }
        }
    Row(){
        Box(Modifier.weight(0.5f)){
            TextButton(onClick = { viewmodel.updateKartya()
                navController.popBackStack("KartyaModositValaszt", true)
                navController.navigate("KartyaModositValaszt")}, Modifier.align(Alignment.Center)) {
                Text(stringResource(R.string.mentes))
            }
        }
        Box(Modifier.weight(0.5f)){
            TextButton(onClick = { viewmodel.torolKartya()
                navController.popBackStack("KartyaModositValaszt", true)
                navController.navigate("KartyaModositValaszt")}, Modifier.align(Alignment.Center)) {
                Text(stringResource(R.string.kartya_torlese))
            }
        }
    }


    }

}

@Composable
fun valaszKartya(text: String, viewmodel: KartyaModositScreenViewModel, poz: Int) {
    Card(Modifier
        .padding(8.dp)
        .fillMaxWidth(), elevation = 8.dp) {
        Box(Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp)) {
            TextField(value = text,
                onValueChange = { viewmodel.setFVValasz(it, poz) },
                placeholder = {
                    if (poz == 0) {
                        Text(stringResource(R.string.helyes_valasz))
                    } else {
                        Text(stringResource(R.string.hibas_valasz))
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White.copy(
                    0f)), modifier = Modifier.fillMaxWidth())
        }
    }
}