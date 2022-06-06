package com.under.superadmin.fragments.unlock_account_recycler_model

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.under.superadmin.R
import com.under.superadmin.model.Client

class UnlockResultViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView) {

    var client: Client? = null
    lateinit var listener: Listener

    /// UI Components
    val clientName_txt: TextView = itemView.findViewById(R.id.unlockAccount_nameClient)
    val identification_txt: TextView = itemView.findViewById(R.id.unlockAccount_identification)
    val restrictiveList_txt: TextView = itemView.findViewById(R.id.unlockAccount_restrictiveList)
    val account_txt: TextView = itemView.findViewById(R.id.unlockAccount_accountNumber)

    val accountLocked: ImageView = itemView.findViewById(R.id.unlockAccount_accountButton)
    val userLocked: ImageView = itemView.findViewById(R.id.unlockAccount_userButton)
    val facialLocked: ImageView = itemView.findViewById(R.id.unlockAccount_facialButton)


    fun addButtonsFunctionality() {

        client?.let { setStateButtons(accountLocked, it.cuentaBloqueada) }
        client?.let { setStateButtons(userLocked, it.usuarioBloqueado) }
        client?.let { setStateButtons(facialLocked, it.reconocimientoFacialBloqueado) }
    }

    private fun setStateButtons(button: ImageView, state: Boolean) {
        if (state){

            button.setOnClickListener { client?.let { it1 -> listener.onGoToUnlockAccount(it1) } }
            button.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_red)

        }else {
            button.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_green)
        }
    }

    interface Listener {
        fun onGoToUnlockAccount(client: Client)
    }
}