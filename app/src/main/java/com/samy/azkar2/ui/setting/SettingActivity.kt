package com.samy.azkar2.ui.setting

import android.os.Bundle
import android.util.TypedValue
import android.widget.Switch
import androidx.annotation.DimenRes
import androidx.appcompat.app.AppCompatActivity
import com.samy.azkar2.R
import com.samy.azkar2.databinding.ActivitySettingBinding
import com.samy.azkar2.pojo.FontSize
import com.samy.azkar2.utils.Constants
import com.samy.azkar2.utils.Utils
import com.samy.azkar2.utils.Utils.getSharedPreferencesBoolean
import com.samy.azkar2.utils.Utils.getSharedPreferencesFloat
import com.samy.azkar2.utils.Utils.myLog
import com.samy.azkar2.utils.Utils.setSharedPreferencesBoolean
import com.samy.azkar2.utils.Utils.setSharedPreferencesFloat
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log


@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
//        observe()
        onBack()
    }

    private fun onBack() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.tvDone.setOnClickListener {
            finish()
        }
    }

    private fun setup() {
        fonts()
        switchers()
//        binding.test.setOnClickListener {
//            myLog("mos samy","binding.title.textSize  ${binding.title.textSize}")
//            myLog("mos samy","binding.tvTitleFount.textSize  ${binding.tvTitleFount.textSize}")
//            myLog("mos samy","binding.tvTxtShow.textSize  ${binding.tvTxtShow.textSize}")
//            myLog("mos samy","binding.tvTxtSize.textSize  ${binding.tvTxtSize.textSize}")
//        }
    }

    private fun fonts() {
        initialTextSize()
        fontSetting()
    }

    private fun switchers() {
        initialSwitchers()
        onclickSwitchers()
    }

    private fun initialSwitchers() {
        val transaction = getSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.TRANSACTION,true)
        val vibration = getSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.VIBRATION,true)
        val sound = getSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.SOUND,true)
        binding.swTransaction.isChecked = transaction
        binding.swVibration.isChecked = vibration
        binding.swSound.isChecked = sound
    }

    private fun onclickSwitchers() {

        binding.swTransaction.setOnCheckedChangeListener { buttonView, isChecked ->
            // Handle switch state change
            if (isChecked) {
                // Switch is ON
                setSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.TRANSACTION,true)
                myLog("mos samy","getSharedPreferencesBoolean ${getSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.TRANSACTION,true)}")
            } else {
                // Switch is OFF
                setSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.TRANSACTION,false)
                myLog("mos samy","getSharedPreferencesBoolean ${getSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.TRANSACTION,true)}")
            }
        }
        binding.swSound.setOnCheckedChangeListener { buttonView, isChecked ->
            // Handle switch state change
            if (isChecked) {
                // Switch is ON
                setSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.SOUND,true)
            } else {
                // Switch is OFF
                setSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.SOUND,false)
            }
        }
        binding.swVibration.setOnCheckedChangeListener { buttonView, isChecked ->
            // Handle switch state change
            if (isChecked) {
                // Switch is ON
                setSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.VIBRATION,true)
            } else {
                // Switch is OFF
                setSharedPreferencesBoolean(this,Constants.FontSizeFile,Constants.VIBRATION,false)
            }
        }
    }

    private fun initialTextSize() {
        val small = getSharedPreferencesFloat(this,Constants.FontSizeFile,Constants.SMALL, 15.60f)
        val median = getSharedPreferencesFloat(this,Constants.FontSizeFile,Constants.MEDIAN, 18.20f)
        val high = getSharedPreferencesFloat(this,Constants.FontSizeFile,Constants.HIGH, 20.80f)

        binding.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, high)
        binding.tvDone.setTextSize(TypedValue.COMPLEX_UNIT_SP, high)
        binding.tvTitleFount.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.tvTxtShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.tvTxtSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.small.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.median.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.high.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
    }

    private fun fontSetting() {
        initialFontSizeRadio()
        onclickFontSizeRadio()
    }


    private fun initialFontSizeRadio() {
        val fontSize = getSharedPreferencesFloat(
            this, Constants.FontSizeFile, Constants.MEDIAN, 18.20f
        )
        when (fontSize) {
            15.60f -> {
                binding.radioLlSize.check(R.id.small)
                binding.tvTxtShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.6f)
            }
            18.20f -> {
                binding.radioLlSize.check(R.id.median)
                binding.tvTxtShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.2f)
            }
            20.80f -> {
                binding.radioLlSize.check(R.id.high)
                binding.tvTxtShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.8f)
            }
        }
    }

    private fun onclickFontSizeRadio() {
        binding.radioLlSize.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.small -> {
                    updateStyle(FontSize.SMALL)
                    radioGroup.check(R.id.small)
                }
                R.id.median -> {
                    updateStyle(FontSize.MEDIAN)
                    radioGroup.check(R.id.median)
                }
                R.id.high -> {
                    updateStyle(FontSize.HIGH)
                    radioGroup.check(R.id.high)
                }
            }

        }
    }

    private fun updateStyle(size: FontSize) {
        when (size.toString()) {
            FontSize.SMALL.toString() -> {
                binding.tvTxtShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.60f)
                update_text_description(com.intuit.ssp.R.dimen._10ssp,13f)
                update_text_normal(com.intuit.ssp.R.dimen._12ssp,15.60f)
                update_text_title(com.intuit.ssp.R.dimen._14ssp,18.20f)
            }
            FontSize.MEDIAN.toString() -> {
                binding.tvTxtShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.20f)
                update_text_description(com.intuit.ssp.R.dimen._12ssp,15.60f)
                update_text_normal(com.intuit.ssp.R.dimen._14ssp,18.20f)
                update_text_title(com.intuit.ssp.R.dimen._16ssp,20.80f)
            }
            FontSize.HIGH.toString() -> {
                binding.tvTxtShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.80f)
                update_text_description(com.intuit.ssp.R.dimen._14ssp,18.20f)
                update_text_normal(com.intuit.ssp.R.dimen._16ssp,20.80f)
                update_text_title(com.intuit.ssp.R.dimen._18ssp,23.40f)
            }
        }
    }

    private fun update_text_description(@DimenRes textSizeRes: Int,textSizeFloat: Float) {
        setSharedPreferencesFloat(this,Constants.FontSizeFile,Constants.SMALL,textSizeFloat)

    }

    private fun update_text_normal(textSize: Int,textSizeFloat: Float) {
        setSharedPreferencesFloat(this,Constants.FontSizeFile,Constants.MEDIAN,textSizeFloat)
    }

    private fun update_text_title(textSize: Int,textSizeFloat: Float) {
        setSharedPreferencesFloat(this,Constants.FontSizeFile,Constants.HIGH,textSizeFloat)
    }
}