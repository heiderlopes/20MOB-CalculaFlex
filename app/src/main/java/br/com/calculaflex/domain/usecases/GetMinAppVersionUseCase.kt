package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.repository.AppRepository

class GetMinAppVersionUseCase(
    private val appRespository: AppRepository
) {

    suspend fun getMinVersionApp(): RequestState<Int> =
        appRespository.getMinVersionApp()
}
