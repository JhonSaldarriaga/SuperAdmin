package com.under.superadmin.fragments.search_user_recycler_model

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.under.superadmin.R
import com.under.superadmin.model.User

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var user: User
    lateinit var listener: Listener

    /// UI Components
    val editButton : Button = itemView.findViewById(R.id.editButtonCard)
    val idCard : TextView = itemView.findViewById(R.id.idCard)
    val telCard : TextView = itemView.findViewById(R.id.telCard)
    val rolCard : TextView = itemView.findViewById(R.id.rolCard)
    val celCard : TextView = itemView.findViewById(R.id.celCard)
    val activeCard : TextView = itemView.findViewById(R.id.activeTextView)
    val inactiveCard : TextView = itemView.findViewById(R.id.inactiveTextView)
    val name : TextView = itemView.findViewById(R.id.nameCard)

    init {
        editButton.setOnClickListener {
            listener.onGoToEditUser(user)
        }
    }

    fun setActive(){
        activeCard.visibility = View.VISIBLE
        inactiveCard.visibility = View.GONE
    }

    fun setInactive(){
        inactiveCard.visibility = View.VISIBLE
        activeCard.visibility = View.GONE
    }

    interface Listener{
        fun onGoToEditUser(user:User)
    }
}