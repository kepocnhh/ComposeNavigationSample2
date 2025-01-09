package test.android.cns2.module.foo

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import test.android.cns2.App
import java.util.UUID

@Composable
internal fun FoosScreen(
    onClick: (UUID) -> Unit,
) {
    val insets = WindowInsets.systemBars.asPaddingValues()
    val logger = App.logger("[Foos]")
    val viewModel = App.viewModel<FoosViewModel>()
    val foos = viewModel.foos.collectAsState().value
    LaunchedEffect(foos) {
        if (foos == null) {
            logger.debug("request foos...")
            viewModel.requestFoos()
        }
    }
    if (foos == null) return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = insets,
        ) {
            foos.fastForEachIndexed { index, foo ->
                item(key = foo.id) {
                    BasicText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clickable {
                                onClick(foo.id)
                            }
                            .padding(start = 8.dp)
                            .wrapContentHeight(),
                        text = "#$index: ${foo.id}",
                    )
                }
            }
        }
        if (foos.isEmpty()) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .align(Alignment.Center)
                    .wrapContentSize(),
                text = "no items",
                style = TextStyle(textAlign = TextAlign.Center),
            )
        }
    }
}
