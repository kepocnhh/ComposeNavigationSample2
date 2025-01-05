package test.android.cns2.provider

import test.android.cns2.MockEnvironment
import test.android.cns2.entity.Band
import test.android.cns2.entity.User

internal class FinalLocals : Locals {
    override var user: User?
        get() {
            return MockEnvironment.user
        }
        set(value) {
            MockEnvironment.user = value
        }

    override var bands: List<Band>
        get() {
            return MockEnvironment.bands
        }
        set(value) {
            MockEnvironment.bands = value
        }
}
