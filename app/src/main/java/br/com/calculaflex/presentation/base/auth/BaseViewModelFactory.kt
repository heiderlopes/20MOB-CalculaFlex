package br.com.calculaflex.presentation.base.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase

class BaseViewModelFactory(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetUserLoggedUseCase::class.java).newInstance(getUserLoggedUseCase)
    }
}
