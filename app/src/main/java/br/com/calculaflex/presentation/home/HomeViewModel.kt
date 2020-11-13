package br.com.calculaflex.presentation.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.calculaflex.domain.entity.DashboardItem
import br.com.calculaflex.domain.entity.DashboardMenu
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetDashboardMenuUseCase
import br.com.calculaflex.presentation.utils.featuretoggle.FeatureToggleHelper
import br.com.calculaflex.presentation.utils.featuretoggle.FeatureToggleListener
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getDashboardMenuUseCase: GetDashboardMenuUseCase
) : ViewModel() {

    var dashboardItemsState = MutableLiveData<RequestState<List<DashboardItem>>>()

    fun getDashboardMenu() {
        viewModelScope.launch {
            val response = getDashboardMenuUseCase.getDashboardMenu()
            when (response) {
                is RequestState.Success -> {
                    createMenu(response.data.items)
                }
                RequestState.Loading -> {
                    dashboardItemsState.value = RequestState.Loading
                }
                is RequestState.Error -> {
                    dashboardItemsState.value = RequestState.Error(response.throwable)
                }
            }
        }
    }

    private fun createMenu(dashboardItem: List<DashboardItem>) {
        val dashBoardItems = arrayListOf<DashboardItem>()

        for (itemMenu in dashboardItem) {
            FeatureToggleHelper().configureFeature(
                itemMenu,
                object : FeatureToggleListener {
                    override fun onEnabled() {
                        dashBoardItems.add(itemMenu)
                    }
                    override fun onInvisible() {

                    }

                    override fun onDisabled(clickListener: (Context) -> Unit) {
                        itemMenu.onDisabledListener = clickListener
                        dashBoardItems.add(itemMenu)
                    }
                })
        }
        dashboardItemsState.value = RequestState.Success(dashBoardItems)
    }
}
