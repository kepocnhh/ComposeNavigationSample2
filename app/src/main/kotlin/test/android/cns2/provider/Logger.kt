package test.android.cns2.provider

internal interface Logger {
    interface Factory {
        fun create(tag: String): Logger
    }

    fun debug(message: String)
}
