package com.dicoding.githubproject3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubproject3.databinding.ActivityUserDetaillBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetaill : AppCompatActivity() {

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.text_ffollowers,
                R.string.text_ffollowing
        )
        const val EXTRA_USER =  "extra_user"
    }

    private lateinit var binding: ActivityUserDetaillBinding
    private lateinit var detailViewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetaillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = DetailUserViewModel()
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        detailViewModel.getDetailUser().observe(this, {
            binding.apply {
                Glide.with(this@UserDetaill)
                        .load(it.avatar)
                        .into(imgItemPhoto)
                tvRepodetail.text = it.repository
                tvFollowersdetail.text = it.followers
                tvFollowingdetail.text = it.following
                tvName.text = it.name
                tvUserName.text = it.username
                tvLocation.text = it.location
                tvCompany.text = it.company
            }
            showLoading(false)
        })
        initTabLayout(userName = String())

        user.name?.let {
            initTabLayout(it)
            if (it != null) {
            detailViewModel.setDetailUser(it)
        }}
}
    private fun initTabLayout(userName: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userName

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(
                    TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
}
}