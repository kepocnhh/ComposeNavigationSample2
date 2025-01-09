package test.android.cns2.module.foo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import test.android.cns2.App
import test.android.cns2.module.foo.FooViewModel
import java.util.UUID

@Composable
internal fun FooScreen(
    id: UUID,
    onBack: () -> Unit,
) {
    val logger = App.logger("[Foo]")
    val viewModel = App.viewModel<FooViewModel>()
    val foo = viewModel.foo.collectAsState().value
    LaunchedEffect(foo) {
        if (foo == null) {
            logger.debug("request foo:id: $id")
            viewModel.requestFoo(id = id)
        }
    }
    if (foo == null) return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
            ) {
                BasicText(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(onClick = onBack)
                        .padding(horizontal = 8.dp)
                        .wrapContentSize(),
                    text = "back",
                    style = TextStyle(textAlign = TextAlign.Center),
                )
            }
            Box(
                modifier = Modifier.weight(1f),
            ) {
                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .align(Alignment.Center)
                        .wrapContentSize(),
                    text = "foo:id: ${foo.id}",
                    style = TextStyle(textAlign = TextAlign.Center),
                )
            }
        }
    }
}
