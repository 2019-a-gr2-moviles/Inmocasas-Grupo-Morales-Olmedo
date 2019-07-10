package com.example.inmocasas.dto

class Inmueble (
    var id: Int = 0,
    var createdAt: Long = 0,
    var updatedAt: Long = 0,
    var sector: String = "",
    var callePrincipal: String ="",
    var calleSecundaria: String = "",
    var numeroPisos: Int = 0,
    var numeroHabitaciones:Int = 0,
    var numeroBanios:Int = 0,
    var numeroParqueaderos:Int = 0,
    var antiguedad: String = "",
    var ciudad: String = "",
    var fkPublicacion: Int = 0
){

}