package test.android.cns2

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import sp.kx.logics.Logics
import sp.kx.logics.LogicsFactory
import sp.kx.logics.LogicsProvider
import sp.kx.logics.contains
import sp.kx.logics.get
import sp.kx.logics.remove
import test.android.cns2.module.app.Injection
import test.android.cns2.provider.Contexts
import test.android.cns2.provider.FinalLoggers

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        _injection = Injection(
            loggers = FinalLoggers,
            contexts = Contexts(
                main = Dispatchers.Main,
                default = Dispatchers.Default,
            ),
        )
    }

    companion object {
        private var _injection: Injection? = null

        private val _logicsProvider = LogicsProvider(
            factory = object : LogicsFactory {
                override fun <T : Logics> create(type: Class<T>): T {
                    val injection = checkNotNull(_injection) { "No injection!" }
                    return type
                        .getConstructor(Injection::class.java)
                        .newInstance(injection)
                }
            },
        )

        @Composable
        inline fun <reified T : Logics> logics(label: String = T::class.java.name): T {
            val (contains, logics) = synchronized(Unit) {
                remember { _logicsProvider.contains<T>(label = label) } to _logicsProvider.get<T>(label = label)
            }
            DisposableEffect(Unit) {
                onDispose {
                    synchronized(Unit) {
                        if (!contains) _logicsProvider.remove<T>(label = label)
                    }
                }
            }
            return logics
        }
    }
}
