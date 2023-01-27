package com.dicoding.submission3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission3.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followerViewModel: FollowerViewModel
    private val loadingFunction = LoadingFunction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel.isLoading.observe(viewLifecycleOwner, { loadingFunction.loadingState(it, binding.progressbarFollowers) })

        followerViewModel.listFollower.observe(viewLifecycleOwner, { listFollower -> setApiData(listFollower) })

        followerViewModel.getFollowersUser(arguments?.getString(DetailActivity.EXTRA_FRAGMENT).toString())
    }

    private fun setApiData(listFollowers: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        with(binding) {
            for (user in listFollowers){
                listUser.clear()
                listUser.addAll(listFollowers)
            }
            rvUserFollowers.layoutManager = LinearLayoutManager(context)
            val adapter = FollowersAdapter(listFollowers)
            rvUserFollowers.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}