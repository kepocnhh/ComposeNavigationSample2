package test.android.cns2.module.router

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import sp.kx.logics.Logics
import test.android.cns2.module.app.Injection

internal class RouterLogics(
    private val injection: Injection,
) : Logics(injection.contexts.main) {
    private val _authorized = MutableStateFlow<Boolean?>(null)
    val authorized = _authorized.asStateFlow()

    fun requestUser() = launch {
        _authorized.value = withContext(injection.contexts.default) {
            delay(1_000)
            injection.locals.user != null
        }
    }
}
