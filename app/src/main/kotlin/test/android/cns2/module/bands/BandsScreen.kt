package test.android.cns2.module.bands

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import test.android.cns2.App

@Composable
internal fun BandsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val viewModel = App.viewModel<BandsViewModel>()
        val bands = viewModel.bands.collectAsState().value
        LaunchedEffect(Unit) {
            if (bands == null) viewModel.requestBands()
        }
        if (bands == null) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = "loading...",
                style = TextStyle(textAlign = TextAlign.Center),
            )
        } else if (bands.isEmpty()) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = "no bands",
                style = TextStyle(textAlign = TextAlign.Center),
            )
        } else {
            // todo
        }
    }
}
