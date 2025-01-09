package test.android.cns2.provider

import test.android.cns2.MockEnvironment
import test.android.cns2.entity.Foo

internal class FinalLocals : Locals {
    override var foos: List<Foo>
        get() {
            return MockEnvironment.foos
        }
        set(value) {
            MockEnvironment.foos = value
        }
}
