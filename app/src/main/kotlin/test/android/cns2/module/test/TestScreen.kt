package test.android.cns2.module.test

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
private fun TestScreen(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
    val ng = remember(nhc) {
        nhc.createGraph(startDestination = "f1") {
            composable("f1") {
                TestScreen(
                    text = "f1",
                    onClick = {
                        nhc.navigate("f2")
                    },
                )
            }
            composable("f2") {
                TestScreen(
                    text = "f2",
                    onClick = {
                        nhc.navigate("f3")
                    },
                )
            }
            composable("f3") {
                TestScreen(
                    text = "f3",
                    onClick = {
                        nhc.navigate("f1") {
                            popUpTo(nhc.graph.id)
                        }
                    },
                )
            }
        }
    }
    NavHost(
        modifier = Modifier.fillMaxWidth(),
        navController = nhc,
        graph = ng,
    )
}
