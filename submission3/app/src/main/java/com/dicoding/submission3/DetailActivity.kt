package com.dicoding.submission3

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submission3.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding

    private lateinit var detailViewModel : DetailViewModel
    private var dataResponse = DataResponse()

    private val loadingFunction = LoadingFunction()

    private var stateButton: Boolean = false
    private var favoriteUser: FavoriteUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        detailViewModel = obtainViewModel(this@DetailActivity)

        // Live data observe
        detailViewModel.isLoading.observe(this) {
            loadingFunction.loadingState(it, binding!!.progressbarDetail)
        }
        detailViewModel.state.observe(this) { status ->
            status?.let {
                Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTabLayout()

        detailViewModel.listDetail.observe(this) { detailList ->
            dataResponse = detailList
            setApiData(dataResponse)
            favoriteUser = FavoriteUser(dataResponse.id, dataResponse.login)
            detailViewModel.getFavorites().observe(this) { favoriteList ->
                if (favoriteList != null) {
                    for (data in favoriteList) {
                        if (dataResponse.id == data.id) {
                            stateButton = true
                            binding?.favFloat?.setImageResource(R.drawable.ic_baseline_favorite_24)
                        }
                    }
                }
            }

            binding?.favFloat?.setOnClickListener {
                if (!stateButton) {
                    stateButton = true
                    binding?.favFloat?.setImageResource(R.drawable.ic_baseline_favorite_24)
                    insertToDatabase(dataResponse)
                } else {
                    stateButton = false
                    binding?.favFloat?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    detailViewModel.delete(dataResponse.id)
                    loadingFunction.activeToast(this, "Favorite user has been deleted.")
                }
            }
        }
    }

    private fun insertToDatabase(detailList: DataResponse) {
        favoriteUser.let { favoriteUser ->
            favoriteUser?.id = detailList.id
            favoriteUser?.login = detailList.login
            favoriteUser?.avatarUrl = detailList.avatarUrl
            detailViewModel.insert(favoriteUser as FavoriteUser)
            loadingFunction.activeToast(this, "Added to Favorites User")
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }


    private fun setApiData(dataResponseList: DataResponse) {
        binding?.apply {
            imgAvatarDetail.loadImage(dataResponse.avatarUrl)
            tvNameDetail.text = dataResponseList.name ?: getString(R.string.no_name)
            tvFollowersValue.text = resources.getString(R.string.followers_value, dataResponseList.followers)
            tvFollowingValue.text = resources.getString(R.string.following_value, dataResponseList.following)
            tvRepoValue.text = resources.getString(R.string.repo_value, dataResponseList.publicRepos)
            tvUserCompany.text = dataResponseList.company ?: "-"
            tvUserLocation.text = dataResponseList.location ?: "-"
            tvUserEmail.text = if (dataResponseList.email == "") "-" else dataResponseList.email
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }

    private fun setTabLayout() {
        val userIntent = intent.extras
        if (userIntent != null) {
            val userLogin = userIntent.getString(EXTRA_USER)
            if (userLogin != null) {
                detailViewModel.getDetailUser(userLogin)
                val login = Bundle()
                login.putString(EXTRA_FRAGMENT, userLogin)
                val pagerAdapter = PagerAdapter(this, login)
                val viewPager: ViewPager2 = binding!!.viewPager

                viewPager.adapter = pagerAdapter
                val tabs: TabLayout = binding!!.tabLayout
                val tabTitle = resources.getStringArray(R.array.tab_header)
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = tabTitle[position]
                }.attach()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAGMENT = "extra_fragment"
    }
}
