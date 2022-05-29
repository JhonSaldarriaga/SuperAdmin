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

        ADMIN_PAGE = "${getString(R.string.admin_fragment_title)}"
        MODULE_ADMIN = "${getString(R.string.admin_fragment_title)}/${getString(R.string.admin_module_title)}"
        ADMIN_USERS = ".../${getString(R.string.admin_user_section_title)}"
        MODULE_TRANSACTIONAL = "${getString(R.string.admin_fragment_title)}/${getString(R.string.transactional_module_title)}"
        page = ADMIN_PAGE
        binding.pageAdminIndicatorTV.text = page

        binding.backAdminButton.setOnClickListener {
            when(page){
                MODULE_ADMIN -> {
                    page = ADMIN_PAGE
                    binding.pageAdminIndicatorTV.text = page
                    crossFade(binding.constraintAdminModule, binding.constraintAdmin)
                    binding.adminDescriptionTV.visibility = View.VISIBLE
                    binding.adminFragmentTitleTV.text = getString(R.string.admin_fragment_title)
                    binding.backAdminButton.visibility = View.GONE
                }
                MODULE_TRANSACTIONAL -> {
                    page = ADMIN_PAGE
                    binding.pageAdminIndicatorTV.text = page
                    crossFade(binding.constraintTransactionalModule, binding.constraintAdmin)
                    binding.adminDescriptionTV.visibility = View.VISIBLE
                    binding.adminFragmentTitleTV.text = getString(R.string.admin_fragment_title)
                    binding.backAdminButton.visibility = View.GONE
                }
                ADMIN_USERS -> {
                    page = MODULE_ADMIN
                    binding.pageAdminIndicatorTV.text = page
                    crossFade(binding.constraintAdminModuleAdminUser, binding.constraintAdminModule)
                    binding.adminFragmentTitleTV.text = getString(R.string.admin_module_title)
                }
            }
        }

        binding.adminModuleButton.setOnClickListener {
            page = MODULE_ADMIN
            binding.pageAdminIndicatorTV.text = page
            binding.adminDescriptionTV.visibility = View.INVISIBLE
            binding.backAdminButton.visibility = View.VISIBLE
            binding.adminFragmentTitleTV.text = getString(R.string.admin_module_title)
            crossFade(binding.constraintAdmin,binding.constraintAdminModule)
        }

        binding.adminUserButton.setOnClickListener {
            page = ADMIN_USERS
            binding.pageAdminIndicatorTV.text = page
            binding.adminDescriptionTV.visibility = View.INVISIBLE
            binding.adminFragmentTitleTV.text = getString(R.string.admin_user_section_title)
            crossFade(binding.constraintAdminModule,binding.constraintAdminModuleAdminUser)
        }

        binding.transactionalModuleButton.setOnClickListener {
            page = MODULE_TRANSACTIONAL
            binding.pageAdminIndicatorTV.text = page
            binding.adminDescriptionTV.visibility = View.INVISIBLE
            binding.backAdminButton.visibility = View.VISIBLE
            binding.adminFragmentTitleTV.text = getString(R.string.transactional_module_title)
            crossFade(binding.constraintAdmin,binding.constraintTransactionalModule)
        }

        return binding.root
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
        fun newInstance() = AdminFragment()
    }

    interface Listener{

    }
}