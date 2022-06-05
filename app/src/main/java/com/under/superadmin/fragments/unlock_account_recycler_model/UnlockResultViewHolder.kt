package com.under.superadmin.fragments.unlock_account_recycler_model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.under.superadmin.R
import com.under.superadmin.model.Client

class UnlockResultViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView) {

    lateinit var client: Client
    lateinit var listener: Listener

    /// UI Components
    val clientName_txt: TextView = itemView.findViewById(R.id.unlockAccount_nameClient)
    val identification_txt: TextView = itemView.findViewById(R.id.unlockAccount_identification)
    val restrictiveList_txt: TextView = itemView.findViewById(R.id.unlockAccount_restrictiveList)
    val account_txt: TextView = itemView.findViewById(R.id.unlockAccount_accountNumber)

    val accountLocked: ImageView = itemView.findViewById(R.id.unlockAccount_accountButton)
    val userLocked: ImageView = itemView.findViewById(R.id.unlockAccount_userButton)
    val facialLocked: ImageView = itemView.findViewById(R.id.unlockAccount_facialButton)


    init {
        setStateButtons(accountLocked, client.cuentaBloqueada)
        setStateButtons(userLocked, client.usuarioBloqueado)
        setStateButtons(facialLocked, client.reconocimientoFacialBloqueado)
    }

    private fun setStateButtons(button: ImageView, state: Boolean) {
        if (state){
            button.setOnClickListener { listener.onGoToUnlockAccount(client) }
            button.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_red)

        }else {
            button.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_green)
        }
    }

    interface Listener {
        fun onGoToUnlockAccount(client: Client)
    }
}