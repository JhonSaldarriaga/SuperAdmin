package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.under.superadmin.databinding.FragmentP2PConsultBinding
import com.under.superadmin.model.Transaction


class P2PConsult : Fragment() {

    private var _binding: FragmentP2PConsultBinding? = null
    private val binding get() = _binding!!

    private var name: String?=null
    private var lastNames: String?=null
    private var account: String?=null
    private var transactionNumber: String?=null
    private var status: String?=null
    private var url: String?=null
    private var date: String?=null

    var transaction: Transaction?=null

    var listener: Listener?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentP2PConsultBinding.inflate(inflater, container, false)
        name = transaction?.Nombre
        lastNames = transaction?.Apellidos
        account = transaction?.Cuenta
        transactionNumber = transaction?.Transaccion
        status = transaction?.Estado
        url = transaction?.Url
        date = transaction?.Fecha


        binding.nameTxt.text = name
        binding.lastNameTxt.text = lastNames
        binding.accountTxt.text = account
        binding.transactionTxt.text = transactionNumber
        binding.statusTxt.text = status
        binding.urlTxt.text = url
        binding.dateTxt.text = date

        binding.activeButton.setOnClickListener{

        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = P2PConsult()
    }

    interface Listener{

        fun onBackUserAdmin()


    }
}