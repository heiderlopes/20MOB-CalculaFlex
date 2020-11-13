package br.com.calculaflex.domain.repository

import br.com.calculaflex.domain.entity.RequestState

interface AppRepository {

    suspend fun getMinVersionApp(): RequestState<Int>

}
