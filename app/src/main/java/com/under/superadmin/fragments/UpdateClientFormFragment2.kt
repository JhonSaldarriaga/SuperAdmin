package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentUpdateClientForm2Binding

import com.under.superadmin.databinding.FragmentUpdateClientFormBinding
import com.under.superadmin.model.Client
import java.util.*
import kotlin.collections.ArrayList

class UpdateClientFormFragment2 : Fragment() {

    private var _binding : FragmentUpdateClientForm2Binding? = null
    private val binding get() = _binding!!

    var listener : Listener? = null
    var client2: Client? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateClientForm2Binding.inflate(inflater, container, false)

        if(client2 !== null){
            setData()
        }

        binding.updateClientSearchBtn3.setOnClickListener(){
            if(validateData()){
                client2!!.primerNombre = binding.nameUpdateET.text.toString()
                client2!!.segundoNombre = binding.snUpdateET.text.toString()
                client2!!.primerApellido = binding.faUpdateET.text.toString()
                client2!!.segundoApellido = binding.saUpdateET.text.toString()
                client2!!.email = binding.emailET.text.toString()

                listener!!.onGoToUdapteClientConfirmation("Actualizar cliente", client2!!)
            }
        }

        return binding.root
    }

    private fun setData(){
        binding.nameUpdateET.setText(client2!!.primerNombre)
        binding.snUpdateET.setText(client2!!.segundoNombre)
        binding.faUpdateET.setText(client2!!.primerApellido)
        binding.saUpdateET.setText(client2!!.segundoApellido)
        binding.emailET.setText(client2!!.email)
    }

    private fun validateData(): Boolean {
        if(!binding.nameUpdateET.text.isNullOrBlank() && !binding.snUpdateET.text.isNullOrBlank() &&
            !binding.faUpdateET.text.isNullOrBlank() && !binding.saUpdateET.text.isNullOrBlank() &&
            !binding.emailET.text.isNullOrBlank() ){
            return true
        }
        return false

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    interface Listener{
        fun onBackUserAdmin()
        fun onGoToUdapteClientConfirmation(mode: String, client: Client)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UpdateClientFormFragment2()
    }
}