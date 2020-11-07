package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User

interface UserRemoteDataSource {

    suspend fun getUserLogged() : RequestState<User>

}