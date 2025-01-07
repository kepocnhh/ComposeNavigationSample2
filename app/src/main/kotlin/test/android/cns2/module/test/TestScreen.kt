package test.android.cns2.module.test

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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

@Composable
private fun TestScreen(
    color: Color,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickable(onClick = onClick)
                .wrapContentSize()
                .align(Alignment.Center),
            text = text,
            style = TextStyle(textAlign = TextAlign.Center),
        )
    }
}

@Composable
internal fun TestScreen() {
    val nhc = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxWidth(),
        navController = nhc,
        startDestination = "f1",
    ) {
        composable(
            route = "f1",
        ) {
            TestScreen(
                color = Color.Red,
                text = "f1",
                onClick = {
                    nhc.navigate("f2")
                },
            )
        }
        composable(
            route = "f2",
            enterTransition = { slideInHorizontally(initialOffsetX = {it}) },
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = { slideOutHorizontally(targetOffsetX = {it}) },
        ) {
            TestScreen(
                color = Color.Green,
                text = "f2",
                onClick = {
                    nhc.navigate("f3")
                },
            )
        }
        composable(
            route = "f3",
            enterTransition = { slideInHorizontally(initialOffsetX = {it}) },
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = { slideOutHorizontally(targetOffsetX = {it}) },
        ) {
            TestScreen(
                color = Color.Blue,
                text = "f3",
                onClick = {
                    nhc.popBackStack(route = "f1", inclusive = false)
                },
            )
        }
    }
}
