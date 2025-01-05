package test.android.cns2.module.app

import test.android.cns2.provider.Contexts
import test.android.cns2.provider.Locals
import test.android.cns2.provider.Logger

internal class Injection(
    val loggers: Logger.Factory,
    val contexts: Contexts,
    val locals: Locals,
)
