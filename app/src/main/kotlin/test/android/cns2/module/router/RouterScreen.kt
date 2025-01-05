package test.android.cns2.module.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import test.android.cns2.App
import test.android.cns2.module.bands.BandsScreen
import test.android.cns2.module.enter.EnterScreen
import test.android.cns2.module.splash.SplashScreen

@Composable
internal fun RouterScreen(navController: NavHostController) {
    val logger = App.logger("[Router]")
    val logics = App.logics<RouterLogics>()
    val authorized = logics.authorized.collectAsState().value
    LaunchedEffect(Unit) {
        if (authorized == null) logics.requestUser()
    }
    val graph = remember(navController) {
        navController.createGraph(startDestination = "splash") {
            composable("splash") { SplashScreen() }
            composable("enter") {
                EnterScreen(
                    onEnter = {
                        logics.enter()
                    },
                )
            }
            composable("bands") { BandsScreen() }
        }
    }
    NavHost(navController = navController, graph = graph)
    logger.debug("authorized: $authorized")
    when (authorized) {
        true -> navController.navigate("bands")
        false -> navController.navigate("enter")
        else -> Unit
    }
}
