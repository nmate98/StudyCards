package com.nmate.studycards

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.nmate.studycards.feleletscreen.FeleletScreen
import com.nmate.studycards.kartyaletrehozasscreen.KartyaLetrehozasScreen
import com.nmate.studycards.muveletvalasztasscreen.MuveletValasztasScreen
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
            FeleletScreen(it.arguments?.getString("id")!!)
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
    }
}