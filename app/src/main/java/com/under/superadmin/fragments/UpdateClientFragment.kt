package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.under.superadmin.databinding.FragmentUpdateClientBinding

class UpdateClientFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding : FragmentUpdateClientBinding? = null
    private val binding get() = _binding!!

    var listener : Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateClientBinding.inflate(inflater, container, false)

        binding.updateClientBackBtn.setOnClickListener { listener!!.onBackUserAdmin() }
        binding.updateClientSearchBtn.setOnClickListener { listener!!.onSearchClientUpdateAccount(
            binding.updateClientAccountTF.text.toString(),
            binding.updateClientTF2.text.toString()
        ) }

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    interface Listener{
        fun onBackUserAdmin()
        fun onSearchClientUpdateAccount(account: String, identification: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UpdateClientFragment()
    }
}