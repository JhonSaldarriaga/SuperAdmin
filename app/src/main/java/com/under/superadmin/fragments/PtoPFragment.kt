package com.under.superadmin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentPtoPBinding
import com.under.superadmin.model.User


class PtoPFragment : Fragment(){

    private var _binding: FragmentPtoPBinding? = null
    private val binding get() = _binding!!

    private var dataTransaction: String? = null
    private var account: String? = null
    private var transaction: String? = null

    var listener: Listener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPtoPBinding.inflate(inflater,container,false)


        binding.backP2P.setOnClickListener{
            listener?.onBackUserAdmin()
        }
        binding.consultButton.setOnClickListener {
            dataTransaction = binding.dataTransaction.text.toString()
            account = binding.account.text.toString()
            transaction = binding.transaction.text.toString()
            listener?.onConsultTransaction(dataTransaction!!, account!!, transaction!!)
        }

        binding.cleanButton.setOnClickListener{
            binding.dataTransaction.setText("")
            binding.account.setText("")
            binding.transaction.setText("")


            binding.dataTransaction.hint = ""
            binding.account.hint = ""
            binding.transaction .hint = ""

        }
        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance() = PtoPFragment()
    }

    interface Listener{

        fun onConsultTransaction(dataTransaction: String, account: String, transaction: String)

        fun onBackUserAdmin()


    }


}