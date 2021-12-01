package com.sharewanted.shareeats.src.main.home.postInfo

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.R
import com.sharewanted.shareeats.config.ApplicationClass
import com.sharewanted.shareeats.config.CommonUtils
import com.sharewanted.shareeats.databinding.ActivityPostInfoBinding
import com.sharewanted.shareeats.src.chat.ChatActivity
import com.sharewanted.shareeats.src.main.MainActivity
import com.sharewanted.shareeats.src.main.chat.models.ChatList
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.order.orderDto.StoreMenu
import com.sharewanted.shareeats.src.main.home.order.selectMenu.SelectMenuActivity
import com.sharewanted.shareeats.src.main.home.participate.Menu
import com.sharewanted.shareeats.src.main.home.participate.ParticipateActivity
import com.sharewanted.shareeats.src.main.userlogin.dto.UserDto
import com.sharewanted.shareeats.util.SharedPreferencesUtil
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "PostInfoActivity_싸피"
class PostInfoActivity : AppCompatActivity() {
    private lateinit var postId: String
    private var selectedMenuList = mutableListOf<StoreMenu>()
    private val mDatabase = Firebase.database.reference
    lateinit var user: UserDto
    private var menuList: MutableList<Menu> = arrayListOf()
    lateinit var mPost: Post

    private lateinit var binding: ActivityPostInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        binding = ActivityPostInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = ApplicationClass.sharedPreferencesUtil.getUser()

        postId = intent.getIntExtra("postId", 0).toString()

        binding.activityPostInfoBtnJoin.setOnClickListener {
            if (binding.activityPostInfoTvMenu.text != "") {

                Toast.makeText(this, "참여하기", Toast.LENGTH_SHORT).show()

                for (i in selectedMenuList.indices) {
                    Log.d(TAG, "onCreate: ${selectedMenuList[i].quantity}")
                    menuList.add(Menu(selectedMenuList[i].name, selectedMenuList[i].price, selectedMenuList[i].quantity, selectedMenuList[i].photo, selectedMenuList[i].desc))
                }

                val intent = Intent(this, ParticipateActivity::class.java).apply {
                    putExtra("post", mPost)
                    putExtra("menu", ArrayList(menuList))
                    putExtra("flag", 1)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "메뉴를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.activityPostInfoBtnChat.setOnClickListener {
            val chatRef = FirebaseDatabase.getInstance().getReference("Chat").push()

            val key = chatRef.key

            FirebaseDatabase.getInstance().getReference("Chat").child(key!!).setValue(
                ChatList("", SharedPreferencesUtil(this).getUser().id, binding.activityPostInfoTvWriter.text.toString(), "", ""))

            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("roomId", key)
            intent.putExtra("nickname", binding.activityPostInfoTvWriter.text.toString())
            startActivity(intent)

        }

    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart: ")

        binding.activityPostInfoBtnCancel.setOnClickListener {
            finish()
        }

        if (postId != null) {

            var loadCheck = false

            mDatabase.child("Post").child(postId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(postSnapshot: DataSnapshot) {
                        if (loadCheck == false) {
                            val post = postSnapshot.getValue(Post::class.java)

                            val peopleNum = postSnapshot.child("participant").children.count()

                            mDatabase.child("Store").child(post!!.storeId)
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {

                                        val storeName =
                                            snapshot.child("name").getValue(String::class.java)

                                        if (user.id == post!!.userId) {
                                            binding.activityPostInfoHeader6.visibility = View.GONE
                                            binding.activityPostInfoTvMenu.visibility = View.GONE
                                            binding.activityPostInfoBtnSelectMenu.visibility =
                                                View.GONE
                                            binding.activityPostInfoBtnJoin.visibility = View.GONE
                                            if (peopleNum < 2) {
                                                binding.activityPostInfoBtnDelete.visibility =
                                                    View.VISIBLE
                                                binding.activityPostInfoBtnChat.visibility =
                                                    View.GONE
                                            }
                                        }

                                        binding.activityPostInfoTvTitle.text = post!!.title
                                        binding.activityPostInfoTvWriter.text = post!!.userId
                                        var date = Date(post.date)
                                        val dateFormat =
                                            SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                                        binding.activityPostInfoTvWriteDate.text =
                                            dateFormat.format(date).toString()
                                        binding.activityPostInfoTvLocation.text = post!!.place
                                        date = Date(post.closedTime)
                                        val timeFormat =
                                            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
                                        binding.activityPostInfoTvClosedTime.text =
                                            timeFormat.format(date).toString()
                                        binding.activityPostInfoTvContent.text = post!!.content

                                        binding.activityPostInfoTvStore.text = storeName

                                        binding.activityPostInfoBtnSelectMenu.setOnClickListener {

                                            val intent = Intent(
                                                this@PostInfoActivity,
                                                SelectMenuActivity::class.java
                                            ).apply {
                                                putExtra("storeId", post!!.storeId)
                                                putExtra("userType", "participant")
                                            }
                                            activityResult.launch(intent)

                                        }

                                        mPost = Post(
                                            postId.toInt(),
                                            post.title,
                                            post.date,
                                            user.id,
                                            post.storeId,
                                            post.place,
                                            post.closedTime,
                                            post.content,
                                            post.fund,
                                            post.minPrice,
                                            post.completed,
                                            post.type
                                        )
                                        loadCheck = true
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Toast.makeText(
                                            this@PostInfoActivity,
                                            error.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                })
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@PostInfoActivity, error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

                })

    }

        binding.activityPostInfoBtnDelete.setOnClickListener {

            val dialog = AlertDialog.Builder(this@PostInfoActivity)
                .setTitle("게시글 삭제")
                .setMessage("정말로 삭제하시겠습니까?")
                .setPositiveButton("삭제") { dialogInterface, i ->
                    mDatabase.child("Post").child(postId).removeValue()
                    mDatabase.child("User").child(ApplicationClass.sharedPreferencesUtil.getUser().id).child("postList").child(postId).removeValue()
                    val intent = Intent(this@PostInfoActivity, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("취소", null)
                .create()

            dialog.show()

        }


    }


    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {

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
            binding.activityPostInfoTvMenu.text = menuString

        }
    }
}