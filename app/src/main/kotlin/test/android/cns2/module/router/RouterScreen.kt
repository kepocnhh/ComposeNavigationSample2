package test.android.cns2.module.router

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import test.android.cns2.App
import test.android.cns2.module.bands.BandsScreen
import test.android.cns2.module.enter.EnterScreen

@Composable
internal fun RouterScreen(navController: NavHostController) {
    val logger = App.logger("[Router]")
    val logics = App.logics<RouterLogics>()
    val authorized = logics.authorized.collectAsState().value
    LaunchedEffect(Unit) {
        if (authorized == null) logics.requestUser()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        if (authorized == null) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = "loading...",
                style = TextStyle(textAlign = TextAlign.Center),
            )
        } else {
            logger.debug("authorized: $authorized")
            val graph = remember(navController) {
                val startDestination = if (authorized) "bands" else "enter"
                navController.createGraph(
                    startDestination = startDestination,
                ) {
                    composable("enter") { EnterScreen() }
                    composable("bands") { BandsScreen() }
                }
            }
            NavHost(navController = navController, graph = graph)
        }
    }
}
