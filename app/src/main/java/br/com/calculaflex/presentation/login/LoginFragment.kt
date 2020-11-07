package br.com.calculaflex.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import br.com.calculaflex.R
import br.com.calculaflex.presentation.base.BaseFragment
import br.com.calculaflex.presentation.base.auth.BaseAuthFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LoginFragment : BaseFragment() {

    override val layout = R.layout.fragment_login

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerBackPressedAction()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}

