package test.android.cns2.module.router

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import sp.kx.logics.Logics
import test.android.cns2.entity.User
import test.android.cns2.module.app.Injection
import java.util.UUID

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

    fun enter() = launch {
         withContext(injection.contexts.default) {
            delay(1_000)
            val user = User(id = UUID(1, 0)) // todo
            injection.locals.user = user
        }
        _authorized.value = true
    }

    fun exit() = launch {
        withContext(injection.contexts.default) {
            delay(1_000)
            injection.locals.user = null
        }
        _authorized.value = false
    }
}
