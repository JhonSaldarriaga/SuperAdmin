package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentUnlockAccountBinding

class UnlockAccountFragment : Fragment() {

    private var _binding : FragmentUnlockAccountBinding? = null
    private val binding get() = _binding!!

    var listener : Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUnlockAccountBinding.inflate(inflater, container, false)

        binding.unlockUserBackButton.setOnClickListener { listener!!.onBackUserAdmin() }
        binding.unlockAccountSearchButton.setOnClickListener { searchClient() }

        return binding.root
    }

    private fun searchClient() {
        if(!verifyEmptyFields()) {
            listener!!.onSearchLockedAccount(
                binding.unlockAccountAccountField.text.toString(),
                binding.unlockAccountIdentificationField.text.toString())

            clearFields()

        }else {
            Toast.makeText(context, R.string.clients_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyEmptyFields(): Boolean {
        var empty = false

        if(binding.unlockAccountAccountField.text.toString().compareTo("") == 0) {
            empty = true
        }

        if(binding.unlockAccountIdentificationField.text.toString().compareTo("") == 0) {
            empty = true
        }

        return empty
    }

    fun clearFields() {
        binding.unlockAccountAccountField.setText("")
        binding.unlockAccountIdentificationField.setText("")
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    interface Listener{
        fun onBackUserAdmin()
        fun onSearchLockedAccount(account: String, identification: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UnlockAccountFragment()
    }
}