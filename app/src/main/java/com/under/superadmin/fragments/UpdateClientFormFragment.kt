package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.under.superadmin.R

import com.under.superadmin.databinding.FragmentUpdateClientFormBinding
import com.under.superadmin.model.Client
import java.util.*
import kotlin.collections.ArrayList

class UpdateClientFormFragment : Fragment() {

    private var _binding : FragmentUpdateClientFormBinding? = null
    private val binding get() = _binding!!

    private var infoSpinnerId : ArrayList<String> = ArrayList()
    private var infoSpinnerGender : ArrayList<String> = ArrayList()

    var listener : Listener? = null
    var client: Client? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateClientFormBinding.inflate(inflater, container, false)
        Collections.addAll(infoSpinnerId, *resources.getStringArray(R.array.edit_personal_info_spinner_id))
        Collections.addAll(infoSpinnerId, *resources.getStringArray(R.array.gender_options))
        loadSpinners()

        if(client !== null){
            setData()
        }

        binding.updateClientSearchBtn3.setOnClickListener(){
            client!!.numeroIdentificacion = binding.idET.text.toString()
            client!!.fechaNacimiento = binding.birthdayET.text.toString()
            client!!.fechaExpedicionDocumento = binding.idExpiditionDateET.text.toString()
            client!!.lugarExpedicionDocumento = binding.idExpiditionPlaceET.text.toString()

            listener!!.onGoToSecondForm(client!!)
        }

        return binding.root
    }

    private fun loadSpinners(){
        val adapterId = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.edit_personal_info_spinner_id))
        binding.idTypeSpinner.adapter = adapterId
        adapterId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterGender = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.gender_options))
        binding.genderTypeSpinner.adapter = adapterGender
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    private fun setData(){
        binding.idET.setText(client!!.numeroIdentificacion)
        binding.birthdayET.setText(client!!.fechaNacimiento)
        binding.idExpiditionDateET.setText(client!!.fechaExpedicionDocumento)
        binding.idExpiditionPlaceET.setText(client!!.lugarExpedicionDocumento)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    interface Listener{
        fun onBackUserAdmin()
        fun onGoToSecondForm(clientUpdate: Client)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UpdateClientFormFragment()
    }
}