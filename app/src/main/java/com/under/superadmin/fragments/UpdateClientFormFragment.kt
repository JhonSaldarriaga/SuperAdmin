package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.under.superadmin.databinding.FragmentUpdateClientFormBinding
import com.under.superadmin.model.Client

class UpdateClientFormFragment : Fragment() {

    private var _binding : FragmentUpdateClientFormBinding? = null
    private val binding get() = _binding!!

    var listener : Listener? = null

    var client: Client? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateClientFormBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    interface Listener{
        fun onBackUserAdmin()
        fun onUpdateClient(client: Client)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UpdateClientFormFragment()
    }
}