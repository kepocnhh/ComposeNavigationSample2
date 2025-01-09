package test.android.cns2.module.enter

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import test.android.cns2.App

@Composable
private fun EnterLoginScreen(
    onLogin: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickable {
                    onLogin("foobar") // todo
                }
                .wrapContentSize()
                .align(Alignment.Center),
            text = "login",
            style = TextStyle(textAlign = TextAlign.Center),
        )
    }
}

@Composable
private fun EnterPasswordScreen(
    onPassword: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickable {
                    onPassword("3454") // todo
                }
                .wrapContentSize()
                .align(Alignment.Center),
            text = "password",
            style = TextStyle(textAlign = TextAlign.Center),
        )
    }
}

@Composable
internal fun EnterScreen(
    onEnter: () -> Unit,
) {
    val nhc = rememberNavController()
    val logger = App.logger("[Enter]")
    val viewModel = TODO()
//    LaunchedEffect(Unit) {
//        viewModel.events.collect { event ->
//            logger.debug("event: $event")
//            when (event) {
//                EnterViewModel.Event.OnEnter -> onEnter()
//            }
//        }
//    }
    val loginState = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(loginState.value) {
        val login = loginState.value
        if (login != null) {
            logger.debug("to -> password")
            nhc.navigate("password")
        }
    }
    val passwordState = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(passwordState.value) {
        val login = loginState.value
        val password = passwordState.value
        if (login != null && password != null) {
            logger.debug("enter...")
//            viewModel.enter(
//                login = login,
//                password = password,
//            )
        }
    }
    val ng = remember(nhc) {
        nhc.createGraph(startDestination = "login") {
            composable(
                route = "login",
            ) {
                EnterLoginScreen(
                    onLogin = {
                        loginState.value = it
                    },
                )
            }
            composable(
                route = "password",
            ) {
                EnterPasswordScreen(
                    onPassword = {
                        passwordState.value = it
                    },
                )
            }
        }
    }
    NavHost(navController = nhc, graph = ng)
}
