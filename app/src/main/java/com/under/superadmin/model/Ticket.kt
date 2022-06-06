package com.under.superadmin.model

data class Ticket(
    var activo: Boolean = true,
    var fechaCerrado: String = "",
    var numeroIdentificacion: String = "",
    var usuarioUtilizado: String = ""
)
