package com.sharewanted.shareeats.src.main.home.order

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.CommonUtils
import com.sharewanted.shareeats.databinding.ActivityOrderBinding
import com.sharewanted.shareeats.src.main.home.order.findStore.FindStoreActivity
import com.sharewanted.shareeats.src.main.home.order.orderDto.PersonMenu
import com.sharewanted.shareeats.src.main.home.order.orderDto.StoreMenu
import com.sharewanted.shareeats.src.main.home.order.selectLocation.SelectLocationActivity
import com.sharewanted.shareeats.src.main.home.order.selectMenu.SelectMenuActivity
import java.text.SimpleDateFormat
import java.util.*


class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private var mDatabase = Firebase.database
    private val POST = "Post"
    private lateinit var foodType: String
    private var completed: Boolean = false
    private var storeId: String? = null
    private var selectedMenuList = mutableListOf<StoreMenu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // custom Toolbar 적용
        val toolbar = binding.activityOrderToolbar
        setSupportActionBar(toolbar)

        ArrayAdapter.createFromResource(this, R.array.food_type_array, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.activityOrderSpinnerType.adapter = it
        }

        binding.activityOrderSpinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                foodType = binding.activityOrderSpinnerType.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                completed = false
                Toast.makeText(this@OrderActivity, "음식 분류를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        binding.activityOrderBtnFindStore.setOnClickListener {
            val intent = Intent(this, FindStoreActivity::class.java)
            activityResult.launch(intent)
        }

        binding.activityOrderBtnSelectMenu.setOnClickListener {
            val intent = Intent(this, SelectMenuActivity::class.java).apply {
                putExtra("storeId", storeId)
            }

            if (binding.activityOrderTvStore.text == null) {
                Toast.makeText(this, "음식점을 먼저 선택하세요.", Toast.LENGTH_SHORT).show()
            } else {
                activityResult.launch(intent)
            }
        }

        binding.activityOrderBtnSelectLocation.setOnClickListener {
            val intent = Intent(this, SelectLocationActivity::class.java)
            if (binding.activityOrderTvMenu.text == null) {
                Toast.makeText(this, "메뉴를 먼저 선택하세요.", Toast.LENGTH_SHORT).show()
            } else {
                activityResult.launch(intent)
            }
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
            val store = binding.activityOrderTvStore.text.toString()
            val minPrice = binding.activityOrderTvMinPrice.text.toString().toInt()
            val location = binding.activityOrderTvLocation.text.toString()

            val postDate = dateFormat.parse(dateFormat.format(temp).toString()).time
            val deadLine = postDate
            val content = binding.activityOrderEtContent.text.toString()
            val participant = PersonMenu("Daniel", "thighBurger", 2, 10000)

            val list = mutableListOf<PersonMenu>()
            list.add(participant)

//            val post = Post(title, store, minPrice, location, postDate, deadLine, content, list)

//            val postRef = mDatabase.getReference(POST).child(post.title)
//            postRef.setValue(post)

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

            if (it.data?.getStringExtra("resultType") == "findStore") {
                binding.activityOrderTvStore.text = it.data?.getStringExtra("storeName").toString()
                storeId = it.data?.getStringExtra("storeId").toString()
                binding.activityOrderTvMinPrice.text = CommonUtils().makeComma(it.data?.getStringExtra("storeMinPrice").toString().toInt())
            } else if (it.data?.getStringExtra("resultType") == "selectMenu") {
                val menuList = it.data?.getParcelableArrayListExtra<StoreMenu>("menuList")
                val quantityList = it.data?.getIntegerArrayListExtra("menuQuantityList")

                var menuString = ""
                for (i in quantityList!!.indices) {
                    if (quantityList[i] != 0) {
                        val storeMenu = menuList?.get(i)?.let { it1 -> StoreMenu(menuList[i].name, menuList[i].price, menuList[i].photo, it1.desc, quantityList[i]) }
                        selectedMenuList.add(storeMenu!!)
                        menuString += "${storeMenu.name} ${storeMenu.quantity}개 \n"
                    }
                }
                binding.activityOrderTvMenu.text = menuString
            } else if (it.data?.getStringExtra("resultType") == "selectLocation") {
                val location = it.data?.getStringExtra("location")
                binding.activityOrderTvLocation.setText(location)
            }



        }
    }
}