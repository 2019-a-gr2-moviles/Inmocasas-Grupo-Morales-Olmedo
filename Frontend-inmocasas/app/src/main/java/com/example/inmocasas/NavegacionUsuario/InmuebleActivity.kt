package com.example.inmocasas.NavegacionUsuario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.inmocasas.R
import com.example.inmocasas.dto.Inmueble
import com.example.inmocasas.dto.Publicacion
import com.example.inmocasas.services.http.HttpPublicacion
import kotlinx.android.synthetic.main.activity_inmueble.*

class InmuebleActivity : AppCompatActivity() {
    var url = "http://192.168.56.1:1337/"
    var publicacion:Publicacion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inmueble)
        val idPublicacionoObtenida:Int= this.intent.getIntExtra("idPublicacion",1)
        btn_izq.setOnClickListener {
            img_inmueble.setImageResource(R.drawable.c2)
        }
        btn_der.setOnClickListener {
            img_inmueble.setImageResource(R.drawable.c1)
        }

        var publicacionObtenida = HttpPublicacion(url,"Publicacion")
        var response = publicacionObtenida.getPopulated(idPublicacionoObtenida,"inmueblesDePublicacion")
        if(response == null){
            Log.i("ERROR","ERRORRRR")
            Toast.makeText(this, "No existen Publicaciones con el id enciado ${idPublicacionoObtenida}", Toast.LENGTH_SHORT).show()
        }
        else{
            var publicaionParseada:Publicacion? = jsonParse(response)

            if (publicaionParseada != null){
                publicacion = publicaionParseada
                Log.i("Pubs","PUBLICACION de id es inmueble ${publicacion}")
                llenarDatos(publicacion!!.inmueblesDePublicacion[0])
            }
            else{
                Log.i("Error Parse","errorP Parseee Inmuebles!!!")
            }
        }

    }

    fun jsonParse(data:String): Publicacion?{
        try{
            val publicacionesParseado = Klaxon()
                .parse<Publicacion>(data)

            return publicacionesParseado

        }
        catch(err: Exception){
            Log.i("Exception","Exception")

        }

        return null
    }

    fun llenarDatos(inmueble:Inmueble){
        this.txt_sector.text = inmueble.sector
        this.txt_calle_p.text = inmueble.callePrincipal
        this.txt_calle_s.text = inmueble.calleSecundaria
        this.txt_numero_pisos.text = inmueble.numeroPisos.toString()
        this.txt_numero_habitaciones.text = inmueble.numeroHabitaciones.toString()
        this.txt_numero_banios.text = inmueble.numeroBanios.toString()
        this.txt_numero_parqueaderos.text = inmueble.numeroParqueaderos.toString()
        this.txt_anio.text = inmueble.antiguedad
        this.txt_ciudad.text = inmueble.ciudad
        this.img_inmueble.setImageResource(R.drawable.c2)

    }





}
