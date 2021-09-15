package com.nmate.studycards.feleletscreen

import android.app.Application
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun FeleletScreen(id: String, navController: NavHostController) {
    val db = Adatbazis.getInstance(LocalContext.current.applicationContext as Application).Dao
    val factory = FeleletScreenViewModelFactory(db, id)
    val viewModel: FeleletScreenViewModel = viewModel(factory = factory)
    Screen(viewModel, navController)
}

@Composable
fun Screen(viewModel: FeleletScreenViewModel, navController: NavHostController) {
    val bevittValasz by viewModel.bevittValasz.observeAsState()
    val helyes by viewModel.helyes.observeAsState()
    var kovetkezo by remember { mutableStateOf(false) }
    val aktualisKerdes by viewModel.aktualisKerdes.observeAsState()

    BackHandler(false) {
    }
    Column() {
        Box(Modifier.fillMaxHeight(0.1f)) {
            felsoSor(viewModel, navController)
        }
        Card(Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(16.dp),
            elevation = 8.dp) {
            Box(Modifier.fillMaxSize()) {
                Text(aktualisKerdes!!.kerdes!!, modifier = Modifier.align(Alignment.Center))
            }
        }
        if (aktualisKerdes!!.tipus == Tipus.VALASZOLOS) {
            Card(Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
                .padding(16.dp),
                elevation = 8.dp) {
                Box(Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {
                    valaszTextField(viewModel)
                }
            }
        } else {
            feleletValasztoMezok(viewModel, Modifier.fillMaxHeight(0.5f))
        }
        Box(Modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
            .padding(16.dp)) {
            gombok(viewModel, navController, Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun felsoSor(viewModel: FeleletScreenViewModel, navController: NavHostController) {
    val sorszam by viewModel.ssz.observeAsState()
    Row(Modifier
        .fillMaxSize()) {
        Text(stringResource(id = R.string.kerdesszam,
            (sorszam!! + 1),
            viewModel.kartyak.value!!.size), Modifier.weight(0.5f))
        TextButton(onClick = { navController.navigate("Pontszam/${sorszam!! + 1}/${viewModel.helyes.value}") },
            Modifier.weight(0.5f)) {
            Text(stringResource(R.string.befejezes))
        }
    }
}

@Composable
fun valaszTextField(viewModel: FeleletScreenViewModel) {
    val bevittValasz by viewModel.bevittValasz.observeAsState()
    TextField(value = bevittValasz!!,
        onValueChange = { viewModel.setBevittValasz(it) },
        modifier = Modifier.fillMaxSize(),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White.copy(0f)))
}

@Composable
fun feleletValasztoMezok(viewModel: FeleletScreenViewModel, modifier: Modifier) {
    Column(modifier = modifier) {
        Row(Modifier
            .padding(16.dp, 16.dp, 16.dp, 8.dp)
            .weight(0.5f)) {
            Box(Modifier
                .weight(0.5f)
                .padding(end = 8.dp)) {
                valaszKartya(viewModel, ssz = 0)
            }
            Box(Modifier
                .weight(0.5f)
                .padding(start = 8.dp)
            ) {
                valaszKartya(viewModel, ssz = 1)
            }
        }
        Row(Modifier
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
            .weight(0.5f)) {
            Box(Modifier
                .weight(0.5f)
                .padding(end = 8.dp)
            ) {
                valaszKartya(viewModel, ssz = 2)
            }
            Box(Modifier
                .weight(0.5f)
                .padding(start = 8.dp)
            ) {
                valaszKartya(viewModel, ssz = 3)
            }
        }
    }
}

@Composable
fun valaszKartya(viewModel: FeleletScreenViewModel, ssz: Int) {
    Card(Modifier
        .fillMaxSize(),
        elevation = 8.dp) {
        val bevittValasz by viewModel.bevittValasz.observeAsState()
        val kesz by viewModel.kesz.observeAsState()
        val text by remember { mutableStateOf(viewModel.kevertValaszok.value!![ssz]) }
        Box(Modifier
            .clickable(enabled = !kesz!!, onClick = {
                viewModel.setBevittValasz(text)
            })
            .background(if (!kesz!!) {
                when (bevittValasz) {
                    text -> Color(0xffffa500)
                    else -> Color.White
                }
            } else {
                when (bevittValasz) {
                    text -> if (viewModel.osszehasonlitHelyesValasszal(text)) {
                        Color.Green
                    } else {
                        Color.Red
                    }
                    else -> if (viewModel.osszehasonlitHelyesValasszal(text)) {
                        Color.Green
                    } else {
                        Color.White
                    }
                }
            })) {

            Text(text,
                Modifier
                    .align(Alignment.Center)
                    .background(Color.White.copy(0f))
            )
        }
    }
}

@Composable
fun gombok(
    viewModel: FeleletScreenViewModel,
    navController: NavHostController,
    modifier: Modifier,
) {
    val kesz by viewModel.kesz.observeAsState()
    val bevittValasz by viewModel.bevittValasz.observeAsState()
    var vege by remember {
        mutableStateOf(false)
    }
    if (!kesz!!) {
        TextButton(onClick = {
            viewModel.ellenorzes()
            viewModel.setKesz(true)
        },
            enabled = !bevittValasz.equals(""),
            modifier = modifier) {
            Text(stringResource(R.string.ellenorzes))
        }
    } else {
        val helyes by viewModel.helyes.observeAsState()
        TextButton(onClick = {
            vege = viewModel.leptetes()
            if (vege) {
                navController.navigate("Pontszam/${viewModel.kartyak.value!!.size}/$helyes")
            } else {
                viewModel.setKesz(false)
                viewModel.setBevittValasz("")
            }

        },
            modifier = modifier) {
            Text(if (!vege) {
                stringResource(R.string.kovetkezo)
            } else {
                stringResource(R.string.vege)
            })
        }
    }
}