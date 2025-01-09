package test.android.cns2.module.router

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import test.android.cns2.App
import test.android.cns2.module.enter.EnterScreen
import test.android.cns2.module.main.MainNavHost
import test.android.cns2.module.splash.SplashScreen

@Composable
internal fun RouterScreen() {
    val logger = App.logger("[Router]")
    val viewModel = TODO()
//    val authorized = viewModel.authorized.collectAsState().value
//    LaunchedEffect(authorized) {
//        if (authorized == null) viewModel.requestUser()
//    }
    val nhc = rememberNavController()
    val ng = remember(nhc) {
        nhc.createGraph(startDestination = "splash") {
            composable("splash") { SplashScreen() }
            composable("enter") {
                EnterScreen(
                    onEnter = {
//                        viewModel.requestUser()
                    },
                )
            }
            composable("main") { MainNavHost() }
        }
    }
    NavHost(
        modifier = Modifier.fillMaxWidth(),
        navController = nhc,
        graph = ng,
    )
//    logger.debug("authorized: $authorized")
//    when (authorized) {
//        true -> nhc.navigate("main")
//        false -> nhc.navigate("enter")
//        else -> Unit
//    }
}
