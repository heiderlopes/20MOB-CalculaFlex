package br.com.calculaflex.domain.repository

import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.entity.RequestState

interface CarRepository {

    suspend fun save(car: Car): RequestState<Car>

}
