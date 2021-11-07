package com.sharewanted.shareeats.src.main.home.order

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.databinding.ActivityOrderBinding
import com.sharewanted.shareeats.src.main.home.order.findRestaurant.FindRestaurantActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.PersonMenu
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.order.selectLocation.SelectLocationActivity
import com.sharewanted.shareeats.src.main.home.order.selectMenu.SelectMenuActivity
import java.text.SimpleDateFormat
import java.util.*


class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private var mDatabase = Firebase.database
    private val POST = "Post"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // custom Toolbar 적용
        val toolbar = binding.activityOrderToolbar
        setSupportActionBar(toolbar)

        binding.activityOrderBtnFindRestaurant.setOnClickListener {
            val intent = Intent(this, FindRestaurantActivity::class.java)
            activityResult.launch(intent)
        }

        binding.activityOrderBtnSelectMenu.setOnClickListener {
            val intent = Intent(this, SelectMenuActivity::class.java)
            activityResult.launch(intent)
        }

        binding.activityOrderBtnSelectLocation.setOnClickListener {
            val intent = Intent(this, SelectLocationActivity::class.java)
            activityResult.launch(intent)
        }

        val current = System.currentTimeMillis()
        val temp = Date(current)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
        val detailFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val timeZone = TimeZone.getTimeZone("Asia/Seoul")
            dateFormat.timeZone = timeZone
            detailFormat.timeZone = timeZone
        }

        val callback = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->

            val detailTime = dateFormat.format(temp).split(" ")
            binding.activityOrderTvTime.text = "${detailTime[0]} ${i}:${i2}"
        }

        binding.activityOrderBtnSelectTime.setOnClickListener {

            val currentTime = detailFormat.format(temp)
            val timeArr = currentTime.split(":")
            val timeDialog = TimePickerDialog(this, callback, timeArr[0].toInt(), timeArr[1].toInt(), true)

            timeDialog.show()
        }

        // 툴바 x버튼, 확인 버튼 클릭 테스트
        binding.activityOrderBtnSave.setOnClickListener {

            val title = binding.activityOrderTvTitle.text.toString()
            val restaurant = binding.activityOrderTvRestaurant.text.toString()
            val minPrice = binding.activityOrderTvMinPrice.text.toString().toInt()
            val location = binding.activityOrderTvLocation.text.toString()

            val postDate = dateFormat.parse(dateFormat.format(temp).toString()).time
            val deadLine = postDate
            val content = binding.activityOrderEtContent.text.toString()
            val participant = PersonMenu("Daniel", "thighBurger", 2, 10000)

            val list = mutableListOf<PersonMenu>()
            list.add(participant)

            val post = Post(title, restaurant, minPrice, location, postDate, deadLine, content, list)

            val postRef = mDatabase.getReference(POST).child(post.title)
            postRef.setValue(post)

            Toast.makeText(this, "글 작성완료", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.activityOrderBtnCancel.setOnClickListener {
            Toast.makeText(this, "글쓰기 취소", Toast.LENGTH_SHORT).show()
            finish()
        }


    }


    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {



        }
    }
}