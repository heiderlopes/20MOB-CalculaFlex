package br.com.calculaflex.presentation.utils.featuretoggle

import android.content.Context

interface FeatureToggleListener {
    fun onEnabled()
    fun onInvisible()
    fun onDisabled(clickListener: (Context) -> Unit)
}
