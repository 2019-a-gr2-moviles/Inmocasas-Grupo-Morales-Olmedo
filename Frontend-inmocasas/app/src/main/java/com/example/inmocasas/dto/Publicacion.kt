package com.example.inmocasas.dto

import java.lang.reflect.Array
import java.util.*
import kotlin.collections.ArrayList

class Publicacion(
    var id: Int = 0,
    var createdAt: Long = 0,
    var updatedAt: Long = 0,
    var titulo: String = "",
    var precio:Double = 0.0,
    var estado:String = "",
    var descripcion:String = "",
    var fkUsuario:Int = 0,
    var inmueblesDePublicacion:ArrayList<Inmueble> = arrayListOf(Inmueble())
) {
    var fechaCreacion: Date
    var fechaActualizacion: Date

    init {
        fechaCreacion = Date(createdAt)
        fechaActualizacion = Date(updatedAt)

    }






}