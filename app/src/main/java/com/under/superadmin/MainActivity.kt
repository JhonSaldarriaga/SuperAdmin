package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.under.superadmin.databinding.ActivityMainBinding
import com.under.superadmin.dialog_fragment.UserConfirmationDialogFragment
import com.under.superadmin.fragments.AdminFragment
import com.under.superadmin.fragments.CreateOrEditUserFragment
import com.under.superadmin.fragments.HomeFragment

class MainActivity : AppCompatActivity(),
    HomeFragment.Listener,
    CreateOrEditUserFragment.Listener,
    UserConfirmationDialogFragment.Listener,
    AdminFragment.Listener {


    private lateinit var homeFragment: HomeFragment
    private lateinit var createOrEditUserFragment: CreateOrEditUserFragment
    private lateinit var adminFragment: AdminFragment
    private var userConfirmationDialogFragment = UserConfirmationDialogFragment()

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.homeMenu
        homeFragment = HomeFragment.newInstance()
        createOrEditUserFragment = CreateOrEditUserFragment.newInstance()
        adminFragment = AdminFragment.newInstance()

        homeFragment.listener = this
        createOrEditUserFragment.listener = this
        adminFragment.listener = this
        userConfirmationDialogFragment.listener = this

        showFragment(homeFragment) // Default fragment

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.homeMenu -> {showFragment(homeFragment)}
                R.id.adminMenu -> {showFragment(adminFragment)}
                //R.id.reportMenu -> {showFragment()}
            }
            true
        }

        binding.bottomNavigationView.setOnItemReselectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.homeMenu -> {showFragment(homeFragment)}
                R.id.adminMenu -> {showFragment(adminFragment)}
                //R.id.reportMenu -> {showFragment()}
            }
            true
        }
    }

    private fun showFragment (fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id,fragment)
        transaction.commit()
    }

    // HOME_FRAGMENT LISTENER
    override fun onUserNameClickListener() {
        createOrEditUserFragment.mode = getString(R.string.edit_personal_info_fragment_title)
        showFragment(createOrEditUserFragment)
    }

    override fun onLogoutClickListener() {
        //LOGOUT IN DATABASE
        startActivity(Intent(this,LoginActivity::class.java)) // PASS to LOGIN
        finish()
    }

    // EDIT_OR_CREATE_USER LISTENER
    // EDIT PERSONAL INFO
    override fun onSaveUserInfo() {
        //SAVE NEW INFO IN DATABASE
        val id = "1193476214" // LOAD CURRENT USER ID
        userConfirmationDialogFragment.mode = getString(R.string.confirm_edit_personal_info_dialog_title)
        userConfirmationDialogFragment.setIdText(id)
        userConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
    }
    override fun onBackHome() {
        showFragment(homeFragment)
    }

    //CREATE NEW USER
    override fun onCreateNewUser() {
        //SAVE NEW USER IN DATABASE
        val id = "1193476214" // LOAD NEW USER ID
        userConfirmationDialogFragment.mode = getString(R.string.confirm_create_user_dialog_title)
        userConfirmationDialogFragment.setIdText(id)
        userConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
    }

    override fun onBackUserAdmin() {
        showFragment(adminFragment)
    }

    //EDIT USER
    override fun onEditUser() {
        //SAVE NEW INFO IN DATABASE
        val id = "1193476214" // LOAD CURRENT USER ID
        userConfirmationDialogFragment.mode = getString(R.string.confirm_edit_personal_info_dialog_title)
        userConfirmationDialogFragment.setIdText(id)
        userConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
    }

    override fun onBackEditUser() {
        TODO("Not yet implemented")
    }

    // USER_CONFIRMATION_DIALOG LISTENER
    override fun onAcceptButton(mode: String) {
        val createUser : String = getString(R.string.confirm_create_user_dialog_title)
        val editPersonalInfo : String = getString(R.string.confirm_edit_personal_info_dialog_title)
        when(mode){
            createUser -> showFragment(adminFragment)
            editPersonalInfo -> showFragment(homeFragment)
        }
    }

    // ADMIN_FRAGMENT LISTENER
    // ADMIN_USER
    override fun onCreateUserClickListener() {
        createOrEditUserFragment.mode = getString(R.string.admin_user_section_create_user_title)
        showFragment(createOrEditUserFragment)
    }

    override fun onEditUserClickListener() {
        TODO("Not yet implemented")
    }
    // TRANSACTIONAL_MODULE
    override fun onUpdateClientClickListener() {
        TODO("Not yet implemented")
    }

    override fun onCloseCompanyClickListener() {
        TODO("Not yet implemented")
    }

    override fun onConnectionsClickListener() {
        TODO("Not yet implemented")
    }

    override fun onUnlockAccountClickListener() {
        TODO("Not yet implemented")
    }

    override fun onValidateTransactionClickListener() {
        TODO("Not yet implemented")
    }

    override fun onHomologationsClickListener() {
        TODO("Not yet implemented")
    }

    override fun onProductClickListener() {
        TODO("Not yet implemented")
    }

    override fun onSubProductClickListener() {
        TODO("Not yet implemented")
    }

    override fun onForwardingOfReceiptClickListener() {
        TODO("Not yet implemented")
    }

    override fun onColabClickListener() {
        TODO("Not yet implemented")
    }
}