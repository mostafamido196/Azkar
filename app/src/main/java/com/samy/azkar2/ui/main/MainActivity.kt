package com.samy.azkar2.ui.main


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.samy.azkar2.R
import com.samy.azkar2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.samy.azkar2.ui.about.AboutActivity
import com.samy.azkar2.ui.setting.SettingActivity
import com.samy.azkar2.utils.Constants
import com.samy.azkar2.utils.Utils

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastNavTVSelected: TextView? = null
    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        navHostFragment.navController
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
        goto()

    }

    private fun goto() {

        binding.navContent.tvAbout.setOnClickListener {
            selectItemNavDrower(binding.navContent.tvAbout)
            val myIntent = Intent(this, AboutActivity::class.java)
            startActivity(myIntent)
            selectItemNavDrower(binding.navContent.tvHome)//علشان لما نرجع نلاقيها مختاره الmain
        }
        binding.navContent.tvSetting.setOnClickListener {
            selectItemNavDrower(binding.navContent.tvSetting)
            val myIntent = Intent(this, SettingActivity::class.java)
            startActivity(myIntent)
            selectItemNavDrower(binding.navContent.tvHome)//علشان لما نرجع نلاقيها مختاره الmain
        }
    }

    private fun setup() {
        binding.navContent.tvHome.isSelected = true

        binding.ivMenu.setOnClickListener {
            if (binding.drawerLayout.isOpen)
                closeDrawer()
            else
                openNavSideBar()
        }

        binding.navContent.tvHome.setOnClickListener {
            selectItemNavDrower(binding.navContent.tvHome)

        }
        binding.navContent.tvSetting.setOnClickListener {
           selectItemNavDrower(binding.navContent.tvSetting)

        }

        initialTextSize()
    }
    private fun initialTextSize() {
        val small =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.SMALL, 15.60f)
        val median =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.MEDIAN, 18.20f)
        val high =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.HIGH, 20.80f)

        binding.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, high)
        binding.navContent.tvAbout.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.navContent.tvHome.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.navContent.tvSetting.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
    }
    private fun selectItemNavDrower(textView: TextView) {
        deselect()
        lastNavTVSelected = textView
        select()
    }


    private fun select() {

        closeDrawer()
        if (lastNavTVSelected != null)
            lastNavTVSelected!!.isSelected = true
    }

    @SuppressLint("SuspiciousIndentation")
    private fun deselect() {
        if (lastNavTVSelected != null)
            lastNavTVSelected!!.isSelected = false
            binding.navContent.tvHome!!.isSelected = false
    }

    private fun closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    fun openNavSideBar() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}