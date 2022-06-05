package com.under.superadmin.model

data class Client(
    var cuentaBloqueada: Boolean,
    var email: String = "",
    var fechaExpedicionDocumento: String = "",
    var fechaNacimiento: String = "",
    var genero: String = "",
    var lugarExpedicionDocumento: String = "",
    var numeroCelular: String = "",
    var numeroIdentificacion: String = "",
    var primerApellido: String = "",
    var primerNombre: String = "",
    var reconocimientoFacialBloqueado: Boolean,
    var segundoApellido: String = "",
    var segundoNombre: String = "",
    var tipoIdentificacion: String = "",
    var usuarioBloqueado: Boolean
)
