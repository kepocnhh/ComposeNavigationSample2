package test.android.cns2.module.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import test.android.cns2.module.bands.BandsScreen

@Composable
internal fun RouterScreen(navController: NavHostController) {
    val graph = remember(navController) {
        navController.createGraph(startDestination = "bands") {
            composable("bands") { BandsScreen() }
        }
    }
    NavHost(navController = navController, graph = graph)
}
