package com.nmate.studycards.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.nmate.studycards.R
import com.nmate.studycards.kartyaletrehozasscreen.KartyaLetrehozasScreenViewModel
import com.nmate.studycards.modellek.Tag

@Composable
fun kilepesDialog(alertDialog: MutableState<Boolean>, navController: NavHostController) {
    AlertDialog(onDismissRequest = { alertDialog.value = false },
        dismissButton = {
            Button(onClick = {
                alertDialog.value = false
            }) { Text(stringResource(R.string.nem)) }
        },
        confirmButton = {
            Button(onClick = {
                alertDialog.value = false
                navController.popBackStack("MainMenu", true)
                navController.navigate("MainMenu")
            }) { Text(stringResource(R.string.igen)) }

        },
        title = { Text(stringResource(R.string.kilepes)) },
        text = { Text(stringResource(R.string.biztosan_kilepsz)) })
}

@Composable
fun tagValaszt(tagValaszt : MutableState<Boolean> , viewmodel : KartyaLetrehozasScreenViewModel, tagek : List<Tag>) {
    viewmodel.tagMasol()
    Dialog(onDismissRequest = {
        tagValaszt.value = false
        viewmodel.tagVisszaallit()
    },
        properties = DialogProperties(dismissOnBackPress = true,
            dismissOnClickOutside = false)) {
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
//                                    viewmodel.getTagek()
                            }) { Text(stringResource(R.string.mentes)) }
                        }
                    }
                }
                Box(Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                    Button(onClick = { tagValaszt.value = false },
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

@Composable
fun kartyaTipusValasztasDialog(navController: NavHostController, mutat : MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {mutat.value = false},
        title = {Text(stringResource(R.string.mod_valasztasa))},
        text = {Text(stringResource(R.string.tipus_kerdes))},
        buttons = {
            Row(){
                TextButton(onClick = { mutat.value = false}) {
                    Text(stringResource(R.string.megse))
                }
                TextButton(onClick = { mutat.value = false
                navController.navigate("FeleletValasztos")}) {
                    Text(stringResource(R.string.feleletvalasztos))
                }
                TextButton(onClick = { mutat.value = false
                    navController.navigate("Valaszolos")}) {
                    Text(stringResource(R.string.valaszolos))
                }
            }
        }
    )
}