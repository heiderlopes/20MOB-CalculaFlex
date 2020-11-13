package br.com.calculaflex.presentation.utils.featuretoggle

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import br.com.calculaflex.domain.entity.DashboardItem
import br.com.calculaflex.domain.entity.enums.FeatureToggleState

class FeatureToggleHelper {

    fun configureFeature(
        dashboardItem: DashboardItem,
        featureToggleListener: FeatureToggleListener
    ) {
        setFeatureToggleListener(dashboardItem, featureToggleListener)
    }

    private fun setFeatureToggleListener(
        dashboardItem: DashboardItem,
        featureToggleListener: FeatureToggleListener
    ) {
        when (dashboardItem.status) {
            FeatureToggleState.INVISIBLE -> {
                featureToggleListener.onInvisible()
            }
            FeatureToggleState.ENABLED -> {
                featureToggleListener.onEnabled()
            }
            FeatureToggleState.DISABLED -> {
                featureToggleListener.onDisabled(this::showMessageUnavailable)
            }
        }
    }

    private fun showMessageUnavailable(ctx: Context) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Eitcha!")
        builder.setMessage("Funcionalidade temporariamente indisponível")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

        }
        builder.show()
    }
}
