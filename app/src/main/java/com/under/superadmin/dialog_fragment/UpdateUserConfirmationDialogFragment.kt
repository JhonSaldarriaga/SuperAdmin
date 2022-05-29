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
import com.under.superadmin.databinding.FragmentUpdateUserConfirmationDialogBinding

class UpdateUserConfirmationDialogFragment : DialogFragment() {

    var listener:Listener? = null

    private var _binding: FragmentUpdateUserConfirmationDialogBinding? = null
    private val binding get() = _binding!!

    private var id:String? = null

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
        _binding = FragmentUpdateUserConfirmationDialogBinding.inflate(inflater,container,false)
        binding.idTV.text = id
        binding.acceptChangeInfoButton.setOnClickListener {
            dismiss()
            listener?.onAcceptButton()
        }

        return binding.root
    }

    fun setIdText(id:String){
        this.id = id
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener{
        fun onAcceptButton()
    }
}