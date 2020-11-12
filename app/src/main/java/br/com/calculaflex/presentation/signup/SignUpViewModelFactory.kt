package br.com.calculaflex.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.domain.usecases.CreateUserUseCase

class SignUpViewModelFactory(
    private val createUserUseCase: CreateUserUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CreateUserUseCase::class.java)
            .newInstance(createUserUseCase)
    }
}
