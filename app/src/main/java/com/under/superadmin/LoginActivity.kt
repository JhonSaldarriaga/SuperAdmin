package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.under.superadmin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val user = binding.userET.text.toString()
            val password = binding.passwordET.text.toString()
            if(user!="" && password!="") login(user,password)
            else Toast.makeText(this,R.string.login_error_empty_fields,Toast.LENGTH_SHORT).show()
        }

        binding.recoveryPasswordTV.setOnClickListener {
            startActivity(Intent(this,ChangePasswordActivity::class.java))
        }
    }

    private fun login(user:String, password:String){
        //--> Database login
    }
}