package com.example.inmocasas.dto

import java.util.*
import kotlin.collections.ArrayList

class Usuario(
    var id: Int,
    var createdAt: Long,
    var updatedAt: Long,
    var nombre: String,
    var apellido:String,
    var telefono:String,
    var nombreUsuario:String,
    var email:String,
    var contrasenia:String,
    var rolesDeUsuario:ArrayList<Rol>,
    var publicacionesDeUsuario:ArrayList<Publicacion>

  //  var usuariosDeEmpresa: ArrayList<Usuario>
) {
    var fechaCreacion: Date
    var fechaActualizacion: Date

    init {
        fechaCreacion = Date(createdAt)
        fechaActualizacion = Date(updatedAt)
    }


}