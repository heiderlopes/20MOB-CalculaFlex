package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import kotlinx.coroutines.delay

class UserRemoteFakeDataSourceImpl : UserRemoteDataSource {

    override suspend fun getUserLogged(): RequestState<User> {
        delay(1000)
        //return RequestState.Success(User("Heider"))
        return RequestState.Error(Exception("Usuario deslogado"))
    }

}