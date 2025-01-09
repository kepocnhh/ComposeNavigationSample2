package test.android.cns2

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import test.android.cns2.module.app.Injection
import test.android.cns2.provider.Contexts
import test.android.cns2.provider.FinalLocals
import test.android.cns2.provider.FinalLoggers
import test.android.cns2.provider.Locals
import test.android.cns2.provider.Logger

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val loggers: Logger.Factory = FinalLoggers
        _loggers = loggers
        val locals: Locals = FinalLocals()
        _injection = Injection(
            loggers = loggers,
            contexts = Contexts(
                main = Dispatchers.Main,
                default = Dispatchers.Default,
            ),
            locals = locals,
        )
    }

    companion object {
        private var _loggers: Logger.Factory? = null
        private var _injection: Injection? = null

        private val _factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val injection = checkNotNull(_injection) { "No injection!" }
                return modelClass
                    .getConstructor(Injection::class.java)
                    .newInstance(injection)
            }
        }

        @Composable
        inline fun <reified T : ViewModel> viewModel(): T {
            return viewModel(
                factory = _factory,
            )
        }

        @Composable
        fun logger(tag: String): Logger {
            return remember(tag) {
                val loggers = checkNotNull(_loggers) { "No loggers!" }
                loggers.create(tag = tag)
            }
        }
    }
}
