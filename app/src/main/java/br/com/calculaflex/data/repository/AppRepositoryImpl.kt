package br.com.calculaflex.data.repository

import br.com.calculaflex.data.remote.datasource.AppRemoteDataSource
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.repository.AppRepository

class AppRepositoryImpl(
    private val appRemoteDataSource: AppRemoteDataSource
) : AppRepository {

    override suspend fun getMinVersionApp(): RequestState<Int> {
        return appRemoteDataSource.getMinVersionApp()
    }
}
