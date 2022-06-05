package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentSearchResultBinding
import com.under.superadmin.fragments.search_user_recycler_model.ResultAdapter
import com.under.superadmin.fragments.search_user_recycler_model.ResultViewHolder
import com.under.superadmin.model.User
import java.util.ArrayList


class SearchResultFragment : Fragment() {

    var listenerViewHolder : ResultViewHolder.Listener? = null
    var listener : Listener? = null
    var userList : ArrayList<User> = ArrayList()

    private var _binding : FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter : ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchResultBinding.inflate(inflater,container,false)
        initRecycler()

        binding.resultSearchBackButton.setOnClickListener {
            listener!!.onBackSearchResult()
        }

        return binding.root
    }

    private fun initRecycler() {
        layoutManager = LinearLayoutManager(activity)
        binding.resultRecycler.layoutManager = layoutManager
        binding.resultRecycler.setHasFixedSize(true)
        adapter = ResultAdapter()
        adapter.listener = listenerViewHolder!!
        adapter.userList = userList
        binding.resultRecycler.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchResultFragment()
    }

    interface Listener{
        fun onBackSearchResult()
    }
}