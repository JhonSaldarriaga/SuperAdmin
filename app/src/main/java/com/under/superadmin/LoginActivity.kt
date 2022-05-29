package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.under.superadmin.databinding.ActivityLoginBinding
import com.under.superadmin.dialog_fragment.ChangePasswordDialogFragment

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

    private fun login(user:String, password:String){
        //--> Database login
    }

    //ChangePasswordDialogFragment
    override fun onNextButton(email:String) {
        startActivity(Intent(this,ChangePasswordActivity::class.java).apply {
            putExtra("email",email)
        })
    }
}