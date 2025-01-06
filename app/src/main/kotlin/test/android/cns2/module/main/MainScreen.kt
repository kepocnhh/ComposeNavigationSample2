package test.android.cns2.module.main

import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import test.android.cns2.module.bands.BandsScreen

@Composable
private fun MainScreen(
    onBands: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable {
                        onBands()
                    }
                    .wrapContentSize(),
                text = "bands",
                style = TextStyle(textAlign = TextAlign.Center),
            )
        }
    }
}

@Composable
internal fun MainNavHost() {
    val nhc = rememberNavController()
    val ng = remember(nhc) {
        nhc.createGraph(startDestination = "main") {
            composable("main") {
                MainScreen(
                    onBands = {
                        nhc.navigate("bands")
                    },
                )
            }
            composable(
                route = "bands",
                enterTransition = { slideInHorizontally(initialOffsetX = {it}) },
                exitTransition = { slideOutHorizontally(targetOffsetX = {it}) },
            ) {
                BandsScreen()
            }
        }
    }
    NavHost(navController = nhc, graph = ng)
}
