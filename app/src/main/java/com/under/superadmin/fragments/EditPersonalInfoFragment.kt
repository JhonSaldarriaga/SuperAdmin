package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentEditPersonalInfoBinding

class EditPersonalInfoFragment : Fragment() {

    var listener: Listener? = null
    private var _binding: FragmentEditPersonalInfoBinding? = null
    private val binding get() = _binding!!

    private var secondPage:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditPersonalInfoBinding.inflate(inflater,container,false)

        loadUserInfo()
        loadSpinners()

        binding.nextSaveButton.setOnClickListener {
            if(secondPage) listener?.onSaveUserInfo()
            else{
                secondPage = true
                setGoneFirstPage()
                setVisibleSecondPage()
                binding.nextSaveButton.text = getString(R.string.text_button_save)
                binding.pageIndicatorTV.text = getString(R.string.edit_personal_info_page_2)
            }
        }

        binding.backIV.setOnClickListener {
            if(secondPage) {
                secondPage = false
                setGoneSecondPage()
                setVisibleFirstPage()
                binding.nextSaveButton.text = getString(R.string.text_button_next)
                binding.pageIndicatorTV.text = getString(R.string.edit_personal_info_page_1)
            } else listener?.onBackHome()
        }

        return binding.root
    }

    private fun loadUserInfo(){
        //LOAD CURRENT USER FROM DATABASE
    }

    private fun loadSpinners(){
        val adapterId = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.edit_personal_info_spinner_id))
        binding.idTypeSpinner.adapter = adapterId
        adapterId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterCompany = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.edit_personal_info_spinner_company))
        binding.companySpinner.adapter = adapterCompany
        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterRol = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.edit_personal_info_spinner_rol))
        binding.rolSpinner.adapter = adapterRol
        adapterRol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    private fun setGoneFirstPage(){
        binding.idTypeSpinner.visibility = View.GONE
        binding.idET.visibility = View.GONE
        binding.nameET.visibility = View.GONE
        binding.firstLastNameET.visibility = View.GONE
        binding.secondLastNameET.visibility = View.GONE
        binding.emailET.visibility = View.GONE
    }

    private fun setGoneSecondPage(){
        binding.userNumberET.visibility = View.GONE
        binding.userPhoneET.visibility = View.GONE
        binding.rolSpinner.visibility = View.GONE
        binding.statusET.visibility = View.GONE
        binding.companySpinner.visibility = View.GONE
    }

    private fun setVisibleFirstPage(){
        binding.idTypeSpinner.visibility = View.VISIBLE
        binding.idET.visibility = View.VISIBLE
        binding.nameET.visibility = View.VISIBLE
        binding.firstLastNameET.visibility = View.VISIBLE
        binding.secondLastNameET.visibility = View.VISIBLE
        binding.emailET.visibility = View.VISIBLE
    }

    private fun setVisibleSecondPage(){
        binding.userNumberET.visibility = View.VISIBLE
        binding.userPhoneET.visibility = View.VISIBLE
        binding.rolSpinner.visibility = View.VISIBLE
        binding.statusET.visibility = View.VISIBLE
        binding.companySpinner.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditPersonalInfoFragment()
    }

    interface Listener{
        fun onSaveUserInfo()
        fun onBackHome()
    }
}