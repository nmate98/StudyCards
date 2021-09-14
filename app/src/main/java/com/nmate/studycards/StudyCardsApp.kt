package com.nmate.studycards

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.nmate.studycards.feleletscreen.FeleletScreen
import com.nmate.studycards.kartyaletrehozasscreen.KartyaLetrehozasScreen
import com.nmate.studycards.kartyaletrehozasscreen.feleletvalasztos.FeleletValasztosScreen
import com.nmate.studycards.kartyaletrehozasscreen.valaszolos.ValaszolosScreen
import com.nmate.studycards.muveletvalasztasscreen.MuveletValasztasScreen
import com.nmate.studycards.pontszamscreen.PontszamScreen
import com.nmate.studycards.tagvalasztasscreen.TagValasztasScreen

@Composable
fun StudyCardsApp() {
    val navController = rememberNavController()
    Navigation(navController = navController)
}

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
        composable("Letrehozas") {
            KartyaLetrehozasScreen(navController)
        }
        composable("TagValaszt") {
            TagValasztasScreen(navController)
        }
        composable("MuveletValaszt") {
            MuveletValasztasScreen(navController)
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
    }
}

