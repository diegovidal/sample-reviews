package com.dvidal.samplereviews.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dvidal.samplereviews.MyApplication
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.navigator.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    private fun injectDagger() {
        (application as MyApplication).appComponent.activityComponent()
            .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDagger()
    }
}
