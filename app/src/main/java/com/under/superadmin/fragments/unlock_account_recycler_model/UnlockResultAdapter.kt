package com.under.superadmin.fragments.unlock_account_recycler_model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.under.superadmin.R
import com.under.superadmin.model.Client

class UnlockResultAdapter: RecyclerView.Adapter<UnlockResultViewHolder>() {

    lateinit var listener: UnlockResultViewHolder.Listener
    var clientList = ArrayList<Client>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnlockResultViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.unlock_client_row, parent, false)
        val resultView = UnlockResultViewHolder(row)
        resultView.listener = listener
        return resultView
    }

    override fun onBindViewHolder(holder: UnlockResultViewHolder, position: Int) {

        val clientResult = clientList[position]

        holder.client = clientResult
        holder.clientName_txt.text = "${clientResult.primerNombre} ${clientResult.primerApellido}"
        holder.identification_txt.text = clientResult.numeroIdentificacion
        holder.restrictiveList_txt.text = clientResult.listaRestrictiva
        holder.account_txt.text = clientResult.numeroCelular
    }

    override fun getItemCount(): Int {
        return clientList.size
    }


}