package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.under.superadmin.databinding.ActivityMainBinding
import com.under.superadmin.dialog_fragment.UserConfirmationDialogFragment
import com.under.superadmin.fragments.AdminFragment
import com.under.superadmin.fragments.CreateOrEditUserFragment
import com.under.superadmin.fragments.HomeFragment
import com.under.superadmin.model.User
import java.util.*

// ACTIVIDAD QUE SOPORTA EL BOTTOM NAVIGATION BAR
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
    private var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Primero antes de todo deberia de cargarse el usuario logeado en el currentUser
        user = loadUser()
        if(user==null || Firebase.auth.currentUser == null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }else Log.e(">>>","Se ha cargado el mainactivity correctamente")

        // SE GUARDAN LAS INSTANCIAS DE LOS FRAGMENTS
        binding.bottomNavigationView.selectedItemId = R.id.homeMenu
        homeFragment = HomeFragment.newInstance()
        createOrEditUserFragment = CreateOrEditUserFragment.newInstance()
        adminFragment = AdminFragment.newInstance()

        // SE PASA EL LISTENER PARA EL PATRON OBSERVER DE CADA FRAGMENT
        homeFragment.listener = this
        createOrEditUserFragment.listener = this
        adminFragment.listener = this
        userConfirmationDialogFragment.listener = this

        /*
         Cada que se quisiera hacer showFragment(homeFragment) debería de hacerse:
         homeFragment.currentUser = currentUser()
         */

        showFragment(homeFragment) // Default fragment

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.homeMenu -> {showFragment(homeFragment)}
                R.id.adminMenu -> {showFragment(adminFragment)}
                //R.id.reportMenu -> {showFragment()} HACE FALTA IMPLEMENTAR EL FRAGMENT PARA LOS REPORTES
            }
            true
        }

        binding.bottomNavigationView.setOnItemReselectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.homeMenu -> {showFragment(homeFragment)}
                R.id.adminMenu -> {showFragment(adminFragment)}
                //R.id.reportMenu -> {showFragment()} HACE FALTA IMPLEMENTAR EL FRAGMENT PARA LOS REPORTES
            }
            true
        }
    }

    fun loadUser():User?{
        val sp = getSharedPreferences("superadmin", MODE_PRIVATE)
        val json = sp.getString("user", "NO_USER")
        if(json == "NO_USER"){
            return null
        }else{
            return Gson().fromJson(json, User::class.java)
        }
    }

    // Funcion que muestra en el fragmentContainer el fragment pasado por el parametro
    private fun showFragment (fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id,fragment)
        transaction.commit()
    }

    // <<HOME_FRAGMENT LISTENER>>

    // Esto nos lleva a editar la informacion del usuario que se encuentra logeado
    // falta implementar el modelo del usuario, para poder pasarselo al fragment
    // y asi pueda cargar toda la informacion como un hint, y pueda editarla
    override fun onUserNameClickListener() {
        showFragment(createOrEditUserFragment.apply {
            mode = getString(R.string.edit_personal_info_fragment_title)
            currentUser = user
        })
    }

    // Cerrar sesión
    override fun onLogoutClickListener() {
        //LOGOUT IN DATABASE AUTH
        startActivity(Intent(this,LoginActivity::class.java)) // PASS to LOGIN
        getSharedPreferences("superadmin", MODE_PRIVATE).edit().clear().apply()
        Firebase.auth.signOut()
        finish()
    }

    // Cambiar contraseña
    override fun onChangePasswordListener() {
        startActivity(Intent(this,ChangePasswordActivity::class.java)) // PASS to LOGIN
    }

    //<<EDIT_OR_CREATE_USER LISTENER>>
    //<EDIT PERSONAL INFO>
    /* Guardar la informacion editada del usuario logeado
       Debería de recibir un objeto tipo USER, con la nueva información. */
    override fun onSaveUserInfo(passUser: User) {
        //SAVE NEW INFO IN DATABASE
        Firebase.firestore.collection("users").document(passUser.email).set(passUser).addOnSuccessListener {
            userConfirmationDialogFragment.mode = getString(R.string.confirm_edit_personal_info_dialog_title)
            userConfirmationDialogFragment.setIdText(passUser.numeroidentificacion)
            userConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
        }
    }
    // Vuelve al fragmentHome
    override fun onBackHome() {
        showFragment(homeFragment)
    }

    //<CREATE NEW USER>
    /* Crea un nuevo usuario en la base de datos
       Debería de recibir un objeto tipo USER, con el nuevo usuario que se quiere agregar */
    override fun onCreateNewUser(user: User) {
        //SAVE NEW USER IN DATABASE
        Firebase.firestore.collection("users").document(user.email).set(user).addOnSuccessListener {
            Log.e(">>>", "Se ha agregado correctamente el usuario al firestore")
        }

        userConfirmationDialogFragment.mode = getString(R.string.confirm_create_user_dialog_title)
        userConfirmationDialogFragment.setIdText(user.numeroidentificacion)
        userConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
    }

    override fun onBackUserAdmin() {
        showFragment(adminFragment)
    }

    //<EDIT USER>
    /* Guardar la informacion editada del usuario seleccionado
       Debería de recibir un objeto tipo USER, con la nueva información. */
    override fun onEditUser(user: User) {
        //SAVE NEW INFO IN DATABASE
        userConfirmationDialogFragment.mode = getString(R.string.confirm_edit_personal_info_dialog_title)
        userConfirmationDialogFragment.setIdText(user.numeroidentificacion)
        userConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
    }

    override fun onBackEditUser() {
        TODO("Not yet implemented")
    }

    // <<USER_CONFIRMATION_DIALOG LISTENER>>
    // El cuadro de dialogo de confirmación, ya está bien.
    override fun onAcceptButton(mode: String) {
        val createUser : String = getString(R.string.confirm_create_user_dialog_title)
        val editPersonalInfo : String = getString(R.string.confirm_edit_personal_info_dialog_title)
        when(mode){
            createUser -> showFragment(adminFragment)
            editPersonalInfo -> showFragment(homeFragment)
        }
    }

    // <<ADMIN_FRAGMENT LISTENER>>
    // <ADMIN_USER>
    // Nos envía a crear usuario
    override fun onCreateUserClickListener() {
        showFragment(createOrEditUserFragment.apply { mode = getString(R.string.admin_user_section_create_user_title) })
    }

    override fun onEditUserClickListener() {
        showFragment(createOrEditUserFragment.apply {
            mode = getString(R.string.edit_user_title)
            currentUser = user
        })
    }

    // <TRANSACTIONAL_MODULE>
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