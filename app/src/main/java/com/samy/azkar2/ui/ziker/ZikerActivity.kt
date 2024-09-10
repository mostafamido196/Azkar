package com.samy.azkar2.ui.ziker

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.samy.azkar2.R
import com.samy.azkar2.databinding.ActivityZikerBinding
import com.samy.azkar2.pojo.Ziker
import com.samy.azkar2.ui.base.help.HelperDialog
import com.samy.azkar2.utils.Constants
import com.samy.azkar2.utils.Utils
import com.samy.azkar2.utils.Utils.myLog
import com.samy.azkar2.utils.Utils.replaceArabicNumbers
import com.samy.azkar2.utils.Utils.replaceArabicString
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ZikerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZikerBinding

    private val viewModel: ZikerViewModel by viewModels()


    @Inject
    lateinit var adapter: ZikerPageAdapter


    var isViberation: Boolean = true
    var isSound: Boolean = true
    var isTransaction: Boolean = true
    var mediaPlayer: MediaPlayer? = null
    var vibrator: Vibrator? = null
    var vibrationEffect1: VibrationEffect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZikerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        orientation(savedInstanceState)
        setup()
        observe()
        onBack()
    }

    private fun orientation(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            val currentPage = savedInstanceState.getInt("current_page", 0)
            // Ensure this is run after the view has been laid out
            binding.viewpager.post {
                binding.viewpager.setCurrentItem(currentPage, false)
            }
        }
    }


    var indexOfCurrentHadith: Int = 0 //when the phone power off then power on
    override fun onResume() {
        super.onResume()
        binding.viewpager.currentItem =
            indexOfCurrentHadith //when the phone power off then power on

    }

    override fun onPause() {
        super.onPause()
        indexOfCurrentHadith =
            binding.viewpager.currentItem //when the phone power off then power on
    }


    private fun onBack() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setup() {
        initialState()
        viewpager()
        progress()
        initialTextSize()
        transactionSettings()
    }


    //orientation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("state", viewModel.getZikerItemState(binding.viewpager.currentItem))
        outState.putInt("current_page", binding.viewpager.currentItem)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // get values from saved state
        super.onRestoreInstanceState(savedInstanceState)
        val state = savedInstanceState.getInt("state")
        val currentPage = savedInstanceState.getInt("current_page")
        binding.viewpager.post {
            binding.viewpager.setCurrentItem(currentPage, false)
//            myLog("Restored current page: $currentPage")
        }
        binding.progressBar.max = viewModel.getZikerMaxItemState(currentPage)
        binding.progressBar.progress = state
        viewModel.setZikerItemState(binding.viewpager.currentItem, state)
    }

    private fun initialTextSize() {
        val small =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.SMALL, 15.60f)
        val median =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.MEDIAN, 18.20f)
        val high =
            Utils.getSharedPreferencesFloat(this, Constants.FontSizeFile, Constants.HIGH, 20.80f)

        binding.titlePage.setTextSize(TypedValue.COMPLEX_UNIT_SP, high)
        binding.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, high)
    }

    private fun initialState() {
        val name = intent.getStringExtra(Constants.TitleZikerPass)
        binding.title.text = name
//        azkar.map { ziker: Ziker -> if (ziker.name == name) this.ziker = ziker }
        viewModel.filterAzkar(name)
        binding.noRepeat.text =
            viewModel.getZikerMaxItemState(binding.viewpager.currentItem).replaceArabicString()

    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.timerStateFlow.collect {
                if (it) {
                    goToNextHadith()
                    openCounter()
                }

            }

        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun transactionSettings() {
        isTransaction = Utils.getSharedPreferencesBoolean(
            this, Constants.FontSizeFile, Constants.TRANSACTION, true
        )
        isViberation = Utils.getSharedPreferencesBoolean(
            this, Constants.FontSizeFile, Constants.VIBRATION, true
        )
        isSound =
            Utils.getSharedPreferencesBoolean(this, Constants.FontSizeFile, Constants.SOUND, true)
        vibrationSetting()


    }

    private fun vibrationSetting() {
        mediaPlayer = MediaPlayer.create(this, R.raw.light_button)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings: LongArray = longArrayOf(50, 50, 50, 50, 50, 50)
            val amplitudes: IntArray =
                intArrayOf(50, 50, 50, 50, 50, 50)
            val repeatIndex = -1 // Do not repeat.

            vibrationEffect1 = VibrationEffect.createWaveform(timings, amplitudes, repeatIndex)
        }

    }

    private fun viewpager() {
        binding.viewpager.adapter = adapter
        adapter.submitList(viewModel.getZikerList())
        onViewPageCallBack()
    }

    private fun progress() {
        updateProgress()
        onClickProgress()
    }

    private fun onViewPageCallBack() {
        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            // This method is triggered when there is any scrolling activity for the current page
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int,
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                updateProgress()
            }

            // triggered when you select a new page
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateProgress()
            }

            // triggered when there is
            // scroll state will be changed
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                updateProgress()
            }
        })

    }


    private fun updateProgress() {
        binding.progressBar.max = viewModel.getZikerMaxItemState(binding.viewpager.currentItem)
        binding.progressBar.setProgress(
            viewModel.getZikerItemState(binding.viewpager.currentItem),
            true
        )
        binding.tvStepsTaken.text =
            "${viewModel.getZikerItemState(binding.viewpager.currentItem)}".replaceArabicNumbers()

        binding.noCounter.text =
            "الذكر ${binding.viewpager.currentItem + 1} من ${viewModel.getZikerSize()}".replaceArabicNumbers()
        binding.noRepeat.text =
            viewModel.getZikerMaxItemState(binding.viewpager.currentItem).replaceArabicString()

    }

    private fun onClickProgress() {
        binding.progressBar.setOnClickListener {
            myLog("progressBar.setOnClickListener")
            increaseProgressCounter()
        }
        binding.tvStepsTaken.setOnClickListener {
            myLog("tvStepsTaken.setOnClickListener")
            increaseProgressCounter()
        }
        binding.viewpager.setOnClickListener {
            myLog("viewpager.setOnClickListener")
            increaseProgressCounter()
        }
        adapter.setOnItemClickListener {
            myLog("setOnItemClickListener")
            increaseProgressCounter()
        }
    }

    private fun increaseProgressCounter() {
        if (!viewModel.isHadithFinish(binding.viewpager.currentItem)) {
            if (isSound) mediaPlayer?.start()
            binding.progressBar.setProgress(binding.progressBar.progress + 1, true)
            binding.tvStepsTaken.text =
                binding.progressBar.progress.toString().replaceArabicNumbers()
            viewModel.increaseZikerItemState(binding.viewpager.currentItem)

            myLog("ziker.arr[binding.viewpager.currentItem].state += 1")
        }
        if (viewModel.isHadithFinish(binding.viewpager.currentItem) && !viewModel.isLastHadith(
                binding.viewpager.currentItem
            )
        ) {
            fullProgress_goToNextHadith()
            makeVibrate()
        } else if (viewModel.isHadithFinish(binding.viewpager.currentItem) && viewModel.isLastHadith(
                binding.viewpager.currentItem
            )
        ) {
            fullprogress()
            makeVibrate()
            showPopUpMenu("انتهى الذكر")
        }
    }

    private fun showPopUpMenu(msg: String) {
        var dialog = HelperDialog(msg) {
            finish()
        }
        dialog.show(supportFragmentManager, null)

    }


    private fun makeVibrate() {
        if (isViberation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vibrator?.vibrate(vibrationEffect1)
            else
                vibrator?.vibrate(200)
        }
    }


    private fun fullprogress() {
        if (isTransaction)
            binding.progressBar.setProgress(binding.progressBar.progress + 1, true)

    }

    private fun fullProgress_goToNextHadith() {
        stopCounter()
        fullprogress()
        viewModel.late_second()

    }

    private fun stopCounter() {
        binding.progressBar.isClickable = false
        binding.tvStepsTaken.isClickable = false
    }

    private fun openCounter() {
        binding.progressBar.isClickable = true
        binding.tvStepsTaken.isClickable = true
    }


    private fun goToNextHadith() {
        myLog("goToNextHadith")
        if (isTransaction) {
            binding.viewpager.currentItem = binding.viewpager.currentItem + 1
            updateProgress()
        }

    }


}