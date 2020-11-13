package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.data.remote.utils.firebase.RemoteConfigKeys
import br.com.calculaflex.domain.entity.DashboardMenu
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.extensions.fromRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception

@ExperimentalCoroutinesApi
class AppRemoteFirebaseDataSourceImpl: AppRemoteDataSource {

    override suspend fun getMinVersionApp(): RequestState<Int> {
        val minVersion = Gson().fromRemoteConfig(RemoteConfigKeys.MIN_VERSION_APP, Int::class.java) ?: 0
        return RequestState.Success(minVersion)
    }

    override suspend fun getDashboardMenu(): RequestState<DashboardMenu> {
        val dashboardMenu = Gson().fromRemoteConfig(RemoteConfigKeys.MENU_DASHBOARD, DashboardMenu::class.java)

        return if(dashboardMenu == null)
            RequestState.Error(Exception("Não foi possível encontrar o menu"))
        else
            RequestState.Success(dashboardMenu)
    }
}
