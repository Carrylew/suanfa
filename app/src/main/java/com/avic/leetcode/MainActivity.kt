package com.avic.leetcode

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var calculate: Button
    lateinit var wan: EditText
    lateinit var tiao: EditText
    lateinit var tong: EditText
    lateinit var result: TextView
    lateinit var ma: Ma

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ma = Ma(this)
        initView()
        initEvent()
    }

    private fun initView() {
        calculate = findViewById(R.id.calculate)
        result = findViewById(R.id.result)
        wan = findViewById(R.id.wan)
        tiao = findViewById(R.id.tiao)
        tong = findViewById(R.id.tong)
    }

    private fun initEvent() {
        calculate.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        val wanList = mutableListOf<Int>()
        val tongList = mutableListOf<Int>()
        val tiaoList = mutableListOf<Int>()
        wan.text.toString().forEach {
            wanList.add(it.toString().toInt())
        }
        tong.text.toString().forEach {
            tongList.add(it.toString().toInt() + 10)
        }
        tiao.text.toString().forEach {
            tiaoList.add(it.toString().toInt() + 20)
        }
        val resultString = ma.calcTing(wanList, tongList, tiaoList)
        if (resultString.isNotEmpty()) {
            result.text = resultString
        } else {
            result.text = "你没有叫"
        }
    }
}