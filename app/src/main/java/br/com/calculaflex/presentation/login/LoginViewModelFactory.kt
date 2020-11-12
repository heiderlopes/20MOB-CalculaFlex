package br.com.calculaflex.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.domain.usecases.LoginUseCase


class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginUseCase::class.java).newInstance(loginUseCase)
    }

}
