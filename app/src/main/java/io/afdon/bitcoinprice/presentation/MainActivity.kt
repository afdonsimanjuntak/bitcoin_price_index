package io.afdon.bitcoinprice.presentation

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import io.afdon.bitcoinprice.MainApp
import io.afdon.bitcoinprice.R
import io.afdon.bitcoinprice.databinding.ActivityMainBinding
import io.afdon.bitcoinprice.presentation.main.MainFragment
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setFragmentFactory()

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addMainFragment()

        requestLocaPermissions()

        binding?.bRequestPermission?.setOnClickListener { requestLocaPermissions() }
    }

    private fun setFragmentFactory() {
        supportFragmentManager.fragmentFactory = (application as MainApp)
            .appComponent.getActivitySubcomponentFactory().create().getFragmentFactory()
    }

    private fun addMainFragment() {
        supportFragmentManager.commit {
            add(R.id.frameLayout, MainFragment::class.java, null)
            addToBackStack(null)
        }
    }

    private fun requestLocaPermissions() {
        var permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        requestMultiplePermissions.launch(permissions)
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            var isGranted = true
            for ((_, value) in permissions) {
                if (!value) {
                    isGranted = false
                    break
                }
            }
            if (isGranted) {
                binding?.bRequestPermission?.visibility = View.GONE
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
                binding?.bRequestPermission?.visibility = View.VISIBLE
            }
        }
}