package com.samy.azkar2.ui.ziker

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.samy.azkar2.databinding.ItemZikerBinding
import com.samy.azkar2.pojo.Hadith
import com.samy.azkar2.utils.Constants
import com.samy.azkar2.utils.Utils
import com.samy.azkar2.utils.Utils.replaceArabicNumbers
import javax.inject.Inject


class ZikerPageAdapter @Inject constructor() :
    ListAdapter<Hadith, ZikerPageAdapter.ViewHolder>(DiffCallback()) {


    class DiffCallback : DiffUtil.ItemCallback<Hadith>() {
        override fun areItemsTheSame(
            oldItem: Hadith, newItem: Hadith
        ): Boolean = newItem == oldItem

        override fun areContentsTheSame(
            oldItem: Hadith, newItem: Hadith
        ): Boolean = newItem == oldItem
    }

    inner class ViewHolder(val binding: ItemZikerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Hadith, position: Int) {
            binding.tvMatn.text = data.matn.replaceArabicNumbers()
            binding.tvIsnad.text = data.isnad.replaceArabicNumbers()

            if (data.isnad.isEmpty()) binding.lineBetween.visibility = View.INVISIBLE

            onclick(data)
            initialTextSize()
        }

        private fun onclick(data: Hadith) {
            binding.tvIsnad.setOnClickListener {
                onItemClickListener?.let { it1 -> it1(data) }
            }
            binding.tvMatn.setOnClickListener {
                onItemClickListener?.let { it1 -> it1(data) }
            }
            binding.layout.setOnClickListener {
                onItemClickListener?.let { it1 -> it1(data) }
            }
            binding.sv.getChildAt(0).setOnClickListener {
                onItemClickListener?.let { it1 -> it1(data) }
            }
            binding.ll.setOnClickListener {
                onItemClickListener?.let { it1 -> it1(data) }
            }


        }

        private fun initialTextSize() {
            val small =
                Utils.getSharedPreferencesFloat(
                    binding.root.context,
                    Constants.FontSizeFile,
                    Constants.SMALL,
                    15.60f
                )
            val median =
                Utils.getSharedPreferencesFloat(
                    binding.root.context,
                    Constants.FontSizeFile,
                    Constants.MEDIAN,
                    18.20f
                )
            val high =
                Utils.getSharedPreferencesFloat(
                    binding.root.context,
                    Constants.FontSizeFile,
                    Constants.HIGH,
                    20.80f
                )

            binding.tvMatn.setTextSize(TypedValue.COMPLEX_UNIT_SP, median)
            binding.tvIsnad.setTextSize(TypedValue.COMPLEX_UNIT_SP, small)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemZikerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)

    }

    private var onItemClickListener: ((Hadith) -> Unit)? = null


    fun setOnItemClickListener(listener: (Hadith) -> Unit) {
        onItemClickListener = listener
    }


}