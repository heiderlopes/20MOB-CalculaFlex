package br.com.calculaflex.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.domain.usecases.GetDashboardMenuUseCase

class HomeViewModelFactory(
    private val getDashboardMenuUseCase: GetDashboardMenuUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetDashboardMenuUseCase::class.java)
            .newInstance(getDashboardMenuUseCase)
    }
}
