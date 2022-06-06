package com.under.superadmin.fragments.search_user_recycler_model

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.under.superadmin.R
import com.under.superadmin.model.User
import com.under.superadmin.model.envio

class EnviosAdapter : RecyclerView.Adapter<EnviosViewHolder>() {

    lateinit var listener:EnviosViewHolder.Listener
    var enviosList = ArrayList<envio>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnviosViewHolder {
        //Inflater: XML-> View
        val inflater = LayoutInflater.from(parent.context)
        //row = View
        val row = inflater.inflate(R.layout.envio_row, parent, false)
        val resultView = EnviosViewHolder(row)
        resultView.listener = listener
        Log.e("listener", listener.toString())
        return resultView
    }

    override fun onBindViewHolder(holder: EnviosViewHolder, position: Int) {
        val envioResult = enviosList[position]

        holder.envio = envioResult
        holder.fecha.text = "${envioResult.fecha} "
        holder.transaccionId.text = "${envioResult.id}"
        holder.transaccion.text = "${envioResult.tipo}"
        holder.monto.text = "${envioResult.monto}"

    }

    override fun getItemCount(): Int {
       return enviosList.size
    }
}