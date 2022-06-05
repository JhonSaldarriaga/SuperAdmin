package com.under.superadmin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentSearchUserBinding


class SearchUserFragment : Fragment() {

    private var _binding : FragmentSearchUserBinding? = null
    private val binding get() = _binding!!

    var listener : Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        loadSpinners()

        binding.searchUserBackButton.setOnClickListener { listener!!.onBackUserAdmin() }
        binding.searchButton.setOnClickListener { listener!!.onSearch(
            binding.colabSpinnerSearchUser.selectedItem.toString(),
            binding.searchBySpinner.selectedItem.toString(),
            binding.optionSpinnerSearchUser.selectedItem.toString()) }

        return binding.root
    }

    private fun loadSpinners(){
        val adapterColab = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.edit_personal_info_spinner_company))
        binding.colabSpinnerSearchUser.adapter = adapterColab
        adapterColab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterSearchBy = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.search_option_spinner))
        binding.searchBySpinner.adapter = adapterSearchBy
        adapterSearchBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.searchBySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (binding.searchBySpinner.selectedItem.toString() == "Tipo de identificación"){
                    val adapterOption = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.edit_personal_info_spinner_id))
                    binding.optionSpinnerSearchUser.adapter = adapterOption
                    adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }else if(binding.searchBySpinner.selectedItem.toString() == "Rol"){
                    val adapterOption = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.edit_personal_info_spinner_rol))
                    binding.optionSpinnerSearchUser.adapter = adapterOption
                    adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.e(">>>","nada seleccionado")
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    interface Listener{
        fun onBackUserAdmin()
        fun onSearch(colab: String, searchBy: String, option: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchUserFragment()
    }
}