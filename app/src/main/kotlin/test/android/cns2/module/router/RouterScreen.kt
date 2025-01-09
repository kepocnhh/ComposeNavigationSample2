package test.android.cns2.module.router

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import test.android.cns2.App
import test.android.cns2.module.foo.FooScreen
import test.android.cns2.module.foo.FoosScreen
import test.android.cns2.module.main.MainScreen
import java.util.UUID

@Composable
internal fun RouterScreen() {
    val logger = App.logger("[Router]")
    val nhc = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = nhc,
        startDestination = "main",
    ) {
        composable(
            route = "main",
        ) {
            DisposableEffect(Unit) {
                onDispose {
                    logger.debug("main: on dispose...")
                }
            }
            MainScreen(
                onClick = { route ->
                    logger.debug("main -> $route")
                    when (route) {
                        MainScreen.Route.Foo -> {
                            nhc.navigate("foos")
                        }
                    }
                }
            )
        }
        composable(
            route = "foos",
            enterTransition = { slideInHorizontally(initialOffsetX = {it}) },
            exitTransition = { slideOutHorizontally(targetOffsetX = {-it}) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = {-it}) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = {it}) },
        ) {
            DisposableEffect(Unit) {
                onDispose {
                    logger.debug("foos: on dispose...")
                }
            }
            FoosScreen(
                onClick = { id ->
                    logger.debug("to foo: $id")
                    nhc.navigate("foo/$id")
                }
            )
        }
        composable(
            route = "foo/{id}",
            enterTransition = { slideInHorizontally(initialOffsetX = {it}) },
            exitTransition = { slideOutHorizontally(targetOffsetX = {-it}) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = {-it}) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = {it}) },
        ) {
            val id = it.arguments?.getString("id")!!.let(UUID::fromString)
            DisposableEffect(Unit) {
                onDispose {
                    logger.debug("foo($id): on dispose...")
                }
            }
            FooScreen(
                id = id,
                onBack = {
                    nhc.popBackStack()
                },
            )
        }
    }
}
