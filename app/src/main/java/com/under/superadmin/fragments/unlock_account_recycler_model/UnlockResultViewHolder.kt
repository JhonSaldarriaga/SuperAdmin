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
    private var listChanges = ArrayList<String>()

    /// UI Components
    val clientName_txt: TextView = itemView.findViewById(R.id.unlockAccount_nameClient)
    val identification_txt: TextView = itemView.findViewById(R.id.unlockAccount_identification)
    val restrictiveList_txt: TextView = itemView.findViewById(R.id.unlockAccount_restrictiveList)
    val account_txt: TextView = itemView.findViewById(R.id.unlockAccount_accountNumber)

    val accountLocked: ImageView = itemView.findViewById(R.id.unlockAccount_accountButton)
    val userLocked: ImageView = itemView.findViewById(R.id.unlockAccount_userButton)
    val facialLocked: ImageView = itemView.findViewById(R.id.unlockAccount_facialButton)


    fun addButtonsFunctionality() {
        if (client?.cuentaBloqueada == true){
            accountLocked.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_red)
            accountLocked()

        }else {
            accountLocked.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_green)
        }

        if(client?.usuarioBloqueado == true){
            userLocked.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_red)
            userLocked()

        }else {
            userLocked.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_green)
        }

        if(client?.reconocimientoFacialBloqueado == true){
            facialLocked.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_red)
            facialRecognitionLocked()

        }else {
            facialLocked.background = ContextCompat.getDrawable(itemView.context, R.drawable.circle_green)
        }
    }

    private fun accountLocked(){
        accountLocked.setOnClickListener {
            listChanges.add(itemView.context.getString(R.string.result_unlock_account_locked_account))
            client?.let { it1 ->
                listener.onGoToUnlockClientConfirmation(itemView.context.getString(R.string.unlock_account_title), it1, listChanges) }
        }
    }

    private fun userLocked() {
        userLocked.setOnClickListener {
            listChanges.add(itemView.context.getString(R.string.result_unlock_account_user_locked))
            client?.let { it1 ->
                listener.onGoToUnlockClientConfirmation(itemView.context.getString(R.string.unlock_account_title), it1, listChanges) }
        }
    }

    private fun facialRecognitionLocked() {
        facialLocked.setOnClickListener {
            listChanges.add(itemView.context.getString(R.string.result_unlock_account_facial_locked))
            client?.let { it1 ->
                listener.onGoToUnlockClientConfirmation(itemView.context.getString(R.string.unlock_account_title), it1, listChanges) }
        }
    }

    interface Listener {
        fun onGoToUnlockClientConfirmation(mode: String, client: Client, changesList: ArrayList<String>)
    }
}