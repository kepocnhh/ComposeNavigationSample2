package test.android.cns2.module.router

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
import java.util.UUID

@Composable
internal fun RouterScreen() {
    val logger = App.logger("[Router]")
    val nhc = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = nhc,
        startDestination = "foos",
    ) {
        composable(
            route = "foos",
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
        composable("foo/{id}") {
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
