package br.com.calculaflex.domain.repository

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User

interface UserRepository {

    suspend fun getUserLogged(): RequestState<User>

}
