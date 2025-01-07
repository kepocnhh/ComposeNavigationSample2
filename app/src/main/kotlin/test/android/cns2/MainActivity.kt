package test.android.cns2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import test.android.cns2.module.router.RouterScreen
import test.android.cns2.module.test.TestScreen

internal class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ComposeView(this)
        setContentView(view)
        view.setContent {
//            RouterScreen()
            TestScreen()
        }
    }
}
