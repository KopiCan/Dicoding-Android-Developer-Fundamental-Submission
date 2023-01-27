package com.dicoding.submission3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission3.databinding.FragmentFollowersBinding

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private val loadingFunction = LoadingFunction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel.isLoading.observe(viewLifecycleOwner) { loadingFunction.loadingState(it, binding.progressbarFollowers) }

        followingViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing -> setApiData(listFollowing) }

        followingViewModel.getFollowingUser(arguments?.getString(DetailActivity.EXTRA_FRAGMENT).toString())
    }

    private fun setApiData(listFollowing: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        with(binding) {
            for (user in listFollowing){
                listUser.clear()
                listUser.addAll(listFollowing)
            }
            rvUserFollowers.layoutManager = LinearLayoutManager(context)
            val adapter = FollowersAdapter(listFollowing)
            rvUserFollowers.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}