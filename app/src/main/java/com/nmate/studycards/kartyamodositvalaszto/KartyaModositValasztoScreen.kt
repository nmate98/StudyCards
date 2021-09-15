package com.nmate.studycards.kartyamodositvalaszto

import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nmate.studycards.Tipus
import com.nmate.studycards.adatbazis.Adatbazis
import com.nmate.studycards.dialogs.KartyaModositTagValaszt
import com.nmate.studycards.dialogs.kilepesDialog
import com.nmate.studycards.modellek.Kartya

@ExperimentalComposeUiApi
@Composable
fun KartyaModositValasztoScreen(navController: NavHostController) {
    val db = Adatbazis.getInstance(LocalContext.current.applicationContext as Application).Dao
    val factory = KartyaModositValasztoScreenViewModelFactory(db)
    val viewmodel: KartyaModositValasztoScreenViewModel = viewModel(factory = factory)
    val alertDialog = remember {mutableStateOf(false)}

    val listaNyitva = remember{ mutableStateOf(false)}


    BackHandler(true) {
        alertDialog.value = true
    }

    if(alertDialog.value){
        kilepesDialog(alertDialog , navController )
    }

    if(listaNyitva.value){
        KartyaModositTagValaszt(viewmodel, listaNyitva )
    }

    Screen(viewmodel, navController, listaNyitva)

}

@Composable
fun Screen(viewmodel: KartyaModositValasztoScreenViewModel, navController: NavHostController, listaNyitva : MutableState<Boolean>) {
    val kivalasztottTag by viewmodel.kivalasztottTag.observeAsState()
    val tagek by viewmodel.tagek.observeAsState()
    val kartyak by viewmodel.kartyak.observeAsState()
    Column(Modifier.fillMaxSize()){
        Box(Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.05f)
            .clickable { listaNyitva.value = true }){
            Text(tagek!![kivalasztottTag!!].tag,
                Modifier.align(Alignment.Center))}
        LazyColumn(Modifier.fillMaxSize()){
            items(kartyak!!){ item ->
                listItem(item , navController)
            }
        }
    }
}

@Composable
fun listItem( item: Kartya, navController: NavHostController) {
    Card(modifier = Modifier
        .padding(8.dp).fillMaxWidth().size(100.dp)
        .clickable {
            if (item.tipus == Tipus.FELELETVALASZTOS) {
                navController.navigate("KartyaModositFeleletValasztos/${item.id}")
            } else {
                navController.navigate("KartyaModositValaszolos/${item.id}")
            }
        }, elevation = 8.dp){
        Box(){
            Text(item.kerdes!!, Modifier.align(Alignment.Center))
        }
    }
}
