package test.android.cns2.module.enter

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import sp.kx.logics.Logics
import test.android.cns2.entity.User
import test.android.cns2.module.app.Injection
import java.util.UUID

internal class EnterLogics(
    private val injection: Injection,
) : Logics(injection.contexts.default) {
    sealed interface Event {
        data object OnEnter : Event
    }

    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    fun enter(login: String, password: String) = launch {
        withContext(injection.contexts.default) {
            delay(1_000)
            val user = User(id = UUID(1, 0)) // todo
            injection.locals.user = user
        }
        _events.emit(Event.OnEnter)
    }
}
