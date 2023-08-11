package com.example.mvvm_practice_kotlin.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_practice_kotlin.R

class SplashScreen : AppCompatActivity() {
    var topAnim: Animation? = null
    var bottomAnim: Animation? = null
    var imageView: ImageView? = null
    var app_name: TextView? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen2)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        imageView = findViewById<ImageView>(R.id.imgLogo)
        app_name = findViewById<TextView>(R.id.tvLogo)
        imageView!!.animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        app_name!!.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        Handler().postDelayed({
            startActivity(
                Intent(
                    this@SplashScreen,
                    MainActivity::class.java
                )
            )
            finish()
        }, 3000)
    }
}