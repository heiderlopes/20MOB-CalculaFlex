package br.com.calculaflex.domain.utils

import br.com.calculaflex.domain.entity.enums.FuelType
import br.com.calculaflex.domain.entity.holder.BetterFuelHolder

class FuelCalculator {

    fun betterFuel(
        betterFuelHolder: BetterFuelHolder
    ): FuelType {

        val performanceOfMyCar = calculatePerfomanceOfCar(
            betterFuelHolder.ethanolAverage,
            betterFuelHolder.gasAverage
        )

        val priceOfFuelIndice = calculatePriceOfFuelIndice(
            betterFuelHolder.ethanolPrice,
            betterFuelHolder.gasPrice
        )

        return if (priceOfFuelIndice <= performanceOfMyCar) {
            FuelType.ETHANOL
        } else
            FuelType.GASOLINE
    }

    fun calculatePerfomanceOfCar(ethanolAverage: Double, gasAverage: Double): Double {
        return ethanolAverage / gasAverage
    }

    fun calculatePriceOfFuelIndice(ethanolPrice: Double, gasPrice: Double): Double {
        return ethanolPrice / gasPrice
    }

}