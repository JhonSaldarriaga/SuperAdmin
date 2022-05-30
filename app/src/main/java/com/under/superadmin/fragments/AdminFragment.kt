package com.under.superadmin.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    /*
       Este fragmento carga todas las opciones de la parte administrativa, es decir
       Modulo administrativo y Modulo transaccional. Tiene las transiciones de las navegaciones
       para cuando se escoge cada opción.
       Las opciones finales de cada modulo, tipo Crear usuario, editar usuarios o Actualizar cliente
       etc ... Ya deberían de llevar hacia otro fragmento.
     */

    private var ADMIN_PAGE : String = ""
    private var MODULE_ADMIN : String = ""
    private var ADMIN_USERS : String = ""
    private var MODULE_TRANSACTIONAL : String = ""

    var listener: Listener? = null
    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    private var page: String = ADMIN_PAGE
    private var shortAnimationDuration: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdminBinding.inflate(inflater,container,false)
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        //INITIAL VALUES
        ADMIN_PAGE = "${getString(R.string.admin_fragment_title)}"
        MODULE_ADMIN = "${getString(R.string.admin_fragment_title)}/${getString(R.string.admin_module_title)}"
        ADMIN_USERS = ".../${getString(R.string.admin_user_section_title)}"
        MODULE_TRANSACTIONAL = "${getString(R.string.admin_fragment_title)}/${getString(R.string.transactional_module_title)}"
        page = ADMIN_PAGE
        binding.pageAdminIndicatorTV.text = page

        // Programada la lógica de la navegación. El back button etc.

        binding.backAdminButton.setOnClickListener {
            when(page){
                MODULE_ADMIN -> {
                    changePage(ADMIN_PAGE,
                        View.VISIBLE,View.GONE,getString(R.string.admin_fragment_title),
                        binding.constraintAdminModule,binding.constraintAdmin)
                }
                MODULE_TRANSACTIONAL -> {
                    changePage(ADMIN_PAGE,
                        View.VISIBLE,View.GONE,getString(R.string.admin_fragment_title),
                        binding.constraintTransactionalModule,binding.constraintAdmin)
                }
                ADMIN_USERS -> {
                    changePage(MODULE_ADMIN,
                        View.INVISIBLE,View.VISIBLE,getString(R.string.admin_module_title),
                        binding.constraintAdminModuleAdminUser,binding.constraintAdminModule)
                }
            }
        }

        binding.adminModuleButton.setOnClickListener {
            changePage(MODULE_ADMIN,
                View.INVISIBLE,View.VISIBLE,getString(R.string.admin_module_title),
                binding.constraintAdmin,binding.constraintAdminModule)
        }

        binding.adminUserButton.setOnClickListener {
            changePage(ADMIN_USERS,
                View.INVISIBLE,View.VISIBLE,getString(R.string.admin_user_section_title),
                binding.constraintAdminModule,binding.constraintAdminModuleAdminUser)
        }

        binding.transactionalModuleButton.setOnClickListener {
            changePage(MODULE_TRANSACTIONAL,
                View.INVISIBLE,View.VISIBLE,getString(R.string.transactional_module_title),
                binding.constraintAdmin,binding.constraintTransactionalModule)
        }

        setOnClickListenerAdminUser()
        setOnClickListenerTransactionalModule()

        return binding.root
    }

    /*
        En estas dos funciones, se hace uso del listener que conecta con la MainActivity, para que
        la MainActivity sea la que se encargue de todas las transiciones de las posibles opciones.
     */
    private fun setOnClickListenerAdminUser(){
        binding.createUserButton.setOnClickListener { listener?.onCreateUserClickListener() }
        binding.editUserButton.setOnClickListener { listener?.onEditUserClickListener() }
    }

    private fun setOnClickListenerTransactionalModule(){
        binding.updateClientButton.setOnClickListener { listener?.onUpdateClientClickListener() }
        binding.closeCompanyButton.setOnClickListener { listener?.onCloseCompanyClickListener() }
        binding.connectionsButton.setOnClickListener { listener?.onConnectionsClickListener() }
        binding.unlockAccountButton.setOnClickListener { listener?.onUnlockAccountClickListener() }
        binding.validateTransactionP2PButton.setOnClickListener { listener?.onValidateTransactionClickListener() }
        binding.homologationsButton.setOnClickListener { listener?.onHomologationsClickListener() }
        binding.productButton.setOnClickListener { listener?.onProductClickListener() }
        binding.subProductButton.setOnClickListener { listener?.onSubProductClickListener() }
        binding.forwardingOfReceiptButton.setOnClickListener { listener?.onForwardingOfReceiptClickListener() }
        binding.colabButton.setOnClickListener { listener?.onColabClickListener() }
    }

    private fun changePage(
        page:String,
        adminDescriptionVisibility:Int,
        backAdminButtonVisibility:Int,
        adminFragmentTitleTV:String,
        page1:View,
        page2:View){
        this.page = page
        binding.pageAdminIndicatorTV.text = page
        binding.adminDescriptionTV.visibility = adminDescriptionVisibility
        binding.backAdminButton.visibility = backAdminButtonVisibility
        binding.adminFragmentTitleTV.text = adminFragmentTitleTV
        crossFade(page1,page2)
    }

    // Animación
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
        fun newInstance() = AdminFragment()
    }

    interface Listener{
        //ADMIN USER
        fun onCreateUserClickListener()
        fun onEditUserClickListener()

        //TRANSACTIONAL MODULE
        fun onUpdateClientClickListener()
        fun onCloseCompanyClickListener()
        fun onConnectionsClickListener()
        fun onUnlockAccountClickListener()
        fun onValidateTransactionClickListener()
        fun onHomologationsClickListener()
        fun onProductClickListener()
        fun onSubProductClickListener()
        fun onForwardingOfReceiptClickListener()
        fun onColabClickListener()
    }
}