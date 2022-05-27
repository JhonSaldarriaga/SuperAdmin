package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.under.superadmin.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    var listener: Listener? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.logoutButton.setOnClickListener {
            //Toast.makeText(requireContext(),"hola", Toast.LENGTH_SHORT).show()
        }

        binding.userNameTV.setOnClickListener { listener?.onUserNameClickListener() }

            return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    interface Listener{
        fun onUserNameClickListener()
    }
}