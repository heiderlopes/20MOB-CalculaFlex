package br.com.calculaflex.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.calculaflex.R
import br.com.calculaflex.data.remote.datasource.AppRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.repository.AppRepositoryImpl
import br.com.calculaflex.data.repository.UserRepositoryImpl
import br.com.calculaflex.domain.entity.DashboardItem
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetDashboardMenuUseCase
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.extensions.startDeeplink
import br.com.calculaflex.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_home

    private lateinit var rvHomeDashboard: RecyclerView
    private lateinit var tvHomeHelloUser: TextView
    private lateinit var tvSubTitleSignUp: TextView


    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(
                GetDashboardMenuUseCase(
                    AppRepositoryImpl(
                        AppRemoteFirebaseDataSourceImpl()
                    )
                ),
                GetUserLoggedUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)

        registerObserver()
        homeViewModel.getDashboardMenu()
    }

    private fun setUpView(view: View) {
        rvHomeDashboard = view.findViewById(R.id.rvHomeDashboard)
        tvHomeHelloUser = view.findViewById(R.id.tvHomeHelloUser)
        tvSubTitleSignUp = view.findViewById(R.id.tvSubTitleSignUp)
    }

    private fun registerObserver() {

        homeViewModel.headerState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Loading -> {
                    tvHomeHelloUser.isVisible = true
                    tvSubTitleSignUp.isVisible = true

                    tvHomeHelloUser.text = "Carregando o nome usuario"
                    tvSubTitleSignUp.text = ""
                }

                is RequestState.Success -> {

                    tvHomeHelloUser.isVisible = true
                    tvSubTitleSignUp.isVisible = true

                    val (title, subTitle, userName) = it.data
                    tvHomeHelloUser.text = String.format(title, userName)
                    tvSubTitleSignUp.text = subTitle
                    hideLoading()
                }

                is RequestState.Error -> {
                    tvHomeHelloUser.isVisible = false
                    tvSubTitleSignUp.isVisible = false
                }
            }

        })

        homeViewModel.dashboardItemsState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    setUpMenu(it.data)
                    hideLoading()
                }

                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        })
    }

    private fun setUpMenu(items: List<DashboardItem>) {
        rvHomeDashboard.adapter = HomeAdapter(items, this::clickItem)
    }

    private fun clickItem(item: DashboardItem) {
        item.onDisabledListener.let {
            it?.invoke(requireContext())
        }

        if (item.onDisabledListener == null) {
            when (item.feature) {
                "SIGN_OUT" -> {
                    //chamar o metodo de logout
                }
                "ETHANOL_OR_GASOLINE" -> {
                    startDeeplink("${item.action.deeplink}?id=${homeViewModel.userLogged?.id}")
                }
                else -> {
                    startDeeplink(item.action.deeplink)
                }
            }
        }
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
