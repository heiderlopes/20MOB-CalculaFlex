package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.DashboardMenu
import br.com.calculaflex.domain.entity.RequestState

interface AppRemoteDataSource {

    suspend fun getMinVersionApp(): RequestState<Int>

    suspend fun getDashboardMenu(): RequestState<DashboardMenu>

}
