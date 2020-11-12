package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.NewUser
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.repository.UserRepository

class CreateUserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun create(newUser: NewUser): RequestState<User> =
        userRepository.create(newUser)
}
