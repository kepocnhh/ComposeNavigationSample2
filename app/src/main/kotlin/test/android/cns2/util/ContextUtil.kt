package test.android.cns2.util

import android.content.Context
import android.widget.Toast

internal fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}
