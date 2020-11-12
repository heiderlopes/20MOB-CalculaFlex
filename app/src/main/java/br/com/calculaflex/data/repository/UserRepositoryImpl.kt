package br.com.calculaflex.data.repository

import br.com.calculaflex.data.remote.datasource.UserRemoteDataSource
import br.com.calculaflex.domain.entity.NewUser
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import br.com.calculaflex.domain.repository.UserRepository

class UserRepositoryImpl(
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUserLogged(): RequestState<User> {
        return userRemoteDataSource.getUserLogged()
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return userRemoteDataSource.doLogin(userLogin)
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return userRemoteDataSource.create(newUser)
    }

}