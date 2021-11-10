package io.afdon.bitcoinprice.presentation

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit
import io.afdon.bitcoinprice.MainApp
import io.afdon.bitcoinprice.R
import io.afdon.bitcoinprice.presentation.main.MainFragment
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            for ((_, value) in permissions) {
                if (!value) {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
                    break
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = (application as MainApp)
            .appComponent.getActivitySubcomponentFactory().create().getFragmentFactory()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            add(android.R.id.content, MainFragment::class.java, null)
            addToBackStack(null)
        }

        var permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
        requestMultiplePermissions.launch(permissions)
    }
}