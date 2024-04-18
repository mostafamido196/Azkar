package com.samy.azkar2.ui.splashscreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.samy.azkar2.databinding.ActivitySplashScreenBinding
import com.samy.azkar2.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val fadeIn = ObjectAnimator.ofFloat(binding.layout, "alpha", 0f, 1f)
        fadeIn.duration = 2000 // Duration of the animation in milliseconds
        fadeIn.interpolator = AccelerateDecelerateInterpolator() // Interpolator for smooth animation
        fadeIn.startDelay = 250 // Delay before the animation starts in milliseconds
        fadeIn.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                binding.layout.visibility = View.VISIBLE // Make the ImageView visible before animating
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }
        })
        fadeIn.start()




    }
}