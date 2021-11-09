package io.afdon.bitcoinprice.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import io.afdon.bitcoinprice.MainApp
import io.afdon.bitcoinprice.R
import io.afdon.bitcoinprice.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = (application as MainApp)
            .appComponent.getActivitySubcomponentFactory().create().getFragmentFactory()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            add(android.R.id.content, MainFragment::class.java, null)
            addToBackStack(null)
        }
    }
}