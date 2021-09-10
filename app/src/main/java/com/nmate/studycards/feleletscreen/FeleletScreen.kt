package com.nmate.studycards.feleletscreen

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nmate.studycards.R
import com.nmate.studycards.Tipus
import com.nmate.studycards.adatbazis.Adatbazis

@Composable
fun FeleletScreen(id: String) {
    val db = Adatbazis.getInstance(LocalContext.current.applicationContext as Application).Dao
    val factory = FeleletScreenViewModelFactory(db)
    val viewModel: FeleletScreenViewModel = viewModel(factory = factory)
    val tagId by remember { mutableStateOf(id.split("_")) }
    viewModel.getKerdesek(tagId)
    Screen(viewModel)

}

@Composable
fun Screen(viewModel: FeleletScreenViewModel) {
    val bevittValasz by viewModel.bevittValasz.observeAsState()
    val helyes by viewModel.helyes.observeAsState()
    var kovetkezo by remember { mutableStateOf(false) }
    val aktualisKerdes by viewModel.aktualisKerdes.observeAsState()
    Column() {
        Card(Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(16.dp)) {
            Box(Modifier.fillMaxSize()) {
                Text(aktualisKerdes!!.kerdes!!, modifier = Modifier.align(Alignment.Center))
            }
        }
        if (aktualisKerdes!!.tipus == Tipus.VALASZOLOS) {
            valaszTextField(viewModel)
        } else {
            feleletValasztoMezok(viewModel)
        }
        buttons(viewModel)
    }
}

@Composable
fun valaszTextField(viewModel: FeleletScreenViewModel) {
    val bevittValasz by viewModel.bevittValasz.observeAsState()
    TextField(value = bevittValasz!!, onValueChange = { viewModel.setBevittValasz(it) })
}

@Composable
fun feleletValasztoMezok(viewModel: FeleletScreenViewModel) {
    Column() {
        Row() {
            Box(Modifier
                .weight(0.5f)
                .fillMaxHeight(0.5f)) {
                valaszKartya(viewModel, ssz = 0)
            }
            Box(Modifier
                .weight(0.5f)
                .fillMaxHeight(0.5f)) {
                valaszKartya(viewModel, ssz = 1)
            }
        }
        Row() {
            Box(Modifier
                .weight(0.5f)
            ) {
                valaszKartya(viewModel, ssz = 2)
            }
            Box(Modifier
                .weight(0.5f)
            ) {
                valaszKartya(viewModel, ssz = 3)
            }
        }
    }
}

@Composable
fun valaszKartya(viewModel: FeleletScreenViewModel, ssz: Int) {
    Card(Modifier
        .fillMaxHeight(0.1f)) {
        val bevittValasz by viewModel.bevittValasz.observeAsState()
        val kesz by viewModel.kesz.observeAsState()
        val text by remember { mutableStateOf(viewModel.kevertValaszok.value!![ssz]) }
        Log.d("TAG", "valaszKartya: $ssz $kesz")
        Text(text,
            Modifier
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
                })
                .fillMaxSize()
        )
    }
}

@Composable
fun buttons(viewModel: FeleletScreenViewModel) {
    val kesz by viewModel.kesz.observeAsState()
    val bevittValasz by viewModel.bevittValasz.observeAsState()
    if (!kesz!!) {
        Button(onClick = {
            viewModel.ellenorzes()
            viewModel.setKesz(true)
        },
            enabled = !bevittValasz.equals("")) {
            Text(stringResource(R.string.ellenorzes))
        }
    } else {
        Button(onClick = {
            viewModel.leptetes()
            viewModel.setKesz(false)
            viewModel.setBevittValasz("")
        }) {
            Text(stringResource(R.string.kovetkezo))
        }
    }
}