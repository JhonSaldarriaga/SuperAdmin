package com.under.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import com.under.superadmin.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private val binding: ActivityChangePasswordBinding by lazy { ActivityChangePasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val email = (intent.extras?.getString("email")).toString()

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
        startActivity(Intent(this,LoginActivity::class.java))
    }
}