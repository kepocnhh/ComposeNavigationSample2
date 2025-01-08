package test.android.cns2.module.test

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
internal fun TestScreen(
    modifier: Modifier,
    nhc: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = nhc,
        startDestination = "f1",
    ) {
        composable(
            route = "f1",
        ) { stackEntry: NavBackStackEntry ->
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
        ) { stackEntry: NavBackStackEntry ->
            TestScreen(
                color = Color.Green,
                text = "f2",
                onClick = {
                    val foo: Long = System.currentTimeMillis()
                    nhc.navigate("f3/$foo")
                },
            )
        }
        composable(
            route = "f3/{foo}",
            arguments = listOf(navArgument("foo") { type = NavType.LongType }),
            enterTransition = { slideInHorizontally(initialOffsetX = {it}) },
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = { slideOutHorizontally(targetOffsetX = {it}) },
        ) { stackEntry: NavBackStackEntry ->
            val foo = stackEntry.arguments?.getLong("foo")
            TestScreen(
                color = Color.Blue,
                text = "f3: $foo",
                onClick = {
                    nhc.popBackStack(route = "f1", inclusive = false)
                },
            )
        }
    }
}

@Composable
internal fun TestScreen() {
    val insets = WindowInsets.systemBars.asPaddingValues()
    val nhc = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(insets),
    ) {
        TestScreen(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            nhc = nhc,
        )
        val bse = nhc.currentBackStackEntryAsState().value
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(start = 8.dp)
                .wrapContentHeight(),
            text = "route: ${bse?.destination?.route}",
        )
    }
}
