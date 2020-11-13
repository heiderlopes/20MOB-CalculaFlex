package br.com.calculaflex.extensions

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

fun Fragment.startDeeplink(deeplink: String) {
    if (deeplink.contains("://")) {
        val uri = Uri.parse(deeplink)
        val responseIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(responseIntent)
    } else {
        val internalIntent = Intent(deeplink)
        startActivity(internalIntent)
    }
}
