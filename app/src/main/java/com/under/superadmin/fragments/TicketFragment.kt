package com.under.superadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.under.superadmin.R
import com.under.superadmin.databinding.FragmentTicketBinding
import com.under.superadmin.model.Client
import com.under.superadmin.model.Ticket
import com.under.superadmin.model.Transaction
import com.under.superadmin.model.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TicketFragment : Fragment() {

    //Declarar aqui los parametros necesarios de los diferentes llamados desde las diferentes pantallas
    var listener: Listener? = null
    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!
    var currentUser: User? = null

    var unlockAccountClient: Client? = null
    var transaction: Transaction? = null


    /*
        Este parametro mode se utiliza para saber desde que pantalla se viene. Ej= si yo vengo desde unlockAccount,
        entonces antes de invocar el metodo showFragment para mostrar este fragmento, le paso al modo el nombre
        del titulo principal de la pantalla desde vengo, en este caso o ejemplo, R.string.unlock_account_title
        el cual si lo abren y buscan en el archivo de strings, aparece "Desbloquear Cuenta", entonces ese sera el mode
     */
    var mode: String = ""


    /*
        Este arraylist se le pasa al framgento antes del metodo showFragment, para ver la lista de items que han
        sido editados
     */
    var listChanges = ArrayList<String>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTicketBinding.inflate(inflater, container, false)

        val adapter = container?.let { ArrayAdapter<String>(it.context, android.R.layout.simple_list_item_1, listChanges) }
        binding.changesMadeList.adapter = adapter

        binding.ticketTitle.text = mode
        binding.ticketConfirmationButton.setOnClickListener { validateTicket() }
        binding.ticketBackButton.setOnClickListener { returnToPreviousPage() }

        return binding.root
    }


    private fun validateTicket(){
        val ticketNumber = binding.ticketNumberField.text.toString()

        if (ticketNumber.compareTo("") != 0){
            Firebase.firestore.collection("tickets").whereEqualTo("numeroIdentificacion", ticketNumber).get().addOnCompleteListener { task ->
                if(task.result?.size() != 0){
                    for(document in task.result!!) {
                        val ticketFound = document.toObject(Ticket::class.java)
                        if(ticketFound.numeroIdentificacion.compareTo(ticketNumber) == 0 && ticketFound.activo){
                            ticketFound.activo = false
                            ticketFound.fechaCerrado = SimpleDateFormat("MMM dd, yy 'at' HH:mm").format(Date())
                            ticketFound.usuarioUtilizado = currentUser?.email.toString()
                        }

                        Firebase.firestore.collection("tickets").document(ticketFound.numeroIdentificacion).set(ticketFound)
                    }
                }
            }
        }

        //Aqui mira qué metodo se le va a asignar al boton Confirmar depeniendo del modo
        if(mode == getString(R.string.unlock_account_title)){
            unlockAccountClient?.let { unlockAccount(it) }
        }else if(mode == getString(R.string.title_P2P)){
            reactivateTransaction(transaction!!)
        }else if (mode == "Actualizar cliente"){
            unlockAccountClient?.let { listener!!.onUpdateClient(it) }
        }
    }

    //Aqui mira qué metodo se le va a asignar al boton de ir para atras, depeniendo del modo
    private fun returnToPreviousPage() {
        if(mode == getString(R.string.unlock_account_title)){
            listener!!.onBackToUnlockAccount()
        }else if(mode == getString(R.string.title_P2P)){
            listener?.onBackToReactiveTransaction()
        }else if(mode == "Actualizar cliente"){
            
        }
    }


    private fun unlockAccount(client: Client) {
        if(listChanges[0].compareTo(getString(R.string.result_unlock_account_locked_account)) == 0) {
            client.cuentaBloqueada = false

        }else if(listChanges[0].compareTo(getString(R.string.result_unlock_account_user_locked)) == 0) {
            client.usuarioBloqueado = false

        }else {
            client.reconocimientoFacialBloqueado = false
        }

        listener!!.onUnlockAccount(client)
    }

    private fun reactivateTransaction(transaction: Transaction){
        transaction.Estado = "ACTIVA"
        listener?.onReactiveTransaction(transaction)
    }

    interface Listener{

        //usar este metodo cuando se viene desde la pagina de desbloquear cuenta en el modulo transaccional
        fun onUnlockAccount(client: Client)

        //usar este metodo cuando se viene desde la pagina de actualizar cliente
        fun onUpdateClient(client: Client)

        //usar este metodo cuando se viene desde la pagina de reactivar transaccion P2P
        fun onReactiveTransaction(transaction: Transaction)

        //usar este metodo para devolverse a la pagina anterior cuando se viene desde desbloquear cuenta
        fun onBackToUnlockAccount()

        //usar este metodo para devolverse a la pagina anterior cuando se viene desde actualizar cliente
        fun onBackToUpdateClient()

        //usar este metodo para devolverse a la pagina anterior cuando se viene desde reactivar transaccion
        fun onBackToReactiveTransaction()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TicketFragment()
    }
}