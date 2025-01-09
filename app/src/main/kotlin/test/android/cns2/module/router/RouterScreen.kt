package test.android.cns2.module.router

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import test.android.cns2.App
import test.android.cns2.module.foo.FooScreen
import test.android.cns2.module.foo.FoosScreen
import test.android.cns2.module.main.MainScreen
import java.util.UUID

internal object RouterScreen {
    sealed interface Route {
        data object Main : Route
        data object Foos : Route
        data class Foo(val id: UUID) : Route

        companion object {
            val order: Set<Class<out Route>> = setOf(
                Route.Main::class.java,
                Route.Foos::class.java,
                Route.Foo::class.java,
            )
        }
    }

    data class Routes<T: Route>(
        val from: Class<out T>?,
        val to: Route,
    )
}

@Composable
internal fun RouterScreen() {
    val logger = App.logger("[Router]")
    val routesState = remember {
        mutableStateOf<RouterScreen.Routes<RouterScreen.Route>>(
            RouterScreen.Routes(from = null, to = RouterScreen.Route.Main),
        )
    }
    val transitionState = remember {
        SeekableTransitionState(routesState.value)
    }
    val transition = rememberTransition(transitionState, label = "entry")
    LaunchedEffect(routesState.value) {
        if (transitionState.currentState != routesState.value) {
            transitionState.animateTo(routesState.value)
        }
    }
    transition.AnimatedContent(
        transitionSpec = {
            ContentTransform(
                targetContentEnter = slideInHorizontally(
                    initialOffsetX = { fullWidth: Int ->
                        val fi = RouterScreen.Route.order.indexOf(routesState.value.from)
                        val ti = RouterScreen.Route.order.indexOf(routesState.value.to::class.java)
                        if (fi > ti) - fullWidth else fullWidth
                    },
                ),
                initialContentExit = slideOutHorizontally(
                    targetOffsetX = { fullWidth: Int ->
                        val fi = RouterScreen.Route.order.indexOf(routesState.value.from)
                        val ti = RouterScreen.Route.order.indexOf(routesState.value.to::class.java)
                        if (fi < ti) - fullWidth else fullWidth
                    },
                ),
                sizeTransform = null,
            )
        },
//        contentKey = {it::to::class.java},
    ) { routes ->
        when (val route = routes.to) {
            is RouterScreen.Route.Foo -> {
                BackHandler {
                    routesState.value = RouterScreen.Routes(
                        from = RouterScreen.Route.Foo::class.java,
                        to = RouterScreen.Route.Foos,
                    )
                }
                FooScreen(
                    id = route.id,
                    onBack = {
                        routesState.value = RouterScreen.Routes(
                            from = RouterScreen.Route.Foo::class.java,
                            to = RouterScreen.Route.Foos,
                        )
                    },
                )
            }
            RouterScreen.Route.Foos -> {
                BackHandler {
                    routesState.value = RouterScreen.Routes(
                        from = RouterScreen.Route.Foos::class.java,
                        to = RouterScreen.Route.Main,
                    )
                }
                FoosScreen(
                    onClick = { id ->
                        routesState.value = RouterScreen.Routes(
                            from = RouterScreen.Route.Foos::class.java,
                            to = RouterScreen.Route.Foo(id = id),
                        )
                    }
                )
            }
            RouterScreen.Route.Main -> {
                MainScreen(
                    onClick = { route ->
                        when (route) {
                            MainScreen.Route.Foo -> {
                                routesState.value = RouterScreen.Routes(
                                    from = RouterScreen.Route.Main::class.java,
                                    to = RouterScreen.Route.Foos,
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
internal fun RouterScreen2() {
    val logger = App.logger("[Router]")
    val routesState = remember {
        mutableStateOf<RouterScreen.Routes<RouterScreen.Route>>(
            RouterScreen.Routes(from = null, to = RouterScreen.Route.Main),
        )
    }
    val routes = routesState.value
    AnimatedVisibility(
        visible = routes.to == RouterScreen.Route.Main,
    ) {
        MainScreen(
            onClick = { route ->
                logger.debug("main -> $route")
                when (route) {
                    MainScreen.Route.Foo -> {
                        routesState.value = RouterScreen.Routes(
                            from = RouterScreen.Route.Main::class.java,
                            to = RouterScreen.Route.Foos,
                        )
                    }
                }
            }
        )
    }
    AnimatedVisibility(
        visible = routes.to == RouterScreen.Route.Foos,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth: Int ->
                when (routes.from) {
                    RouterScreen.Route.Foo::class.java -> - fullWidth
                    else -> fullWidth
                }
            },
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth: Int ->
                when (routes.to) {
                    is RouterScreen.Route.Foo -> - fullWidth
                    else -> fullWidth
                }
            },
        ),
    ) {
        BackHandler {
            routesState.value = RouterScreen.Routes(
                from = RouterScreen.Route.Foos::class.java,
                to = RouterScreen.Route.Main,
            )
        }
        FoosScreen(
            onClick = { id ->
                logger.debug("to foo: $id")
                routesState.value = RouterScreen.Routes(
                    from = RouterScreen.Route.Foos::class.java,
                    to = RouterScreen.Route.Foo(id = id),
                )
            }
        )
    }
    AnimatedVisibility(
        visible = routes.to is RouterScreen.Route.Foo,
    ) {
        val route = remember { routes.to as RouterScreen.Route.Foo }
        val id = route.id
        BackHandler {
            routesState.value = RouterScreen.Routes(
                from = RouterScreen.Route.Foo::class.java,
                to = RouterScreen.Route.Foos,
            )
        }
        FooScreen(
            id = id,
            onBack = {
                routesState.value = RouterScreen.Routes(
                    from = RouterScreen.Route.Foo::class.java,
                    to = RouterScreen.Route.Foos,
                )
            },
        )
    }
}

@Composable
internal fun RouterScreen1() {
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
            FooScreen(
                id = id,
                onBack = {
                    nhc.popBackStack()
                },
            )
        }
    }
}
