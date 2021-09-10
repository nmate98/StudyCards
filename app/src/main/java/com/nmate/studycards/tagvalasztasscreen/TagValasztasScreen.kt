package com.nmate.studycards.tagvalasztasscreen

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
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
    var enableButton : Boolean by remember { mutableStateOf(viewmodel.kivalasztottTagek.value!!.isNotEmpty())}
    viewmodel.getTagek()
    Column(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(8.dp)) {
            items(tagek) { item ->
                Row() {
                    var checked by remember {
                        mutableStateOf(false)
                    }
                    Checkbox(checked = checked, onCheckedChange = {
                        checked =
                            if (viewmodel.kivalasztottTagek.value!!.contains(item.id)) {
                                viewmodel.torol(item.id!!)
                                enableButton = viewmodel.kivalasztottTagek.value!!.isNotEmpty()
                                false
                            } else {
                                viewmodel.kivalaszt(item.id!!)
                                enableButton = viewmodel.kivalasztottTagek.value!!.isNotEmpty()
                                true
                            }
                    })
                    Text(item.tag)
                }
            }
        }
        Button(enabled = enableButton,
            onClick = {
                var arg = viewmodel.kivalasztottTagek.value!!.toString()
                arg = arg.substring(1, viewmodel.kivalasztottTagek.value!!.toString().length-1).replace(", ", "_")
                navController.navigate("Felelet/${arg}") }) {
            Text(stringResource(R.string.kezdes))
        }
    }


}