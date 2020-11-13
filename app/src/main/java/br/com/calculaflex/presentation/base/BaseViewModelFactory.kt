package br.com.calculaflex.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.domain.usecases.GetMinAppVersionUseCase

class BaseViewModelFactory(
    private val getMinAppVersionUseCase: GetMinAppVersionUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetMinAppVersionUseCase::class.java)
            .newInstance(getMinAppVersionUseCase)
    }
}
