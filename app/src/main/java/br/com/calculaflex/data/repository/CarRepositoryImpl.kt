package br.com.calculaflex.data.repository

import br.com.calculaflex.data.remote.datasource.CarRemoteDataSource
import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.repository.CarRepository

class CarRepositoryImpl(
    private val carRemoteDataSource: CarRemoteDataSource
) : CarRepository {

    override suspend fun save(car: Car): RequestState<Car> {
        return carRemoteDataSource.save(car)
    }

}
