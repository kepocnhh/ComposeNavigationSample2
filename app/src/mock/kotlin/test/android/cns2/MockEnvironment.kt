package test.android.cns2

import test.android.cns2.entity.Foo
import java.util.UUID

internal object MockEnvironment {
    var foos: List<Foo> = initFoos()

    private fun initFoos(): List<Foo> {
        return (1..24).map { number ->
            Foo(id = UUID(number.toLong(), 0))
        }
    }
}
