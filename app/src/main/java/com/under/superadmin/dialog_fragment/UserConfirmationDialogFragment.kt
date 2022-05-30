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

    /*
        Este DialogFragment, con la opción de ahorrar código, lo utilizo para:
        Confirmacion de crear usuario
        Confirmacion de editar usuario
        Confirmacion de editar usuario logeado

        Para escoger con que modo iniciar el DialogFragment, antes de mostrarlo en la MainActivity,
        deben de pasarle a la referencia del userConfimationDialogFragment un mode
        que corresponda al uso que le quieren dar.

        Para confirmacion de crear usuario:
        userConfirmationDialogFragment.mode = getString(R.string.confirm_create_user_dialog_title)

        Para confirmacion de editar usuario:
        userConfirmationDialogFragment.mode = getString(R.string.confirm_edit_personal_info_dialog_title)

        Ademas de eso, también deben de pasar el id del usuario. Ya sea el creado o editado:
        userConfirmationDialogFragment.id = EL ID
     */

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