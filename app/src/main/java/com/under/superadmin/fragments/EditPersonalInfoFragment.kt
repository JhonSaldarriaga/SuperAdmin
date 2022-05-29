package com.under.superadmin.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentEditPersonalInfoBinding

class EditPersonalInfoFragment : Fragment() {

    var listener: Listener? = null
    private var _binding: FragmentEditPersonalInfoBinding? = null
    private val binding get() = _binding!!

    private var secondPage:Boolean = false
    private var shortAnimationDuration: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditPersonalInfoBinding.inflate(inflater,container,false)
        secondPage = false
        loadUserInfo()
        loadSpinners()

        shortAnimationDuration = resources.getInteger(android.R.integer.config_mediumAnimTime)

        binding.nextSaveButton.setOnClickListener {
            if(secondPage) listener?.onSaveUserInfo()
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
        fun newInstance() = EditPersonalInfoFragment()
    }

    interface Listener{
        fun onSaveUserInfo()
        fun onBackHome()
    }
}