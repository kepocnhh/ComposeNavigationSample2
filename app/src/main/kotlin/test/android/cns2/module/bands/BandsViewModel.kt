package test.android.cns2.module.bands

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import test.android.cns2.entity.Band
import test.android.cns2.module.app.Injection

internal class BandsViewModel(
    private val injection: Injection,
) : ViewModel() {
    private val _bands = MutableStateFlow<List<Band>?>(null)
    val bands = _bands.asStateFlow()

    fun requestBands() = viewModelScope.launch(injection.contexts.main) {
        _bands.value = withContext(injection.contexts.default) {
            injection.locals.bands
        }
    }
}
