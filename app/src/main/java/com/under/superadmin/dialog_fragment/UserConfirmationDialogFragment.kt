package com.under.superadmin.dialog_fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentUserConfirmationDialogBinding

class UserConfirmationDialogFragment : DialogFragment() {

    var listener:Listener? = null

    private var _binding: FragmentUserConfirmationDialogBinding? = null
    private val binding get() = _binding!!

    private var id:String = ""
    var mode : String = ""

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
        _binding = FragmentUserConfirmationDialogBinding.inflate(inflater,container,false)

        val createUser : String = getString(R.string.confirm_create_user_dialog_title)
        val editPersonalInfo : String = getString(R.string.confirm_edit_personal_info_dialog_title)

        var title : String = ""
        var center : String = ""

        when(mode){
            createUser -> {
                title = getString(R.string.confirm_create_user_dialog_title)
                center = getString(R.string.confirm_create_user_dialog_text)
            }
            editPersonalInfo -> {
                title = getString(R.string.confirm_edit_personal_info_dialog_title)
                center = getString(R.string.confirm_edit_personal_info_dialog_text)
            }
        }

        binding.titleTV.text = title
        binding.centerTV.text = center
        binding.idTV.text = id
        binding.acceptChangeInfoButton.setOnClickListener {
            dismiss()
            listener?.onAcceptButton(mode)
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
        fun onAcceptButton(mode:String)
    }
}