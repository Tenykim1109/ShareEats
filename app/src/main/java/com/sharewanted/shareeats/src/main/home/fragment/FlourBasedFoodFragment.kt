package com.sharewanted.shareeats.src.main.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sharewanted.shareeats.databinding.FragmentFlourBasedFoodBinding
import com.sharewanted.shareeats.src.main.home.HomeListAdapter
import com.sharewanted.shareeats.src.main.home.order.orderDto.Post
import com.sharewanted.shareeats.src.main.home.postInfo.PostInfoActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FlourBasedFoodFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: HomeListAdapter
    private var postList = mutableListOf<Post>()
    private val mDatabase = Firebase.database.reference

    private lateinit var binding : FragmentFlourBasedFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFlourBasedFoodBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        mDatabase.child("Post").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                for (dataSnapshot in snapshot.children) {
                    val type = dataSnapshot.child("type").getValue(String::class.java)
                    val completed = dataSnapshot.child("completed").getValue(String::class.java).toString()

                    if (type == "분식") {
                        if (completed == "모집중") {
                            val post = dataSnapshot.getValue(Post::class.java)
                            postList.add(post!!)
                        }
                    }
                }

                adapter = HomeListAdapter(postList)
                binding.fragmentJflourBasedFoodLv.adapter = adapter
                binding.fragmentJflourBasedFoodLv.setOnItemClickListener { adapterView, view, i, l ->
                    val intent = Intent(context, PostInfoActivity::class.java).apply {
                        putExtra("postId", postList[i].postId)
                    }
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Post error", error.toString())
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlourBasedFoodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}