package com.under.superadmin.model

data class User(
    var apellido1 : String = "",
    var apellido2 : String = "",
    var nombre : String = "",
    var colaborador : String = "",
    var claveAuto : Boolean = false,
    var email : String = "",
    var estado : String = "",
    var numeroCelular : String = "",
    var numeroTelefono : String = "",
    var numeroidentificacion : String = "",
    var tipoIdetificacion : String = "",
    var rol : String = "",
    var claveAutomatica : String = ""
)
