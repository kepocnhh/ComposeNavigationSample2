package test.android.cns2.module.bands

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import sp.kx.logics.Logics
import test.android.cns2.entity.Band
import test.android.cns2.module.app.Injection

internal class BandsLogics(
    private val injection: Injection,
) : Logics(injection.contexts.main) {
    private val _bands = MutableStateFlow<List<Band>?>(null)
    val bands = _bands.asStateFlow()

    fun requestBands() = launch {
        _bands.value = withContext(injection.contexts.default) {
            injection.locals.bands
        }
    }
}
