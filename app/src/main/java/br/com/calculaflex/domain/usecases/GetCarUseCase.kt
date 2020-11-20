package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.repository.CarRepository

class GetCarUseCase(
    private val carRepository: CarRepository
) {
    suspend fun findBy(id: String): RequestState<Car> {
        return carRepository.findBy(id)
    }
}
