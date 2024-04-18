package com.samy.azkar2.ui.main.home

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samy.azkar2.databinding.ItemTitleZikerBinding
import com.samy.azkar2.ui.main.MainActivity
import com.samy.azkar2.utils.Constants
import com.samy.azkar2.utils.Utils
import javax.inject.Inject

class NameAdapter @Inject constructor() :
    ListAdapter<String, NameAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, Type: Int): ViewHolder =
        ViewHolder(
            ItemTitleZikerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemTitleZikerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String) {

            binding.zikerName.text = name

            binding.zikerName.setOnClickListener {

                onItemClickListener?.let { click ->
                    click(name)
                }
            }

            initialTextSize()


        }

        private fun initialTextSize() {
            val small = Utils.getSharedPreferencesFloat(
                binding.root.context,
                Constants.FontSizeFile,
                Constants.SMALL,
                15.60f
            )
            val median = Utils.getSharedPreferencesFloat(
                binding.root.context,
                Constants.FontSizeFile,
                Constants.MEDIAN,
                18.20f
            )
            val high = Utils.getSharedPreferencesFloat(
                binding.root.context,
                Constants.FontSizeFile,
                Constants.HIGH,
                20.80f
            )

            binding.zikerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, median!!)

        }

    }


    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String, newItem: String
        ): Boolean = newItem == oldItem

        override fun areContentsTheSame(
            oldItem: String, newItem: String
        ): Boolean = newItem == oldItem
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

}