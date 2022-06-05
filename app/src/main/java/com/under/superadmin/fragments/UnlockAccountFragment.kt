package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.unlockAccountSearchButton.setOnClickListener { listener!!.onSearchLockedAccount(
            binding.unlockAccountAccountField.text.toString(),
            binding.unlockAccountIdentificationField.text.toString()
        ) }

        return binding.root
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