package com.sharewanted.shareeats.src.main.home.participate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.database.creditcard.CreditCard
import com.sharewanted.shareeats.database.creditcard.CreditCardRepository
import com.sharewanted.shareeats.databinding.ActivityParticipateBinding
import com.sharewanted.shareeats.src.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val TAG = "ParticipateActivity_싸피"
class ParticipateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParticipateBinding
    private lateinit var adapter: ParticipateAdapter
    private var list = mutableListOf<Menu>()
    var selectedCard: CreditCard?=null

    private var cardPassword: String?=null

    lateinit var creditCardRepository: CreditCardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParticipateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        creditCardRepository = CreditCardRepository.get()

        val cardList = resources.getStringArray(R.array.credit_card)

        val toolbar = binding.activityParticipateToolbar
        setSupportActionBar(toolbar)

        initView()


        Log.d(TAG, "onCreate: ")
        binding.activityParticipateBtnCancel.setOnClickListener {
            Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show()
//            finish()
        }

        binding.activityParticipateBtnPayment.setOnClickListener {
            val company = binding.activityParticipateSpinner.selectedItem.toString()
            val number = binding.activityParticipateCardNumber.text.toString()
            val expiry = binding.activityParticipateExpiry.text.toString()
            val cvc = binding.activityParticipateCvc.text.toString()
            val password = binding.activityParticipatePassword.text.toString()

            if (cardPassword != "") {
                if (cardPassword.equals(password)) {
                    Toast.makeText(this, "결제하기", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onCreate: $company $number $expiry $cvc $password")
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
                } else {
                    Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            } else {
                val creditCard = CreditCard("joseph", company, number, expiry, cvc, password)
                CoroutineScope(Dispatchers.IO).launch {
                    creditCardRepository.insert(creditCard)
                }
                cardPassword = password
            }
        }


        val cardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cardList)

        binding.activityParticipateSpinner.adapter = cardAdapter

        binding.activityParticipateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@ParticipateActivity, "${cardList[position]}", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    CoroutineScope(Dispatchers.IO).async {
                        selectedCard = creditCardRepository.getCreditCard("joseph", cardList[position])
                    }.await()

                    updateView()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    private fun initView() {
        adapter = ParticipateAdapter(list)
        binding.activityParticipateRvMenu.adapter = adapter
        binding.activityParticipateRvMenu.layoutManager = LinearLayoutManager(this)

    }

    fun updateView() {
        if (selectedCard != null) {
            binding.activityParticipateCardNumber.setText(selectedCard!!.number)
            binding.activityParticipateExpiry.setText(selectedCard!!.expiry)
            binding.activityParticipateCvc.setText(selectedCard!!.cvc)
            cardPassword = selectedCard!!.password
        } else {
            binding.activityParticipateCardNumber.setText("")
            binding.activityParticipateExpiry.setText("")
            binding.activityParticipateCvc.setText("")
            binding.activityParticipatePassword.setText("")
            cardPassword = ""
        }
    }
}