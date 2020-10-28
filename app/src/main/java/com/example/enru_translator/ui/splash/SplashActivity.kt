package com.example.enru_translator.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.enru_translator.R
import com.example.enru_translator.ui.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        actionBar?.elevation= 0F
//        getSupportActionBar()?.setElevation(0F);
        setContentView(R.layout.activity_splash)
        setSupportActionBar(toolbar_splash)

        Handler().postDelayed(Runnable {
            // This method will be executed once the timer is over

            // This method will be executed once the timer is over
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 500)
    }
}