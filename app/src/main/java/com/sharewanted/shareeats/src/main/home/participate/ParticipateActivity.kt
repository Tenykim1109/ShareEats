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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.database.creditcard.CreditCard
import com.sharewanted.shareeats.database.creditcard.CreditCardRepository
import com.sharewanted.shareeats.databinding.ActivityParticipateBinding
import com.sharewanted.shareeats.src.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ParticipateActivity_싸피"
class ParticipateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParticipateBinding
    private lateinit var adapter: ParticipateAdapter
    private var list = mutableListOf<Menu>()

    private var cardPassword: String?=null

    lateinit var creditCardRepository: CreditCardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParticipateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        creditCardRepository = CreditCardRepository.get()

        var cardList = resources.getStringArray(R.array.credit_card)

        val toolbar = binding.activityParticipateToolbar
        setSupportActionBar(toolbar)

        initView()

        binding.activityParticipateBtnCancel.setOnClickListener {
            Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show()
//            finish()
        }

        binding.activityParticipateBtnPayment.setOnClickListener {
            var company = binding.activityParticipateSpinner.selectedItem.toString()
            var number = binding.activityParticipateCardNumber.text.toString()
            var expiry = binding.activityParticipateExpiry.text.toString()
            var cvc = binding.activityParticipateCvc.text.toString()
            var password = binding.activityParticipatePassword.text.toString()

            Toast.makeText(this, "결제하기", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "$company $number $expiry $cvc $password", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onCreate: $company $number $expiry $cvc $password")
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)

            var creditCard = CreditCard("joseph", company, number, expiry, cvc, password)
            CoroutineScope(Dispatchers.IO).launch {
                creditCardRepository.insert(creditCard)
            }

        }


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

        var tempId = "joseph"
        var tempCompany = "KB국민카드"
        var defaultCard = creditCardRepository.getCreditCard(tempId, tempCompany)

        if (defaultCard != null) {
            defaultCard.observe(this) {
                binding.activityParticipateCardNumber.setText(it.number)
                binding.activityParticipateExpiry.setText(it.expiry)
                binding.activityParticipateCvc.setText(it.cvc)
                cardPassword = it.password
            }
        }
    }
}