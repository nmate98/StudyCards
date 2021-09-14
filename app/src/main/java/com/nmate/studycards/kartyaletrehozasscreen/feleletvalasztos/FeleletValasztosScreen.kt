package com.nmate.studycards.kartyaletrehozasscreen.feleletvalasztos

import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nmate.studycards.R
import com.nmate.studycards.Tipus
import com.nmate.studycards.adatbazis.Adatbazis
import com.nmate.studycards.dialogs.kilepesDialog
import com.nmate.studycards.dialogs.tagValaszt
import com.nmate.studycards.kartyaletrehozasscreen.KartyaLetrehozasScreenViewModel
import com.nmate.studycards.kartyaletrehozasscreen.KartyaLetrehozasScreenViewModelFactory
import com.nmate.studycards.modellek.Kartya
import com.nmate.studycards.modellek.Tag

@Composable
fun FeleletValasztosScreen(navController: NavHostController) {
    val db = Adatbazis.getInstance(LocalContext.current.applicationContext as Application).Dao
    val factory = KartyaLetrehozasScreenViewModelFactory(db)
    val viewmodel: KartyaLetrehozasScreenViewModel = viewModel(factory = factory)
    Screen(navController, viewmodel)
}

@Composable
fun Screen(navController: NavHostController, viewmodel: KartyaLetrehozasScreenViewModel) {
    var kerdes by remember { mutableStateOf("") }
    var helyes = remember { mutableStateOf("") }
    var hibas1 = remember { mutableStateOf("") }
    var hibas2 = remember { mutableStateOf("") }
    var hibas3 = remember { mutableStateOf("") }
    var tagValaszt = remember { mutableStateOf(false) }
    val tagek: List<Tag> by viewmodel.tagek.observeAsState(listOf())
    var alertDialog = remember { mutableStateOf(false) }

    BackHandler(true) {
        alertDialog.value = true
    }

    if (alertDialog.value) {
        kilepesDialog(alertDialog, navController)
    }

    if (tagValaszt.value) {
        tagValaszt(tagValaszt, viewmodel, tagek)
    }

    Column() {
        Card(Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(16.dp), elevation = 8.dp) {
            Box(Modifier.padding(16.dp)) {
                TextField(value = kerdes, onValueChange = { kerdes = it }, placeholder = {
                    Text(
                        stringResource(R.string.kerdes))
                }, modifier = Modifier
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(0f)),
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
                valaszKartya(helyes, true)
            }
            Box(Modifier
                .padding(8.dp, 2.dp, 8.dp, 2.dp)
                .fillMaxWidth()) {
                valaszKartya(hibas1, false)
            }
            Box(Modifier
                .padding(8.dp, 2.dp, 8.dp, 2.dp)
                .fillMaxWidth()) {
                valaszKartya(hibas2, false)
            }
            Box(Modifier
                .padding(8.dp, 2.dp, 8.dp, 8.dp)
                .fillMaxWidth()) {
                valaszKartya(hibas3, false)
            }
        }
        TextButton(onClick = {
            val valasz =
                "${helyes.value}**vege**${hibas1.value}**vege**${hibas2.value}**vege**${hibas3.value}"
            viewmodel.saveKartya(Kartya(id = null,
                kerdes = kerdes,
                tipus = Tipus.FELELETVALASZTOS,
                valasz = valasz))
            kerdes = ""
            helyes.value = ""
            hibas1.value = ""
            hibas2.value = ""
            hibas3.value = ""
            viewmodel.tagListaUrit()
        },modifier = Modifier.fillMaxWidth(),
            enabled = kerdes != "" && helyes.value != "" && hibas1.value != "" && hibas2.value != "" && hibas3.value != "" && viewmodel.kivalasztottTagek.value!!.size > 0) {
            Text(stringResource(R.string.kartya_mentese))
        }
    }
}

@Composable
fun valaszKartya(text: MutableState<String>, helyes: Boolean) {
    Card(Modifier
        .padding(8.dp)
        .fillMaxWidth(), elevation = 8.dp) {
        Box(Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp)) {
            TextField(value = text.value,
                onValueChange = { text.value = it },
                placeholder = {
                    if (helyes) {
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