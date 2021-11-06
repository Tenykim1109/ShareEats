package com.sharewanted.shareeats.src.main.home.participate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.databinding.ActivityParticipateBinding
import com.sharewanted.shareeats.src.main.MainActivity

private const val TAG = "ParticipateActivity_싸피"
class ParticipateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParticipateBinding
    private lateinit var adapter: ParticipateAdapter
    private var list = mutableListOf<Menu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParticipateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.activityParticipateToolbar
        setSupportActionBar(toolbar)

        initView()

        binding.activityParticipateBtnCancel.setOnClickListener {
            Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show()
//            finish()
        }

        binding.activityParticipateBtnPayment.setOnClickListener {
            Toast.makeText(this, "걸제하기", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }

        var cardList = resources.getStringArray(R.array.credit_card)

        val cardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cardList)

        binding.activityParticipateSpinner.adapter = cardAdapter

        binding.activityParticipateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@ParticipateActivity, "${cardList[position]}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    // RecyclerView 초기화
    private fun initView() {
        adapter = ParticipateAdapter(list)
        binding.activityParticipateRvMenu.adapter = adapter
        binding.activityParticipateRvMenu.layoutManager = LinearLayoutManager(this)
    }
}