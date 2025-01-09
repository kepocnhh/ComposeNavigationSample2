package test.android.cns2.module.foo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import test.android.cns2.entity.Foo
import test.android.cns2.module.app.Injection
import java.util.UUID

internal class FooViewModel(
    private val injection: Injection,
) : ViewModel() {
    private val _foo = MutableStateFlow<Foo?>(null)
    val foo = _foo.asStateFlow()

    fun requestFoo(id: UUID) = viewModelScope.launch(injection.contexts.main) {
        _foo.value = withContext(injection.contexts.default) {
            injection.locals.foos.firstOrNull { it.id == id } ?: TODO()
        }
    }
}
