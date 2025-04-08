package com.example.to_dolist_3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // TextView
        val splashText = findViewById<TextView>(R.id.tvTo_Do_List)
        val tvToDoListAnim = AnimationUtils.loadAnimation(this, R.anim.tv_to_do_list)

        // ImageView
        val imageToDoList = findViewById<ImageView>(R.id.image_start)
        val imageStartAnim = AnimationUtils.loadAnimation(this, R.anim.image_start)

        tvToDoListAnim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                imageToDoList.startAnimation(imageStartAnim)
                imageToDoList.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })

        splashText.startAnimation(tvToDoListAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}