package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.under.superadmin.databinding.ActivityMainBinding
import com.under.superadmin.fragments.EditPersonalInfoFragment
import com.under.superadmin.fragments.HomeFragment

class MainActivity : AppCompatActivity(), HomeFragment.Listener, EditPersonalInfoFragment.Listener {


    private lateinit var homeFragment: HomeFragment
    private lateinit var editPersonalInfoFragment: EditPersonalInfoFragment

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.homeMenu
        homeFragment = HomeFragment.newInstance()
        homeFragment.listener = this
        editPersonalInfoFragment = EditPersonalInfoFragment.newInstance()
        editPersonalInfoFragment.listener = this

        showFragment(homeFragment) // Default fragment
        //startActivity(Intent(this,LoginActivity::class.java)) // PASS to LOGIN
    }

    private fun showFragment (fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id,fragment)
        transaction.commit()
    }

    //HOME_FRAGMENT LISTENER
    override fun onUserNameClickListener() {
        showFragment(editPersonalInfoFragment)
    }

    //EDIT_PERSONAL_INFO LISTENER
    override fun onSaveUserInfo() {
        //SAVE NEW INFO IN DATABASE
    }
    override fun onBackHome() {
        showFragment(homeFragment)
    }
}