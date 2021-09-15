package com.nmate.studycards.dialogs

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.nmate.studycards.R
import com.nmate.studycards.kartyaletrehozasscreen.KartyaLetrehozasScreenViewModel
import com.nmate.studycards.kartyamodositscreen.KartyaModositScreenViewModel
import com.nmate.studycards.kartyamodositvalaszto.KartyaModositValasztoScreenViewModel
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

@ExperimentalComposeUiApi
@Composable
fun KartyaLetrehozTagValaszt(
    tagValaszt: MutableState<Boolean>,
    viewmodel: KartyaLetrehozasScreenViewModel
) {
    val tagek by viewmodel.tagek.observeAsState()
    Log.d("TAG", "KartyaLetrehozTagValaszt: $tagek")
    viewmodel.tagMasol()
    Dialog(onDismissRequest = {
        tagValaszt.value = false
        viewmodel.tagVisszaallit()
    },
        properties = DialogProperties(dismissOnBackPress = true,
            dismissOnClickOutside = false, usePlatformDefaultWidth = false)) {
        Box(Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(0.9f)
            .background(Color.White)) {
            Column(Modifier.fillMaxSize()) {
                felsoText()
                LazyColumn(Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)) {
                    items(tagek!!) { item ->
                        listItem(item,
                            viewmodel.kivalasztottTagek.value!!,
                            torol = { id -> viewmodel.tagTorol(id) },
                            kivalaszt = { id -> viewmodel.tagKivalaszt(id) })
                    }
                    item {
                        ujTagMezo(Modifier.fillParentMaxWidth(0.6f)
                        ) { tag: Tag -> viewmodel.saveTag(tag)
                        viewmodel.getTagek()}
                    }
                }
                mentesGomb(tagValaszt)
            }

        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun KartyaModositTagValaszt(
    tagValaszt: MutableState<Boolean>,
    viewmodel: KartyaModositScreenViewModel,
) {
    val tagek by viewmodel.tagek.observeAsState()
    viewmodel.tagMasol()
    Dialog(onDismissRequest = {
        tagValaszt.value = false
        viewmodel.tagVisszaallit()
    },
        properties = DialogProperties(dismissOnBackPress = true,
            dismissOnClickOutside = false, usePlatformDefaultWidth = false)) {
        Box(Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(0.9f)
            .background(Color.White)) {
            felsoText()
            LazyColumn(Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)) {
                items(tagek!!) { item ->
                    listItem(item,
                        viewmodel.kivalasztottTagek.value!!,
                        torol = { id -> viewmodel.tagTorol(id) },
                        kivalaszt = { id -> viewmodel.tagKivalaszt(id) })
                }
                item {
                    ujTagMezo(Modifier.fillParentMaxWidth(0.6f)
                    ) { tag: Tag -> viewmodel.saveTag(tag)
                        viewmodel.getTagek()}
                }
            }
            mentesGomb(tagValaszt)
        }
    }
}

@Composable
fun felsoText() {
    Box(Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.1f)) {
        Text(stringResource(R.string.valassz_tageket),
            Modifier.align(Alignment.Center))
    }
}

@Composable
fun listItem(
    item: Tag,
    kivalasztottTagek: List<Long>,
    torol: (Long) -> Unit,
    kivalaszt: (Long) -> Unit,
) {
    var checked by remember {
        mutableStateOf(kivalasztottTagek.contains(item.id))
    }
        Row(Modifier
            .height(50.dp)
            .fillMaxWidth()
            .clickable {
                checked = !checked
            }) {
            Box(){
                Checkbox(checked = checked,
                    onCheckedChange =
                    {
                        if(checked){
                            torol(item.id!!)
                        }
                        else{
                            kivalaszt(item.id!!)
                        }
                        checked = !checked
                    },
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.Center)
                )
            }
            Box(){
                Text(item.tag,
                    Modifier
                        .padding(0.dp, 8.dp, 8.dp, 8.dp)
                        .align(Alignment.Center))
            }
        }
}

@Composable
fun ujTagMezo(modifier: Modifier, onClick: (Tag) -> Unit) {
        Row(Modifier
            .fillMaxWidth()
            .height(100.dp)) {
            var checked by remember {
                mutableStateOf(false)
            }
            var ujTag by remember {
                mutableStateOf("")
            }
            Box(){
                Checkbox(checked = checked,
                    onCheckedChange =
                    {
                        checked = !checked
                        if (!checked) {
                            ujTag = ""
                        }
                    },
                    Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )
            }
            Box(Modifier.weight(0.8f)){
                TextField(value = ujTag,
                    onValueChange = { ujTag = it },
                    enabled = checked,
                    modifier = modifier
                        .align(Alignment.Center),
                    colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White.copy(0f)))
            }
            Box(Modifier
                .weight(0.2f)
                .fillMaxHeight()){
                TextButton(enabled = checked, onClick = { onClick(Tag(null, ujTag)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) { Image(painter = painterResource(id = R.drawable.ic_baseline_check_24),
                    contentDescription = null ) }
            }
        }

}

@Composable
fun mentesGomb(tagValaszt: MutableState<Boolean>) {
    Box(Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        TextButton(onClick = { tagValaszt.value = false },
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()) {
            Text(stringResource(R.string.ok))
        }
    }
}


@Composable
fun kartyaTipusValasztasDialog(navController: NavHostController, mutat: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = { mutat.value = false },
        title = { Text(stringResource(R.string.mod_valasztasa)) },
        text = { Text(stringResource(R.string.tipus_kerdes)) },
        buttons = {
            Row() {
                TextButton(onClick = { mutat.value = false }) {
                    Text(stringResource(R.string.megse))
                }
                TextButton(onClick = {
                    mutat.value = false
                    navController.navigate("FeleletValasztos")
                }) {
                    Text(stringResource(R.string.feleletvalasztos))
                }
                TextButton(onClick = {
                    mutat.value = false
                    navController.navigate("Valaszolos")
                }) {
                    Text(stringResource(R.string.valaszolos))
                }
            }
        }
    )
}

@ExperimentalComposeUiApi
@Composable
fun KartyaModositTagValaszt(
    viewmodel: KartyaModositValasztoScreenViewModel,
    mutat: MutableState<Boolean>,
) {
    val kivalasztottID by viewmodel.kivalasztottTag.observeAsState()
    Dialog(onDismissRequest = { mutat.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(Modifier
            .background(Color.White)
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.5f)) {
            LazyColumn() {
                itemsIndexed(viewmodel.tagek.value!!) { id, item ->
                    if (id != 0 && id != kivalasztottID) {
                        Box(Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                viewmodel.kivalaszt(id)
                                mutat.value = false
                            }) {
                            Text(item.tag,
                                Modifier.align(Alignment.Center))
                        }

                        Divider(color = Color.LightGray)
                    }
                }
            }
        }
    }
}
