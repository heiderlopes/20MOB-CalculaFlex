package br.com.calculaflex.presentation.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.calculaflex.domain.entity.DashboardItem
import br.com.calculaflex.domain.entity.DashboardMenu
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.usecases.GetDashboardMenuUseCase
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.presentation.utils.featuretoggle.FeatureToggleHelper
import br.com.calculaflex.presentation.utils.featuretoggle.FeatureToggleListener
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getDashboardMenuUseCase: GetDashboardMenuUseCase,
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {

    var dashboardItemsState = MutableLiveData<RequestState<List<DashboardItem>>>()

    val headerState = MutableLiveData<RequestState<Triple<String, String, String>>>()

    var userLogged: User? = null

    fun getDashboardMenu() {
        viewModelScope.launch {

            headerState.value = RequestState.Loading

            val dashboardResponse = getDashboardMenuUseCase.getDashboardMenu()
            val userResponse = getUserLoggedUseCase.getUserLogged()

            setUpUser(userResponse)
            setUpHeader(dashboardResponse, userResponse)
            setUpDashboard(dashboardResponse)

        }
    }

    private fun setUpDashboard(dashResponse: RequestState<DashboardMenu>) {
        when (dashResponse) {
            is RequestState.Success -> {
                createMenu(dashResponse.data.items)
            }
            RequestState.Loading -> {
                dashboardItemsState.value = RequestState.Loading
            }
            is RequestState.Error -> {
                dashboardItemsState.value = RequestState.Error(dashResponse.throwable)
            }
        }
    }

    private fun setUpHeader(
        dashboardResponse: RequestState<DashboardMenu>,
        userResponse: RequestState<User>
    ) {
        if (dashboardResponse is RequestState.Success && userResponse is RequestState.Success) {
            headerState.value =
                RequestState.Success(Triple(dashboardResponse.data.title, dashboardResponse.data.subTitle, userResponse.data.name))
        } else {
            headerState.value = RequestState.Error(Exception())
        }
    }

    private fun setUpUser(userResponse: RequestState<User>) {
        userLogged = when (userResponse) {
            is RequestState.Success -> {
                userResponse.data
            }
            else -> null
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
