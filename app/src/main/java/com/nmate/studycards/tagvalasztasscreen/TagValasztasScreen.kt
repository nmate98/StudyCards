package com.nmate.studycards.tagvalasztasscreen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nmate.studycards.R
import com.nmate.studycards.adatbazis.Adatbazis
import com.nmate.studycards.modellek.Tag

@Composable
fun TagValasztasScreen(navController: NavHostController) {
    val db = Adatbazis.getInstance(LocalContext.current.applicationContext as Application).Dao
    val factory = TagValasztasScreenViewModelFactory(db)
    val viewmodel: TagValasztasScreenViewModel = viewModel(factory = factory)
    val tagek: List<Tag> by viewmodel.tagek.observeAsState(listOf())
    Column(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(8.dp)) {
            items(tagek) { item ->
                listItem(viewmodel = viewmodel, item =item )
            }
        }
            enableButton(viewmodel, navController , Modifier.fillMaxWidth())
    }
}

@Composable
fun listItem(viewmodel: TagValasztasScreenViewModel, item : Tag) {
    Box(Modifier.padding(4.dp)){
        Row() {
            var checked by remember {
                mutableStateOf(false)
            }
            Checkbox(checked = checked, onCheckedChange = {
                checked =
                    if (viewmodel.kivalasztottTagek.value!!.contains(item.id)) {
                        viewmodel.torol(item.id!!)
                        false
                    } else {
                        viewmodel.kivalaszt(item.id!!)
                        true
                    }
            }, Modifier.padding(end = 4.dp))
            Text(item.tag)
        }
    }
}

@Composable
fun enableButton(viewmodel: TagValasztasScreenViewModel, navController: NavHostController, modifier: Modifier) {
    val kivalasztottTagek by viewmodel.kivalasztottTagek.observeAsState()
    TextButton(enabled = kivalasztottTagek!!.isNotEmpty(),
        onClick = {
            var arg = viewmodel.kivalasztottTagek.value!!.toString()
            arg = arg.substring(1, viewmodel.kivalasztottTagek.value!!.toString().length-1).replace(", ", "_")
            navController.navigate("Felelet/${arg}") },
    modifier = modifier) {
        Text(stringResource(R.string.kezdes))
    }
}