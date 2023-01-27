package com.dicoding.submission3

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission3.databinding.ActivityMainBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")
private lateinit var mainViewModel: MainViewModel


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private var listGithubUser = ArrayList<GithubUser>()
    private val loadingFunc = LoadingFunction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Submssion2)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val preferences = SettingPreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, SettingViewModel(preferences))
            .get(MainViewModel::class.java)

        mainViewModel.githubUserList.observe(this) {githubUserList ->
            setUserData(githubUserList)}

        mainViewModel.isLoading.observe(this) { loadingFunc.loadingState(it, binding!!.progressBar)}

        mainViewModel.totalCount.observe(this) { showText(it)}
        
        mainViewModel.state.observe(this) {state -> 
            state?.let { 
                Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT). show()
            }
        }

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding?.rvUserSearch?.layoutManager = layoutManager
        
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding?.searchBar?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchUser()
        
        mainViewModel.getTheme().observe(this) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun searchUser() {
        binding?.searchBar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { 
                    binding?.rvUserSearch?.visibility = View.VISIBLE
                    mainViewModel.searchGithubUser(it)
                    setUserData(listGithubUser)
                }
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_bar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_bar -> {
                val intent = Intent(this@MainActivity, ThemeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.fav_bar -> {
                val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.rvUserSearch?.windowToken, 0)
    }

    private fun showText(totalCount: Int) {
        binding?.apply {
            if (totalCount == 0) {
                tvSearchGuide.visibility = View.VISIBLE
                tvSearchGuide.text = resources.getString(R.string.search_not_found)
            } else {
                tvSearchGuide.visibility = View.INVISIBLE
            }
        }
    }

    private fun setUserData(listGithubUser: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        for (user in listGithubUser){
            listUser.clear()
            listUser.addAll(listGithubUser)
        }
        val adapter = SearchAdapter(listUser)
        binding?.rvUserSearch?.adapter = adapter

        adapter.setOnItemCallback(object : SearchAdapter.OnItemCallback{
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: GithubUser) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, data.login)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}