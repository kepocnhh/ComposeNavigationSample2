package test.android.cns2.module.foo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import test.android.cns2.entity.Foo
import test.android.cns2.module.app.Injection

internal class FoosViewModel(
    private val injection: Injection,
) : ViewModel() {
    private val _foos = MutableStateFlow<List<Foo>?>(null)
    val foos = _foos.asStateFlow()

    fun requestFoos() = viewModelScope.launch(injection.contexts.main) {
        _foos.value = withContext(injection.contexts.default) {
            injection.locals.foos
        }
    }
}
