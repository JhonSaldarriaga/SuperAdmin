package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentListaEnviosBinding
import com.under.superadmin.databinding.FragmentSearchResultBinding
import com.under.superadmin.fragments.search_user_recycler_model.EnviosAdapter
import com.under.superadmin.fragments.search_user_recycler_model.EnviosViewHolder
import com.under.superadmin.fragments.search_user_recycler_model.ResultAdapter
import com.under.superadmin.fragments.search_user_recycler_model.ResultViewHolder
import com.under.superadmin.model.User
import com.under.superadmin.model.envio
import java.util.ArrayList


class ListaEnvios : Fragment() {

    var listenerViewHolder : EnviosViewHolder.Listener? = null
    var listener : ListaEnvios.Listener? = null
    var envioList : ArrayList<envio> = ArrayList()

    private var _binding : FragmentListaEnviosBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter : EnviosAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaEnviosBinding.inflate(inflater,container,false)

        initRecycler()
        binding.resultSearchBackButton3.setOnClickListener {
            listener!!.onBackSearchResult()
        }

        return binding.root
    }
    private fun initRecycler() {
        layoutManager = LinearLayoutManager(activity)
        binding.enviosRV.layoutManager = layoutManager
        binding.enviosRV.setHasFixedSize(true)
        adapter = EnviosAdapter()
        adapter.listener = listenerViewHolder!!
        adapter.enviosList = envioList
        binding.enviosRV.adapter = adapter
    }
    companion object {
        @JvmStatic
        fun newInstance() = ListaEnvios()
    }

    interface Listener{
        fun onBackSearchResult()
    }


}