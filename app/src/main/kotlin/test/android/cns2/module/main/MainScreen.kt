package test.android.cns2.module.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import test.android.cns2.App

internal object MainScreen {
    enum class Route {
        Foo,
    }
}

@Composable
internal fun MainScreen(
    onClick: (MainScreen.Route) -> Unit,
) {
    val insets = WindowInsets.systemBars.asPaddingValues()
    val logger = App.logger("[Main]")
    DisposableEffect(Unit) {
        onDispose {
            logger.debug("on dispose...")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentPadding = insets,
        ) {
            MainScreen.Route.entries.forEachIndexed { _, route ->
                item(key = route.ordinal) {
                    BasicText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clickable {
                                logger.debug("main -> $route")
                                onClick(route)
                            }
                            .padding(start = 8.dp)
                            .wrapContentSize(),
                        text = route.name,
                    )
                }
            }
        }
    }
}
