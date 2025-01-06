package test.android.cns2

import test.android.cns2.entity.Band
import test.android.cns2.entity.User
import java.util.UUID

internal object MockEnvironment {
    var user: User? = initUser()
    var bands: List<Band> = initBands()

    private fun initUser(): User? {
        return null
//        return User(id = UUID(1, 0))
    }

    private fun initBands(): List<Band> {
        return emptyList()
//        return (1..10).map { number ->
//            Band(
//                id = UUID(number.toLong(), 0),
//                title = "band #$number",
//            )
//        }
    }
}
