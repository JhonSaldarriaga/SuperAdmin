package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.under.superadmin.databinding.ActivityChangePasswordBinding
import com.under.superadmin.model.User
import java.util.*

class ChangePasswordActivity : AppCompatActivity() {

    private val binding: ActivityChangePasswordBinding by lazy { ActivityChangePasswordBinding.inflate(layoutInflater) }

    private var currentEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //AQUI SE RECIBE EL EMAIL PASADO DESDE EL LOGIN CUANDO LE DAMOS SIGUIENTE AL DIALOG FRAGMENT
        currentEmail = (intent.extras?.getString("email")).toString()

        binding.newPasseordET.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.mayusPassConditionTV.isVisible = s.toString().lowercase() != s.toString()
                binding.minusPassConditionTV.isVisible = s.toString().uppercase() != s.toString()
                binding.minCharPassConditionTV.isVisible = s.toString().length >= 8
                s?.let {
                    var found = false
                    for (c in it){
                        if(c.isDigit()){
                            binding.numberPassConditionTV.isVisible = true
                            found = true
                            break
                        }
                    }
                    if(!found) binding.numberPassConditionTV.isVisible = false
                }
            }
        })

        binding.changePasswordButton.setOnClickListener {
            if(binding.mayusPassConditionTV.isVisible
                && binding.minusPassConditionTV.isVisible
                && binding.minCharPassConditionTV.isVisible
                && binding.numberPassConditionTV.isVisible){

                val newPassword = binding.newPasseordET.text.toString()
                val confirmPassword = binding.confirmPasswordET.text.toString()
                if(newPassword == confirmPassword){
                    changePassword(newPassword)
                }else Toast.makeText(this,R.string.change_password_error_not_equal,Toast.LENGTH_SHORT).show()

            }else Toast.makeText(this,R.string.change_password_error_condition,Toast.LENGTH_SHORT).show()
        }
    }

    private fun changePassword(newPass:String){
        //-->> do the change password process
        Firebase.auth.createUserWithEmailAndPassword(
            currentEmail, newPass
        ).addOnSuccessListener {
            Log.e(">>>","Se ha agregado correctamente el usuario al auth")
            Firebase.firestore.collection("users").document(currentEmail).update("claveAuto", false).addOnSuccessListener {
                Log.e(">>>", "El usuario tiene claveAuto en falso correctamente")
                Firebase.firestore.collection("users").document(currentEmail).get().addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    startActivity(Intent(this,MainActivity::class.java).apply {
                        putExtra("user", Gson().toJson(user))
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
        }
    }
}