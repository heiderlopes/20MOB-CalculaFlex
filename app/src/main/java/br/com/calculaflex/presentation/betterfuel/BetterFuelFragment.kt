package br.com.calculaflex.presentation.betterfuel

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.R
import br.com.calculaflex.data.remote.datasource.CarRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.repository.CarRepositoryImpl
import br.com.calculaflex.data.repository.UserRepositoryImpl
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.enums.FuelType
import br.com.calculaflex.domain.usecases.CalculateBetterFuelUseCase
import br.com.calculaflex.domain.usecases.GetCarUseCase
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.domain.usecases.SaveCarUseCase
import br.com.calculaflex.domain.utils.FuelCalculator
import br.com.calculaflex.extensions.getDouble
import br.com.calculaflex.extensions.getString
import br.com.calculaflex.presentation.base.auth.BaseAuthFragment
import br.com.calculaflex.presentation.watchers.DecimalTextWatcher
import br.com.calculaflexlib.components.customdialog.CustomDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class BetterFuelFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_better_fuel

    private lateinit var etCar: EditText
    private lateinit var etKmGasoline: EditText
    private lateinit var etKmEthanol: EditText
    private lateinit var etPriceGasoline: EditText
    private lateinit var etPriceEthanol: EditText
    private lateinit var btCalculate: Button
    private lateinit var btClear: TextView

    private val betterFuelViewModel: BetterFuelViewModel by lazy {
        ViewModelProvider(
            this,
            BetterFuelViewModelFactory(
                SaveCarUseCase(
                    GetUserLoggedUseCase(
                        UserRepositoryImpl(
                            UserRemoteFirebaseDataSourceImpl(
                                Firebase.auth,
                                Firebase.firestore
                            )
                        )
                    ),
                    CarRepositoryImpl(
                        CarRemoteFirebaseDataSourceImpl(
                            FirebaseFirestore.getInstance()
                        )
                    )
                ),
                CalculateBetterFuelUseCase(
                    FuelCalculator()
                ),
                GetCarUseCase(
                    CarRepositoryImpl(
                        CarRemoteFirebaseDataSourceImpl(
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(BetterFuelViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        setUpListener()

        registerObserver()

        betterFuelViewModel.getCar(arguments?.getString("id") ?: "")
    }

    private fun setUpView(view: View) {
        etCar = view.findViewById(R.id.etCar)
        etKmGasoline = view.findViewById(R.id.etKmGasoline)
        etKmEthanol = view.findViewById(R.id.etKmEthanol)
        etPriceGasoline = view.findViewById(R.id.etPriceGasoline)
        etPriceEthanol = view.findViewById(R.id.etPriceEthanol)
        btCalculate = view.findViewById(R.id.btCalculate)
        btClear = view.findViewById(R.id.btClear)
    }

    private fun setUpListener() {
        etPriceGasoline.addTextChangedListener(DecimalTextWatcher(etPriceGasoline))
        etPriceEthanol.addTextChangedListener(DecimalTextWatcher(etPriceEthanol))

        etKmGasoline.addTextChangedListener(DecimalTextWatcher(etKmGasoline, 1))
        etKmEthanol.addTextChangedListener(DecimalTextWatcher(etKmEthanol, 1))

        btCalculate.setOnClickListener {
            betterFuelViewModel.calculateBetterFuel(
                etCar.getString(),
                etKmGasoline.getDouble(),
                etKmEthanol.getDouble(),
                etPriceGasoline.getDouble(),
                etPriceEthanol.getDouble()
            )
        }

        btClear.setOnClickListener { clearFields() }
    }

    private fun registerObserver() {
        betterFuelViewModel.calculateState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()

                    val betterFuelMessage = when (it.data) {
                        FuelType.GASOLINE -> Pair("Gasolina", "Melhor abastecer com gasolina")
                        FuelType.ETHANOL -> Pair("Álcool", "Melhor abastecer com álcool")
                    }

                    val (title, subtitle) = betterFuelMessage

                    val customDialog = CustomDialog()

                    customDialog.showDialog(
                        requireActivity(),
                        R.drawable.ic_logo_splash,
                        title,
                        subtitle,
                        "OK",
                        View.OnClickListener { customDialog.dismissDialog() },
                        false
                    )

                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> {
                    showLoading("Calculando o melhor combustível para você")
                }
            }
        })


        betterFuelViewModel.carSelectedState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    val car = it.data
                    etCar.setText(car.vehicle)
                    etKmGasoline.setText(car.kmGasolinePerLiter.toString())
                    etKmEthanol.setText(car.kmEthanolPerLiter.toString())
                    etPriceGasoline.setText(car.priceGasolinePerLiter.toString())
                    etPriceEthanol.setText(car.priceEthanolPerLiter.toString())
                    hideLoading()

                }
                is RequestState.Error -> {
                    hideLoading()
                }
                is RequestState.Loading -> {
                    showLoading("Aguarde um momento")
                }
            }
        })
    }

    private fun clearFields() {
        etCar.setText("")
        etKmGasoline.setText("")
        etKmEthanol.setText("")
        etPriceGasoline.setText("")
        etPriceEthanol.setText("")
    }
}
