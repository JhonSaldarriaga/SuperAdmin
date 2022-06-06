package com.under.superadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.under.superadmin.databinding.FragmentBlankBinding
import java.util.*
import kotlin.collections.ArrayList


class SendComprobante : Fragment() {
    // TODO: Rename and change types of parameters
    private var tiposEnvios : ArrayList<String> = ArrayList()

    var listener: SendComprobante.Listener? = null
    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBlankBinding.inflate(inflater,container,false)
        Collections.addAll(tiposEnvios, *resources.getStringArray(R.array.tipo_transaccion))
        loadSpinners()
        binding.consultar.setOnClickListener(){
            sendInfo()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SendComprobante()
    }

    interface Listener{
        fun onSearchComprobante(numeroCuenta: String, fecha: String, tipo: String)
    }

    private fun loadSpinners(){
        val adapterId = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.tipo_transaccion))
        binding.tipo.adapter = adapterId
        adapterId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    private fun sendInfo(){
        val numeroCuenta = binding.numeroCuenta2.text.toString()
        val fecha = binding.numeroCuenta.text.toString()
        val tipo = binding.tipo.selectedItem.toString()
        if(numeroCuenta!=""&&fecha!="") {
            listener!!.onSearchComprobante(numeroCuenta, fecha, tipo)
        }
    }
}