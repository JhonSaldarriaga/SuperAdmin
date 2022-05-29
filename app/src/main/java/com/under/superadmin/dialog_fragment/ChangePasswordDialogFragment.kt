package com.under.superadmin.dialog_fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentChangePasswordDialogBinding

class ChangePasswordDialogFragment : DialogFragment() {

    var listener: Listener? = null
    private var _binding: FragmentChangePasswordDialogBinding? = null
    private val binding get() = _binding!!

    //ACOMODA LA VISTA
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordDialogBinding.inflate(inflater,container,false)

        binding.goToChangePasswordButton.setOnClickListener {
            val email = binding.emailChangePasswordET.text.toString()
            if (email!="") listener?.onNextButton(binding.emailChangePasswordET.text.toString())
            else Toast.makeText(requireContext(), R.string.login_error_empty_fields, Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener{
        fun onNextButton(email: String)
    }
}