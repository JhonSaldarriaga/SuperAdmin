package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentUnlockAccountResultBinding
import com.under.superadmin.fragments.unlock_account_recycler_model.UnlockResultAdapter
import com.under.superadmin.fragments.unlock_account_recycler_model.UnlockResultViewHolder
import com.under.superadmin.model.Client

class UnlockAccountResultFragment : Fragment() {

    var listenerViewHolder: UnlockResultViewHolder.Listener? = null
    var listener: Listener? = null
    var clientList: ArrayList<Client> = ArrayList()

    private var _binding: FragmentUnlockAccountResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: UnlockResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentUnlockAccountResultBinding.inflate(inflater, container, false)
        initRecycler()

        binding.unlockAccountResulBackButton.setOnClickListener { listener!!.onBackUnlockAccountSearchResult() }

        return binding.root
    }

    private fun initRecycler() {
        layoutManager = LinearLayoutManager(activity)
        binding.unlockAccountResulRecycler.layoutManager = layoutManager
        binding.unlockAccountResulRecycler.setHasFixedSize(true)

        adapter = UnlockResultAdapter()
        adapter.listener = listenerViewHolder!!
        adapter.clientList = clientList
        binding.unlockAccountResulRecycler.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = UnlockAccountResultFragment()
    }

    interface Listener{
        fun onBackUnlockAccountSearchResult()
    }
}