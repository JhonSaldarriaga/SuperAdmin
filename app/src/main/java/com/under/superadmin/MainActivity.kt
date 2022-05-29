package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.under.superadmin.databinding.ActivityMainBinding
import com.under.superadmin.dialog_fragment.UpdateUserConfirmationDialogFragment
import com.under.superadmin.fragments.EditPersonalInfoFragment
import com.under.superadmin.fragments.HomeFragment

class MainActivity : AppCompatActivity(),
    HomeFragment.Listener,
    EditPersonalInfoFragment.Listener,
    UpdateUserConfirmationDialogFragment.Listener {


    private lateinit var homeFragment: HomeFragment
    private lateinit var editPersonalInfoFragment: EditPersonalInfoFragment
    private var updateUserConfirmationDialogFragment = UpdateUserConfirmationDialogFragment()

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.homeMenu
        homeFragment = HomeFragment.newInstance()
        editPersonalInfoFragment = EditPersonalInfoFragment.newInstance()

        homeFragment.listener = this
        editPersonalInfoFragment.listener = this
        updateUserConfirmationDialogFragment.listener = this

        showFragment(homeFragment) // Default fragment

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.homeMenu -> {showFragment(homeFragment)}
                //R.id.reportMenu -> {showFragment()}
                //R.id.adminMenu -> {showFragment()}
            }
            true
        }

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
    override fun onLogoutClickListener() {
        //LOGOUT IN DATABASE
        startActivity(Intent(this,LoginActivity::class.java)) // PASS to LOGIN
        finish()
    }

    //EDIT_PERSONAL_INFO LISTENER
    override fun onSaveUserInfo() {
        //SAVE NEW INFO IN DATABASE
        val id = "1193476214" // LOAD CURRENT USER ID
        updateUserConfirmationDialogFragment.setIdText(id)
        updateUserConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
    }
    override fun onBackHome() {
        showFragment(homeFragment)
    }

    // UPDATE_USER_CONFIRMATION_DIALOG LISTENER
    override fun onAcceptButton() {
        showFragment(homeFragment)
    }
}