package com.under.superadmin.model

data class Client(
    var cuentaBloqueada: Boolean = false,
    var email: String = "",
    var fechaExpedicionDocumento: String = "",
    var fechaNacimiento: String = "",
    var genero: String = "",
    var listaRestrictiva: String = "",
    var lugarExpedicionDocumento: String = "",
    var numeroCelular: String = "",
    var numeroIdentificacion: String = "",
    var primerApellido: String = "",
    var primerNombre: String = "",
    var reconocimientoFacialBloqueado: Boolean = false,
    var segundoApellido: String = "",
    var segundoNombre: String = "",
    var tipoIdentificacion: String = "",
    var usuarioBloqueado: Boolean = false
)
