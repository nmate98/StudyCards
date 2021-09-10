package com.nmate.studycards.kartyaletrehozasscreen

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nmate.studycards.R
import com.nmate.studycards.Tipus
import com.nmate.studycards.adatbazis.Adatbazis
import com.nmate.studycards.modellek.Kartya
import com.nmate.studycards.modellek.Tag


@Composable
fun KartyaLetrehozasScreen(navController: NavHostController) {
    val db = Adatbazis.getInstance(LocalContext.current.applicationContext as Application).Dao
    val factory = KartyaLetrehozasScreenViewModelFactory(db)
    val viewmodel: KartyaLetrehozasScreenViewModel = viewModel(factory = factory)
    var tipus by remember { mutableStateOf(Tipus.VALASZOLOS) }
    var kerdes by remember { mutableStateOf("") }
    var helyes by remember { mutableStateOf("") }
    var hibas1 by remember { mutableStateOf("") }
    var hibas2 by remember { mutableStateOf("") }
    var hibas3 by remember { mutableStateOf("") }
    var tagValaszt by remember { mutableStateOf(false) }

    val tagek: List<Tag> by viewmodel.tagek.observeAsState(listOf())

    viewmodel.getTagek()

    if (tagValaszt) {
        Dialog(onDismissRequest = { tagValaszt = false },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)) {
            Box(Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.9f)
                .background(Color.White)) {
                Column() {
                    Text(stringResource(R.string.valassz_tageket),
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f))
                    LazyColumn(Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)) {
                        items(tagek) { item ->
                            Row() {
                                var checked by remember {
                                    mutableStateOf(viewmodel.kivalasztottTagek.value!!.contains(item.id))
                                }
                                Checkbox(checked = checked,
                                    onCheckedChange =
                                    {
                                        checked =
                                            if (viewmodel.kivalasztottTagek.value!!.contains(item.id)) {
                                                viewmodel.tagTorol(item.id!!)
                                                false
                                            } else {
                                                viewmodel.tagKivalaszt(item.id!!)
                                                true
                                            }
                                    }
                                )
                                Text(item.tag)
                            }

                        }
                        item {
                            Row() {
                                var checked by remember {
                                    mutableStateOf(false)
                                }
                                var ujTag by remember {
                                    mutableStateOf("")
                                }
                                Checkbox(checked = checked,
                                    onCheckedChange =
                                    {
                                        checked = !checked
                                        if (!checked) {
                                            ujTag = ""
                                        }
                                    }
                                )
                                TextField(value = ujTag,
                                    onValueChange = { ujTag = it },
                                    enabled = checked,
                                    modifier = Modifier.fillParentMaxWidth(0.8f))
                                Button(onClick = {
                                    viewmodel.saveTag(Tag(id = null, tag = ujTag))
                                    viewmodel.getTagek()
                                }) { Text(stringResource(R.string.mentes)) }
                            }
                        }
                    }
                    Box(Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()) {
                        Button(onClick = { tagValaszt = false },
                            Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.4f)
                                .align(Alignment.BottomEnd)) {
                            Text(stringResource(R.string.ok))
                        }
                    }
                }
            }
        }
    }

    Column() {
        TextField(value = kerdes, onValueChange = { kerdes = it }, placeholder = { Text(
                    stringResource(R.string.kerdes)) })
        Row() {
            Button(onClick = {
                tipus = if (tipus == Tipus.VALASZOLOS) {
                    Tipus.FELELETVALASZTOS
                } else {
                    Tipus.VALASZOLOS
                }
                if (tipus == Tipus.VALASZOLOS)
                    hibas1 = ""
                hibas2 = ""
                hibas3 = ""
            }) {
                Text(if (tipus == Tipus.VALASZOLOS) {
                    stringResource(R.string.feleletvalasztos)
                } else {
                    stringResource(R.string.valaszolos)
                })
            }
            Button(onClick = {
                tagValaszt = true
            }) {
                Text(stringResource(R.string.tagek_kivalasztasa))
            }
        }
        Text(stringResource(R.string.kivalasztott_tag, viewmodel.kivalasztottTagek.value!!.size))
        TextField(value = helyes,
            onValueChange = { helyes = it },
            placeholder = {
                Text(if (tipus == Tipus.VALASZOLOS) {
                    "Válasz"
                } else {
                    "Helyes megfejtés"
                })
            })
        if (tipus == Tipus.FELELETVALASZTOS) {
            TextField(value = hibas1,
                onValueChange = { hibas1 = it },
                placeholder = {
                    Text(stringResource(R.string.hibas_valasz))
                })
            TextField(value = hibas2,
                onValueChange = { hibas2 = it },
                placeholder = {
                    Text(stringResource(R.string.hibas_valasz))
                })
            TextField(value = hibas3,
                onValueChange = { hibas3 = it },
                placeholder = {
                    Text(stringResource(R.string.hibas_valasz))
                })
        }
        Button(onClick = {
            val valasz = if (tipus == Tipus.VALASZOLOS) {
                helyes
            } else {
                "$helyes**vege**$hibas1**vege**$hibas2**vege**$hibas3"
            }
            viewmodel.saveKartya(Kartya(id = null, kerdes = kerdes, tipus = tipus, valasz = valasz))
        },
            enabled = kerdes != "" && helyes != "" && ((tipus == Tipus.FELELETVALASZTOS && hibas1 != "" && hibas2 != "" && hibas3 != "") || tipus == Tipus.VALASZOLOS) && viewmodel.kivalasztottTagek.value!!.size > 0) {
            Text(stringResource(R.string.kartya_mentese))
        }
    }
}


