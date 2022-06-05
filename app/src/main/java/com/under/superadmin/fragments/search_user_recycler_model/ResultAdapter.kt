package com.under.superadmin.fragments.search_user_recycler_model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.under.superadmin.R
import com.under.superadmin.model.User

class ResultAdapter : RecyclerView.Adapter<ResultViewHolder>() {

    lateinit var listener:ResultViewHolder.Listener
    var userList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        //Inflater: XML-> View
        val inflater = LayoutInflater.from(parent.context)
        //row = View
        val row = inflater.inflate(R.layout.result_row, parent, false)
        val resultView = ResultViewHolder(row)
        resultView.listener = listener
        return resultView
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val userResult = userList[position]
        if(userResult.estado == "ACTIVO") holder.setActive()
        else holder.setInactive()
        holder.user = userResult
        holder.name.text = "${userResult.nombre} ${userResult.apellido1}"
        holder.idCard.text = userResult.numeroidentificacion
        holder.telCard.text = userResult.numeroTelefono
        holder.rolCard.text = userResult.rol
        holder.celCard.text = userResult.numeroCelular
    }

    override fun getItemCount(): Int {
       return userList.size
    }
}