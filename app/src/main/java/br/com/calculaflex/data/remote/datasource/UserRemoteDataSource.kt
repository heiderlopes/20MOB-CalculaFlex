package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin

interface UserRemoteDataSource {

    suspend fun getUserLogged() : RequestState<User>

    suspend fun doLogin(userLogin: UserLogin): RequestState<User>

}