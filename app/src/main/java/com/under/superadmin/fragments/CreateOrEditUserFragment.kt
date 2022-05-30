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

    var listener: Listener? = null
    private var _binding: FragmentCreateOrEditUserBinding? = null
    private val binding get() = _binding!!

    private var secondPage:Boolean = false
    private var shortAnimationDuration: Int = 0

    //var currentUser : User? = null -> cargar userInformation when EditPersonalInfo or EditUser
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

        //Aqui verifica si está en un modo de edición, para cargar la información del usuario
        if (mode == editPersonalInfo || mode == editUser) loadUserInfo()

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

    /*
        Aqui se debería de implementar el cargar la información del usuario en los editText
        y spinners. El texto en blanco debería de cambiarse por la información del currentUser
     */
    private fun loadUserInfo(){
    /*
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

        setSpinText(binding.companySpinner, "")
        setSpinText(binding.idTypeSpinner, "")
        setSpinText(binding.rolSpinner, "")
         */
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
        fun onSaveUserInfo()
        fun onBackHome()

        fun onCreateNewUser()
        fun onBackUserAdmin()

        fun onEditUser()
        fun onBackEditUser()
    }
}