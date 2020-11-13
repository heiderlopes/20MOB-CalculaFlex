package br.com.calculaflex.presentation.updateapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import br.com.calculaflex.R
class UpdateAppFragment : Fragment() {

    private lateinit var btUpdateApp: Button
    private lateinit var btUpdateLater: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
    }

    private fun setUpView(view: View) {
        btUpdateApp = view.findViewById(R.id.btUpdateApp)
        btUpdateLater = view.findViewById(R.id.btUpdateLater)

        btUpdateApp.setOnClickListener {
            openAppInStore()
        }

        btUpdateLater.setOnClickListener {
            activity?.finish()
        }
    }

    private fun openAppInStore() {
        var intent: Intent
        val packageName = activity?.packageName
        try {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            startActivity(intent)
        } catch (e: android.content.ActivityNotFoundException) {
            intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
            startActivity(intent)
        }
    }
}