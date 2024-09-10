package com.samy.azkar2.ui.about

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.samy.azkar2.R
import com.samy.azkar2.databinding.ActivityAboutBinding
import com.samy.azkar2.utils.Constants
import com.samy.azkar2.utils.Utils
import com.samy.azkar2.utils.Utils.myLog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.facebookImg.setOnClickListener {
            val facebookUrl =
                "https://www.facebook.com/waleed.elrefaiee"
            val facebookIntent = Intent(Intent.ACTION_VIEW)
            facebookIntent.data = Uri.parse(facebookUrl)
            startActivity(facebookIntent)
        }
        initialTextSize()
        textAbout()
    }

    private fun textAbout() {
        /*
        الحمد لله رب العالمين والصلاة والسلام على رسول الله صلى الله عليه وسلم وبعد
فهذا تطبيق صحيح الأذكار وهو مأخوذ من مؤلفات  الشيخ الدكتور وليد الرفاعي  كما نرحب بالاقتراحات والملاحظات عبر نموذج التواصل بالموقع.
ندعو الله عز وجل أن يتقبل منا هذا العمل وينفعنا وإياكم به. رجاءا لا تنسونا من صالح دعائكم وساهموا معنا في نشر تطبيق صحيح الأذكار والأدعية النبوية .
         */
        val data =
            "الحمد لله رب العالمين والصلاة والسلام على رسول الله صلى الله عليه وسلم وبعد: \n" +
                    "فهذا تطبيق صحيح الأذكار وهو مأخوذ من مؤلفات\n" +
                    "\nالشيخ الدكتور / ابو وسام وليد الرفاعي\n" +
                    "كما نرحب بالاقتراحات والملاحظات عبر نموذج التواصل بالموقع.\n" +
                    "ندعو الله عز وجل أن يتقبل منا هذا العمل وينفعنا وإياكم به. رجاءا لا تنسونا من صالح دعائكم وساهموا معنا في نشر تطبيق صحيح الأذكار والأدعية النبوية."

        val key = "الشيخ الدكتور / ابو وسام وليد الرفاعي"
        val start = data.indexOf(key)
        val end = start + key.length
        val spannableString = SpannableString(data)
        // Define custom span
        val customSpan = CustomSpan(this, 32, 32, 28f, R.font.alfont_com_alfont_com_4_30, true)
        spannableString.setSpan(customSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        binding.tvAbout.text = spannableString
    }

    private fun initialTextSize() {
        val small =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.SMALL, 15.60f)
        val median =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.MEDIAN, 18.20f)
        val high =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.HIGH, 20.80f)

        binding.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, high)
        binding.tvAbout.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
    }

}