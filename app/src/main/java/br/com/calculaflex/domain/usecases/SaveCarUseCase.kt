package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.repository.CarRepository

class SaveCarUseCase(
    private val getUserLoggedUseCase: GetUserLoggedUseCase,
    private val carRepository: CarRepository
) {
    suspend fun save(car: Car): RequestState<Car> {
        val userLogged = getUserLoggedUseCase.getUserLogged()

        return when(userLogged) {
            is RequestState.Success -> {
                car.userId = userLogged.data.id
                carRepository.save(car)
            }

            is RequestState.Loading -> {
                RequestState.Loading
            }

            is RequestState.Error -> {
                RequestState.Error(Exception("Usuário não encontrado para associar o carro"))
            }
        }
    }

}
