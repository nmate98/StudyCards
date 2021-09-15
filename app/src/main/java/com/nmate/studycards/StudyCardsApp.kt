package com.nmate.studycards

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.nmate.studycards.feleletscreen.FeleletScreen
import com.nmate.studycards.kartyaletrehozasscreen.feleletvalasztos.FeleletValasztosScreen
import com.nmate.studycards.kartyaletrehozasscreen.valaszolos.ValaszolosScreen
import com.nmate.studycards.kartyamodositscreen.feleletvalasztos.KartyaModositFeleletValasztosScreen
import com.nmate.studycards.kartyamodositscreen.valaszolos.KartyaModositValaszolosScreen
import com.nmate.studycards.kartyamodositvalaszto.KartyaModositValasztoScreen
import com.nmate.studycards.pontszamscreen.PontszamScreen
import com.nmate.studycards.tagvalasztasscreen.TagValasztasScreen

@ExperimentalComposeUiApi
@Composable
fun StudyCardsApp() {
    val navController = rememberNavController()
    Navigation(navController = navController)
}

@ExperimentalComposeUiApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "MainMenu") {
        composable("MainMenu") {
            MainMenu(navController)
        }
        composable("Felelet/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })) {
            FeleletScreen(it.arguments?.getString("id")!!, navController)
        }
        composable("TagValaszt") {
            TagValasztasScreen(navController)
        }
        composable("FeleletValasztos") {
            FeleletValasztosScreen(navController)
        }
        composable("Valaszolos") {
            ValaszolosScreen(navController)
        }
        composable("Pontszam/{osszes}/{helyes}",
            arguments = listOf(navArgument("osszes") { type = NavType.IntType },
                navArgument("helyes") { type = NavType.IntType })) {
            PontszamScreen(navController,
                it.arguments?.getInt("osszes")!!,
                it.arguments?.getInt("helyes")!!)
        }
        composable("KartyaModositValaszt") {
            KartyaModositValasztoScreen(navController)
        }
        composable("KartyaModositValaszolos/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })) {
            KartyaModositValaszolosScreen(navController, it.arguments?.getLong("id")!!)
        }
        composable("KartyaModositFeleletValasztos/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })) {
            KartyaModositFeleletValasztosScreen(navController, it.arguments?.getLong("id")!!)
        }
    }
}

