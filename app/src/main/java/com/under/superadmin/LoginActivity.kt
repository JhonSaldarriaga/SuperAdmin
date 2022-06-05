package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.under.superadmin.databinding.ActivityLoginBinding
import com.under.superadmin.dialog_fragment.ChangePasswordDialogFragment
import com.under.superadmin.model.User

class LoginActivity : AppCompatActivity(), ChangePasswordDialogFragment.Listener  {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private var dialogChangePassword = ChangePasswordDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dialogChangePassword.listener = this

        binding.loginButton.setOnClickListener {
            val user = binding.userET.text.toString()
            val password = binding.passwordET.text.toString()
            if(user!="" && password!="") login(user,password)
            else Toast.makeText(this,R.string.login_error_empty_fields,Toast.LENGTH_SHORT).show()
        }

        binding.recoveryPasswordTV.setOnClickListener {
            dialogChangePassword.show(supportFragmentManager,"Type email")
        }
    }

    // AQUI DEBERÍAMOS IMPLEMENTE EL AUTH DE FIREBASE
    private fun login(userEmail:String, password:String){
        //--> Database login
        Firebase.auth.signInWithEmailAndPassword(userEmail,password).addOnSuccessListener {
            val fbuser = Firebase.auth.currentUser
            Firebase.firestore.collection("users").document(userEmail).get().addOnSuccessListener {
                val user = it.toObject(User::class.java)
                saveUser(user!!)
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }.addOnFailureListener { exception ->
            Firebase.firestore.collection("users").document(userEmail).get().addOnSuccessListener {
                val user = it.toObject(User::class.java)
                if(user!=null){
                    if(user.claveAuto){
                        startActivity(Intent(this,ChangePasswordActivity::class.java).apply {
                            putExtra("email", user.email)
                        })
                    }
                }else Toast.makeText(this,exception.message,Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { Toast.makeText(this,exception.message,Toast.LENGTH_SHORT).show() }
        }
    }

    //ChangePasswordDialogFragment
    // PatronObserver con el ChangePasswordDialogFragment, cuando se le de al boton siguiente
    // pasamos el email por el intent a la actividad CHANGE PASSWORD
    override fun onNextButton(email:String) {
        /*
        startActivity(Intent(this,ChangePasswordActivity::class.java).apply {
            putExtra("email",email)
        })
         */
        Toast.makeText(this, "Se implementará luego", Toast.LENGTH_SHORT).show()
    }

    private fun saveUser(user:User){
        val sp = getSharedPreferences("superadmin", MODE_PRIVATE)
        val json = Gson().toJson(user)
        sp.edit().putString("user",json).apply()
    }
}