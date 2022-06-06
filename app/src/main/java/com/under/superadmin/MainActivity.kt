package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.under.superadmin.databinding.ActivityMainBinding
import com.under.superadmin.dialog_fragment.UserConfirmationDialogFragment
import com.under.superadmin.fragments.*
import com.under.superadmin.fragments.search_user_recycler_model.ResultViewHolder
import com.under.superadmin.fragments.unlock_account_recycler_model.UnlockResultViewHolder
import com.under.superadmin.model.Client
import com.under.superadmin.model.User
import java.util.*
import kotlin.collections.ArrayList

// ACTIVIDAD QUE SOPORTA EL BOTTOM NAVIGATION BAR
class MainActivity : AppCompatActivity(),
    HomeFragment.Listener,
    CreateOrEditUserFragment.Listener,
    UserConfirmationDialogFragment.Listener,
    AdminFragment.Listener,
    SearchUserFragment.Listener,
    SearchResultFragment.Listener,
    ResultViewHolder.Listener,
    UnlockAccountFragment.Listener,
    UpdateClientFragment.Listener,
    UpdateClientFormFragment.Listener,
    UpdateClientFormFragment2.Listener,
    UnlockAccountResultFragment.Listener,
    UnlockResultViewHolder.Listener,
    TicketFragment.Listener{

    private lateinit var homeFragment: HomeFragment
    private lateinit var createOrEditUserFragment: CreateOrEditUserFragment
    private lateinit var adminFragment: AdminFragment
    private lateinit var searchUserFragment: SearchUserFragment
    private lateinit var resultSearchUserFragment: SearchResultFragment
    private lateinit var unlockAccountFragment: UnlockAccountFragment
    private lateinit var unlockAccountResultFragment: UnlockAccountResultFragment
    private lateinit var  updateClientFragment: UpdateClientFragment
    private lateinit var  updateClientFormFragment: UpdateClientFormFragment
    private lateinit var  updateClientForm2Fragment: UpdateClientFormFragment2
    private lateinit var ticketFragment: TicketFragment
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
        searchUserFragment = SearchUserFragment.newInstance()
        resultSearchUserFragment = SearchResultFragment.newInstance()
        unlockAccountFragment = UnlockAccountFragment.newInstance()
        unlockAccountResultFragment = UnlockAccountResultFragment.newInstance()
        updateClientFragment = UpdateClientFragment.newInstance()
        updateClientFormFragment = UpdateClientFormFragment.newInstance()
        updateClientForm2Fragment = UpdateClientFormFragment2.newInstance()
        ticketFragment = TicketFragment.newInstance()

        // SE PASA EL LISTENER PARA EL PATRON OBSERVER DE CADA FRAGMENT
        homeFragment.listener = this
        createOrEditUserFragment.listener = this
        adminFragment.listener = this
        userConfirmationDialogFragment.listener = this
        searchUserFragment.listener = this
        resultSearchUserFragment.listenerViewHolder = this
        resultSearchUserFragment.listener = this
        unlockAccountFragment.listener = this
        unlockAccountResultFragment.listenerViewHolder = this
        unlockAccountResultFragment.listener = this
        updateClientFragment.listener =this
        updateClientFormFragment.listener =this
        updateClientForm2Fragment.listener =this
        ticketFragment.listener = this

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

    private fun loadUser():User?{
        val sp = getSharedPreferences("superadmin", MODE_PRIVATE)
        val json = sp.getString("user", "NO_USER")
        if(json == "NO_USER"){
            return null
        }else{
            return Gson().fromJson(json, User::class.java)
        }
    }

    private fun saveUser(user:User){
        val sp = getSharedPreferences("superadmin", MODE_PRIVATE)
        val json = Gson().toJson(user)
        sp.edit().putString("user",json).apply()
        this.user = user
    }

    // Funcion que muestra en el fragmentContainer el fragment pasado por el parametro
    private fun showFragment (fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id,fragment)
        transaction.commit()
    }

    // <<HOME_FRAGMENT LISTENER>>


    override fun onInitHomeFragment(): User {
        return user!!
    }

    // Esto nos lleva a editar la informacion del usuario que se encuentra logeado
    // falta implementar el modelo del usuario, para poder pasarselo al fragment
    // y asi pueda cargar toda la informacion como un hint, y pueda editarla
    override fun onUserNameClickListener() {
        createOrEditUserFragment.mode = getString(R.string.edit_personal_info_fragment_title)
        createOrEditUserFragment.currentUser = user
        showFragment(createOrEditUserFragment)
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
        startActivity(Intent(this,ChangePasswordActivity::class.java).apply {
            putExtra("mode", ChangePasswordActivity.changePassword)
            putExtra("email", user!!.email)
        }) // PASS to LOGIN
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
            saveUser(passUser)
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
            userConfirmationDialogFragment.mode = getString(R.string.confirm_create_user_dialog_title)
            userConfirmationDialogFragment.setIdText(user.numeroidentificacion)
            userConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
        }
    }

    override fun onBackUserAdmin() {
        showFragment(adminFragment)
    }

    //<EDIT USER>
    /* Guardar la informacion editada del usuario seleccionado
       Debería de recibir un objeto tipo USER, con la nueva información. */
    override fun onEditUser(user: User) {
        //SAVE NEW INFO IN DATABASE
        if(user.email == this.user!!.email){
            saveUser(user)
        }
        Firebase.firestore.collection("users").document(user.email).set(user).addOnSuccessListener {
            userConfirmationDialogFragment.mode = getString(R.string.edit_user_title)
            userConfirmationDialogFragment.setIdText(user.numeroidentificacion)
            userConfirmationDialogFragment.show(supportFragmentManager,"Confirm update user info")
        }
    }

    override fun onBackEditUser() {
        showFragment(searchUserFragment)
    }

    // <<USER_CONFIRMATION_DIALOG LISTENER>>
    // El cuadro de dialogo de confirmación, ya está bien.
    override fun onAcceptButton(mode: String) {
        val createUser : String = getString(R.string.confirm_create_user_dialog_title)
        val editPersonalInfo : String = getString(R.string.confirm_edit_personal_info_dialog_title)
        val editUser : String = getString(R.string.edit_user_title)
        when(mode){
            createUser -> showFragment(adminFragment)
            editPersonalInfo -> showFragment(homeFragment)
            editUser -> showFragment(searchUserFragment)
        }
    }

    // <<ADMIN_FRAGMENT LISTENER>>

    override fun verifyLogisticType(): Boolean {
        return user!!.rol == "LOGISTICA"
    }

    // <ADMIN_USER>
    // Nos envía a crear usuario
    override fun onCreateUserClickListener() {
        createOrEditUserFragment.mode = getString(R.string.admin_user_section_create_user_title)
        showFragment(createOrEditUserFragment)
    }

    override fun onEditUserClickListener() {
        showFragment(searchUserFragment)
    }

    // SEARCH USER FRAGMENT
    override fun onSearch(colab: String, searchBy: String, option: String) {

        Log.e(">>>", "colab:$colab searchBy:$searchBy option:$option")

        Firebase.firestore.collection("users").whereEqualTo("colaborador", colab).get().addOnCompleteListener{ task ->
                if(task.result?.size() != 0){
                    var usersFound = ArrayList<User>()
                    for(document in task.result!!) {
                        val userFound = document.toObject(User::class.java)
                        if(searchBy == "Tipo de identificación") {
                            if(userFound.tipoIdetificacion == option) {
                                Log.e(">>>","usertypeIdentificatonFound: ${userFound.tipoIdetificacion}")
                                usersFound.add(userFound)
                            }
                        }else if(searchBy == "Rol") {
                            Log.e(">>>","userRolFound: ${userFound.rol}")
                            if(userFound.rol == option) usersFound.add(userFound)
                        }
                    }
                    if(usersFound.size>0){
                        resultSearchUserFragment.userList = usersFound
                        showFragment(resultSearchUserFragment)
                    }else Toast.makeText(this, R.string.users_not_found, Toast.LENGTH_SHORT).show()
                }else Toast.makeText(this, R.string.users_not_found, Toast.LENGTH_SHORT).show()
            }
    }

    // RESULT SEARCH USER
    override fun onBackSearchResult() {
        showFragment(searchUserFragment)
    }

    // RESULT VIEW HOLDER FRAGMENT
    override fun onGoToEditUser(user: User) {
        createOrEditUserFragment.mode = getString(R.string.edit_user_title)
        createOrEditUserFragment.currentUser = user
        showFragment(createOrEditUserFragment)
    }

    // <TRANSACTIONAL_MODULE>
    override fun onUpdateClientClickListener() {
        showFragment(updateClientFragment)
    }

    override fun onCloseCompanyClickListener() {
        TODO("Not yet implemented")
    }

    override fun onConnectionsClickListener() {
        TODO("Not yet implemented")
    }



    override fun onUnlockAccountClickListener() {
        showFragment(unlockAccountFragment)
    }

    override fun onSearchLockedAccount (account: String, identification: String) {
        Firebase.firestore.collection("clients").whereEqualTo("numeroCelular", account).get().addOnCompleteListener{ task ->
            if(task.result?.size() != 0){
                var clientsFound = ArrayList<Client>()
                for(document in task.result!!) {
                    val clientFound = document.toObject(Client::class.java)
                    if(identification == clientFound.numeroIdentificacion) {
                        clientsFound.add(clientFound)
                    }
                }

                if(clientsFound.size > 0){
                    unlockAccountResultFragment.clientList = clientsFound
                    showFragment(unlockAccountResultFragment)

                }else Toast.makeText(this, R.string.clients_not_found, Toast.LENGTH_SHORT).show()

            }else Toast.makeText(this, R.string.clients_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackUnlockAccountSearchResult() {
        showFragment(unlockAccountFragment)
    }

    override fun onGoToUnlockClientConfirmation(mode: String, client: Client, changesList: ArrayList<String>) {
        ticketFragment.mode = mode
        ticketFragment.listChanges = changesList
        ticketFragment.currentUser = user
        ticketFragment.unlockAccountClient = client
        showFragment(ticketFragment)
    }

    override fun onGoToUdapteClientConfirmation(mode: String, client: Client) {
        ticketFragment.mode = mode
        ticketFragment.currentUser = user
        ticketFragment.unlockAccountClient = client
        showFragment(ticketFragment)
    }

    override fun onSearchClientUpdateAccount (account: String, identification: String) {
        Firebase.firestore.collection("clients").whereEqualTo("numeroIdentificacion", identification).get().addOnCompleteListener{ task ->
            if(task.result?.size() != 0){
                var clientFound : Client? = null
                for(document in task.result!!) {
                    val currentFound = document.toObject(Client::class.java)
                    if(identification == currentFound.numeroIdentificacion) {
                        clientFound = currentFound
                    }
                }

                if(clientFound !== null ){
                    updateClientFormFragment.client = clientFound
                    showFragment(updateClientFormFragment)

                }else Toast.makeText(this, R.string.clients_not_found, Toast.LENGTH_SHORT).show()

            }else Toast.makeText(this, R.string.clients_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGoToSecondForm(clientUpdate: Client){
        updateClientForm2Fragment.client2 = clientUpdate
        showFragment(updateClientForm2Fragment)
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


    //Metodos para implementar una vez se haya aceptado el ticket exitosamente
    override fun onUnlockAccount(client: Client) {
        Firebase.firestore.collection("clients").document(client.numeroCelular).set(client)

        showFragment(unlockAccountFragment)
    }

    override fun onUpdateClient(client: Client) {
        Log.e(">>>", client.toString())
        Firebase.firestore.collection("clients").document(client.numeroCelular).set(client)
    }

    override fun onReactiveTransaction() {
        TODO("Not yet implemented")
    }

    //Metodos para devolverse cuando se esta en la pagina de ticket
    override fun onBackToUnlockAccount() {
        showFragment(unlockAccountFragment)
    }

    override fun onBackToUpdateClient() {
        TODO("Not yet implemented")
    }

    override fun onBackToReactiveTransaction() {
        TODO("Not yet implemented")
    }

}