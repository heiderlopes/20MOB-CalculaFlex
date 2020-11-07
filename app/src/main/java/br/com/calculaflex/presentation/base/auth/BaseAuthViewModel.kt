package br.com.calculaflex.presentation.base.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class BaseAuthViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {

    val userLoggedState = MutableLiveData<RequestState<User>>()

    fun getUserLogged () {
        viewModelScope.launch {
            userLoggedState.value = getUserLoggedUseCase.getUserLogged()
        }
    }
}