package com.example.inmocasas.dto

import java.lang.reflect.Array
import java.util.*
import kotlin.collections.ArrayList

class Publicacion(
    var id: Int,
    var createdAt: Long,
    var updatedAt: Long,
    var titulo: String,
    var precio:Double,
    var estado:String,
    var descripcion:String,
    var fkUsuario:Int,
    var inmueblesDePublicacion:ArrayList<Inmueble> = arrayListOf(Inmueble())
) {
    var fechaCreacion: Date
    var fechaActualizacion: Date

    init {
        fechaCreacion = Date(createdAt)
        fechaActualizacion = Date(updatedAt)

    }





}