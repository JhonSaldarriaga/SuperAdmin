package com.under.superadmin.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentCreateOrEditUserBinding

class CreateOrEditUserFragment : Fragment() {

    var listener: Listener? = null
    private var _binding: FragmentCreateOrEditUserBinding? = null
    private val binding get() = _binding!!

    private var secondPage:Boolean = false
    private var shortAnimationDuration: Int = 0

    //val currentUser : User? = null -> Use to load userInformation when EditPersonalInfo
    var mode : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateOrEditUserBinding.inflate(inflater,container,false)
        secondPage = false
        loadSpinners()
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        val createUser : String = getString(R.string.admin_user_section_create_user_title)
        val editUser : String = getString(R.string.edit_user_title)
        val editPersonalInfo : String = getString(R.string.edit_personal_info_fragment_title)

        if (mode == editPersonalInfo) loadUserInfo()

        binding.titleEditOrCreatTV.text = mode

        binding.nextSaveButton.setOnClickListener {
            if(secondPage){
                when(mode){
                    createUser -> listener?.onCreateNewUser()
                    editPersonalInfo -> listener?.onSaveUserInfo()
                    editUser -> listener?.onEditUser()
                }
                clearField()
            }
            else{
                secondPage = true
                crossFade(binding.constraintPage1,binding.constraintPage2)
                binding.nextSaveButton.text = getString(R.string.text_button_save)
                binding.pageIndicatorTV.text = getString(R.string.edit_personal_info_page_2)
            }
        }

        binding.backIV.setOnClickListener {
            if(secondPage) {
                secondPage = false
                crossFade(binding.constraintPage2,binding.constraintPage1)
                binding.nextSaveButton.text = getString(R.string.text_button_next)
                binding.pageIndicatorTV.text = getString(R.string.edit_personal_info_page_1)
            } else {
                when(mode){
                    createUser -> listener?.onBackUserAdmin()
                    editPersonalInfo -> listener?.onBackHome()
                    editUser -> listener?.onBackEditUser()
                }
                clearField()
            }
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

    private fun clearField(){
        binding.userNumberET.setText("")
        binding.userPhoneET.setText("")
        binding.statusET.setText("")
        binding.idET.setText("")
        binding.nameET.setText("")
        binding.firstLastNameET.setText("")
        binding.secondLastNameET.setText("")
        binding.emailET.setText("")

        binding.userNumberET.hint = ""
        binding.userPhoneET.hint = ""
        binding.statusET.hint = ""
        binding.idET.hint = ""
        binding.nameET.hint = ""
        binding.firstLastNameET.hint = ""
        binding.secondLastNameET.hint = ""
        binding.emailET.hint = ""
    }

    //p1 -> p2
    private fun crossFade(p1: View, p2: View) {
        p2.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
        p1.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    p1.visibility = View.GONE
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateOrEditUserFragment()
    }

    interface Listener{
        fun onSaveUserInfo()
        fun onBackHome()

        fun onCreateNewUser()
        fun onBackUserAdmin()

        fun onEditUser()
        fun onBackEditUser()
    }
}