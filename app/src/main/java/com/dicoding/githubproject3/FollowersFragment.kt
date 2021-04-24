package com.dicoding.githubproject3

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubproject3.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    companion object {
        private val ARG_USERNAME = "username"

        @JvmStatic
        fun newInstance(username: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapter: UserAdapter
    private lateinit var detailUserViewModel: DetailUserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowers.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getString(ARG_USERNAME)

        detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)
        if (user != null) {
            detailUserViewModel.setFollowers(user)
        }

        detailUserViewModel.getFollowers().observe(viewLifecycleOwner) {
            adapter.setData(it)

        }
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(activity, UserDetaill::class.java)
                intent.putExtra(UserDetaill.EXTRA_USER, data)
                startActivity(intent)
            }
        })
    }
}
