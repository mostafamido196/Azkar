package com.samy.azkar2.ui.base.help

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.samy.azkar2.R
import com.samy.azkar2.databinding.DialogHelpBinding
import com.samy.azkar2.utils.Constants
import com.samy.azkar2.utils.Utils


class HelperDialog(
    private val msg: String? = null,
    private val mDialogsListener: (() -> Unit)? = null

) : DialogFragment() {

    private lateinit var binding: DialogHelpBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_help,
            container,
            false
        )
        isCancelable = false
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

        binding.tvBack.setOnClickListener {
            mDialogsListener?.let { click ->
                click()
            }
        }
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

    }

    private fun initialTextSize() {
        val small =
            Utils.getSharedPreferencesFloat(binding.root.context, Constants.FontSizeFile, Constants.SMALL, 15.60f)
        val median =
            Utils.getSharedPreferencesFloat(binding.root.context, Constants.FontSizeFile, Constants.MEDIAN, 18.20f)
        val high =
            Utils.getSharedPreferencesFloat(binding.root.context, Constants.FontSizeFile, Constants.HIGH, 20.80f)

        binding.tvMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.tvBack.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
        binding.tvCancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
    }

    private fun setUp() {
        initialTextSize()
        binding.tvMsg.text = msg
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val wlp: WindowManager.LayoutParams = dialog?.window!!.attributes
        wlp.gravity = Gravity.CENTER
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

}