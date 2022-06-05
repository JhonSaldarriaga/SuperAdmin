package com.under.superadmin.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentCreateOrEditUserBinding
import com.under.superadmin.model.User
import java.util.*
import kotlin.collections.ArrayList

class CreateOrEditUserFragment : Fragment() {

    /*
        Este fragment, con la opción de ahorrar código, lo utilizo para:
        Editar información del usuario logeado.
        Editar información de un usuario.
        Crear usuario.
        Para escoger con que modo iniciar el fragment, antes de utilizar el método showFragment()
        en la MainActivity, deben de pasarle a la referencia del createOrEditUserFragment un mode
        que corresponda al uso que le quieren dar.

        Para crear usuario:
        createOrEditUserFragment.mode = getString(R.string.admin_user_section_create_user_title)

        Para editar usuario:
        createOrEditUserFragment.mode = getString(R.string.edit_user_title)

        Para editar información de un usuario logeado:
        createOrEditUserFragment.mode = getString(R.string.edit_personal_info_fragment_title)

        en cualquiera de los dos casos de edición debería de pasarse también a un atributo llamado
        currentUser el usuario que se quiere editar, para poder cargar toda esa información en los
        editText y Spinner. No la coloco porque falta el modelo del user
     */

    private var editPersonalInfoSpinnerCompany : ArrayList<String> = ArrayList()
    private var editPersonalInfoSpinnerId : ArrayList<String> = ArrayList()
    private var editPersonalInfoSpinnerRol : ArrayList<String> = ArrayList()

    var listener: Listener? = null
    private var _binding: FragmentCreateOrEditUserBinding? = null
    private val binding get() = _binding!!

    private var secondPage:Boolean = false
    private var shortAnimationDuration: Int = 0

    var currentUser : User? = null
    var mode : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateOrEditUserBinding.inflate(inflater,container,false)

        Collections.addAll(editPersonalInfoSpinnerCompany, *resources.getStringArray(R.array.edit_personal_info_spinner_company))
        Collections.addAll(editPersonalInfoSpinnerId, *resources.getStringArray(R.array.edit_personal_info_spinner_id))
        Collections.addAll(editPersonalInfoSpinnerRol, *resources.getStringArray(R.array.edit_personal_info_spinner_rol))
        secondPage = false
        loadSpinners()
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        val createUser : String = getString(R.string.admin_user_section_create_user_title)
        val editUser : String = getString(R.string.edit_user_title)
        val editPersonalInfo : String = getString(R.string.edit_personal_info_fragment_title)

        //Aqui verifica si está en un modo de edición, para cargar la información del usuario
        if (mode == editPersonalInfo || mode == editUser) loadUserInfo(currentUser!!)

        binding.titleEditOrCreatTV.text = mode

        binding.nextSaveButton.setOnClickListener {
            if(secondPage){
                if(verifyEmptyFields()) Toast.makeText(requireContext(), R.string.login_error_empty_fields, Toast.LENGTH_SHORT).show()
                else{
                    when(mode){
                        createUser -> listener?.onCreateNewUser(extractUserInfo().apply { claveAuto = true })
                        editPersonalInfo -> listener?.onSaveUserInfo(extractUserInfo())
                        editUser -> listener?.onEditUser(extractUserInfo())
                    }
                    clearField()
                }
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

    private fun verifyEmptyFields(): Boolean{
        var userNumber: String = if(binding.userNumberET.text.toString() != "") binding.userNumberET.text.toString() else binding.userNumberET.hint.toString()
        var userPhone = if(binding.userPhoneET.text.toString() != "") binding.userPhoneET.text.toString() else binding.userPhoneET.hint.toString()
        var status = if(binding.statusET.text.toString()!="")binding.statusET.text.toString() else binding.statusET.hint.toString()
        var id = if(binding.idET.text.toString()!="") binding.idET.text.toString() else binding.idET.hint.toString()
        var name = if(binding.nameET.text.toString()!="") binding.nameET.text.toString() else binding.nameET.hint.toString()
        var firstLastName = if(binding.firstLastNameET.text.toString()!="") binding.firstLastNameET.text.toString() else binding.firstLastNameET.hint.toString()
        var secondLastName = if(binding.secondLastNameET.text.toString()!="") binding.secondLastNameET.text.toString() else binding.secondLastNameET.hint.toString()
        var email = if(binding.emailET.text.toString()!="") binding.emailET.text.toString() else binding.emailET.hint.toString()

        return userNumber != "" && userPhone != "" && status != "" && id != "" && name != "" && firstLastName != "" && secondLastName != "" && email != ""
    }

    private fun extractUserInfo():User{
        var userNumber: String = if(binding.userNumberET.text.toString() != "") binding.userNumberET.text.toString() else binding.userNumberET.hint.toString()
        var userPhone = if(binding.userPhoneET.text.toString() != "") binding.userPhoneET.text.toString() else binding.userPhoneET.hint.toString()
        var status = if(binding.statusET.text.toString()!="")binding.statusET.text.toString() else binding.statusET.hint.toString()
        var id = if(binding.idET.text.toString()!="") binding.idET.text.toString() else binding.idET.hint.toString()
        var name = if(binding.nameET.text.toString()!="") binding.nameET.text.toString() else binding.nameET.hint.toString()
        var firstLastName = if(binding.firstLastNameET.text.toString()!="") binding.firstLastNameET.text.toString() else binding.firstLastNameET.hint.toString()
        var secondLastName = if(binding.secondLastNameET.text.toString()!="") binding.secondLastNameET.text.toString() else binding.secondLastNameET.hint.toString()
        var email = if(binding.emailET.text.toString()!="") binding.emailET.text.toString() else binding.emailET.hint.toString()
        var typeId = binding.idTypeSpinner.selectedItem.toString()
        var colaborador = binding.companySpinner.selectedItem.toString()
        var rol = binding.rolSpinner.selectedItem.toString()

        return User(firstLastName,secondLastName,name,colaborador,false,email,status,userNumber,userPhone,id,typeId,rol)
    }

    /*
        Aqui se debería de implementar el cargar la información del usuario en los editText
        y spinners. El texto en blanco debería de cambiarse por la información del currentUser
     */
    private fun loadUserInfo(user:User){
        binding.userNumberET.hint = user.numeroCelular
        binding.userPhoneET.hint = user.numeroTelefono
        binding.statusET.hint = user.estado
        binding.idET.hint = user.numeroidentificacion
        binding.nameET.hint = user.nombre
        binding.firstLastNameET.hint = user.apellido1
        binding.secondLastNameET.hint = user.apellido2
        binding.emailET.hint = user.email
        binding.rolSpinner.setSelection(editPersonalInfoSpinnerRol.indexOf(user.rol))
        binding.companySpinner.setSelection(editPersonalInfoSpinnerCompany.indexOf(user.colaborador))
        binding.idTypeSpinner.setSelection(editPersonalInfoSpinnerId.indexOf(user.tipoIdetificacion))
    }

    /*
        Carga los spinners. Como no se muy bien que información maneja cada spinner.
        Se puede agregar el values/strings ahí se encuentran los arreglos de los 3 spinners.
        Para agregarle elementos solo es ir creando <item>Texto</item>
     */
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

    // Animacion de transicion
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
        fun onSaveUserInfo(user: User)
        fun onBackHome()

        fun onCreateNewUser(user: User)
        fun onBackUserAdmin()

        fun onEditUser(user: User)
        fun onBackEditUser()
    }
}