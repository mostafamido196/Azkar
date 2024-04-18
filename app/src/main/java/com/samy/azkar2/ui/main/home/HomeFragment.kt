package com.samy.azkar2.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.samy.azkar2.R
import com.samy.azkar2.databinding.FragmentHomeBinding
import com.samy.azkar2.pojo.Ziker
import com.samy.azkar2.ui.ziker.ZikerActivity
import com.samy.azkar2.utils.Constants
import com.samy.azkar2.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var adapter: NameAdapter

    @Inject
    lateinit var allZiker: List<Ziker>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        setup()
        goto()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setup() {
        binding.rv.adapter = adapter
        val arr: List<String> = allZiker.map { ziker: Ziker -> ziker.name }
        adapter.submitList(arr)
        adapter.notifyDataSetChanged()

    }

    private fun goto() {
        adapter.setOnItemClickListener {
            val myIntent = Intent(activity, ZikerActivity::class.java)
            myIntent.putExtra(Constants.TitleZikerPass, it)
            startActivity(myIntent)

        }
    }


}