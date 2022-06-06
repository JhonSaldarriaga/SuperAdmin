package com.under.superadmin.fragments.search_user_recycler_model

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.under.superadmin.R
import com.under.superadmin.model.User
import com.under.superadmin.model.envio

class EnviosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var envio: envio
    lateinit var listener: Listener

    /// UI Components
    val reenvioButton : Button = itemView.findViewById(R.id.reenvio)
    val fecha : TextView = itemView.findViewById(R.id.fecha)
    val transaccionId : TextView = itemView.findViewById(R.id.transaccionId)
    val transaccion : TextView = itemView.findViewById(R.id.transaccion)
    val monto : TextView = itemView.findViewById(R.id.monto)


    init {
        reenvioButton.setOnClickListener {
        listener.envioComprobante()
        }
    }



    interface Listener{
        fun envioComprobante()

    }
}