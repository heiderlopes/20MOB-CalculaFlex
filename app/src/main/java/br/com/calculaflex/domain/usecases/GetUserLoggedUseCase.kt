package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.repository.UserRepository

class GetUserLoggedUseCase (
    private val userRepository: UserRepository
) {

    suspend fun getUserLogged() = userRepository.getUserLogged()
}