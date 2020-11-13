package br.com.calculaflex.data.remote.datasource

import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.entity.RequestState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CarRemoteFirebaseDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : CarRemoteDataSource {

    override suspend fun save(car: Car): RequestState<Car> {
        return try {
            firebaseFirestore.collection("cars")
                .document(car.userId)
                .set(car)
                .await()
            RequestState.Success(car)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }
}
