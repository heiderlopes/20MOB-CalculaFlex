package br.com.calculaflex.presentation.betterfuel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.domain.usecases.CalculateBetterFuelUseCase
import br.com.calculaflex.domain.usecases.GetCarUseCase
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.domain.usecases.SaveCarUseCase

class BetterFuelViewModelFactory(
    private val saveCarUseCase: SaveCarUseCase,
    private val calculateBetterFuelUseCase: CalculateBetterFuelUseCase,
    private val getCarUseCase: GetCarUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SaveCarUseCase::class.java,
            CalculateBetterFuelUseCase::class.java,
            GetCarUseCase::class.java
        )
            .newInstance(saveCarUseCase, calculateBetterFuelUseCase, getCarUseCase)
    }
}

