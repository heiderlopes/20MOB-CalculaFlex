package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.DashboardMenu
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.repository.AppRepository

class GetDashboardMenuUseCase(
    private val appRespository: AppRepository
) {

    suspend fun getDashboardMenu(): RequestState<DashboardMenu> =
        appRespository.getDashboardMenu()
}
