package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.entity.RequestState

interface CarRemoteDataSource {

    suspend fun save(car: Car): RequestState<Car>

}
