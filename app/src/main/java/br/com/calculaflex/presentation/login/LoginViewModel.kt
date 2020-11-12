package br.com.calculaflex.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import br.com.calculaflex.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLogin(email, password))
        }
    }
}
