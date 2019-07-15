package com.example.inmocasas.NavegacionUsuario

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.inmocasas.MainActivity
import com.example.inmocasas.MapsActivity
import com.example.inmocasas.R
import com.example.inmocasas.dto.LastPublicated
import com.example.inmocasas.dto.Publicacion
import com.example.inmocasas.services.http.HttpInmueble

import com.example.inmocasas.services.http.HttpPublicacion
import kotlinx.android.synthetic.main.activity_publicar.*
import java.security.Policy

class PublicarActivity : AppCompatActivity() {
    var url = "http://192.168.56.1:1337/"
    var publicacionesPublicas:List<LastPublicated>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicar)
        img_publicar.setImageResource(R.drawable.c1)
        btn_map.setOnClickListener {
            irAMapsActivity()
        }
        btn_publicar.setOnClickListener {
            publicar()
        }
    }


    fun irAMapsActivity(){
        val intentPropio = Intent(
            this,
            MapsActivity::class.java
        )
        this.startActivityForResult(intentPropio,305)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){

            RESULT_OK ->{
                when(requestCode){
                    305 -> {

                        val longitud = data?.getDoubleExtra("latitud",0.0)
                        val latitud = data?.getDoubleExtra("longitud", 0.0)
                        edit_latitud.apply{setText(latitud.toString())}
                        edit_longitud.apply{setText(longitud.toString())}
                        Log.i("respuesta","Latitud: $latitud Longitud $longitud")


                    }

                }

            }
            Activity.RESULT_CANCELED ->{

                Log.i("respuesta","No escojio nada el mijin!!!")

            }
        }
    }

    fun publicar(){
        var instanceForComanionObject = MainActivity
        var currentUser = instanceForComanionObject.currentUser
        var fkUsuario = currentUser?.id
        var publicacionHttp: HttpPublicacion = HttpPublicacion(url,"Publicacion")
        var inmuebleHttp: HttpInmueble = HttpInmueble(url,"Inmueble")
        var response = publicacionHttp.getByQuery("populate=false&sort=id%20DESC")

        if(response == "[]"){
            Log.i("ERROR","No lasT Publications")
        }
        else{
            var lastPublicated: LastPublicated? = jsonParse(response)

            if (lastPublicated != null){
                Log.i("Pubs","Ultima Publicacion es ${lastPublicated.id}")


                var parametrosPublicacionAEnviar = listOf(
                    "id" to lastPublicated.id + 1,
                    "titulo" to edit_titulo.text.toString(),
                    "precio" to edit_precio.text.toString().toDouble(),
                    "estado" to "pendiente",
                    "descripcion" to edit_descripcion.text.toString(),
                    "fkUsuario" to fkUsuario
                )
                var parametrosInmuebleAEnviar = listOf(
                    "sector" to edit_sector.text.toString(),
                    "callePrincipal" to edit_calle_principal.text.toString(),
                    "calleSecundaria" to edit_calle_secundaria.text.toString(),
                    "numeroPisos" to edit_numero_pisos.text.toString().toInt(),
                    "numeroHabitaciones" to edit_numero_habitaciones.text.toString().toInt(),
                    "numeroBanios" to edit_numero_banios.text.toString().toInt(),
                    "numeroParqueaderos" to edit_numero_parqueaderos.text.toString().toInt(),
                    "antiguedad" to edit_anio.text.toString(),
                    "ciudad" to edit_ciudad.text.toString(),
                    "latitud" to edit_latitud.text.toString().toDouble(),
                    "longitud" to edit_longitud.text.toString().toDouble(),
                    "fkPublicacion" to lastPublicated.id +1
                )
                try{
                    publicacionHttp.post(parametrosPublicacionAEnviar)
                    Toast.makeText(this, "Publicación Exitosa", Toast.LENGTH_SHORT).show()
                }
                catch(e:Exception){
                    Toast.makeText(this, "Error Al Publicar", Toast.LENGTH_SHORT).show()

                }
                Thread.sleep(1000)
                try{
                    inmuebleHttp.post(parametrosInmuebleAEnviar)

                }
                catch(e: Exception){
                    Toast.makeText(this, "Error al enviar el Inmueble", Toast.LENGTH_SHORT).show()

                }







            }
            else{
                Log.i("Error Parse","error Parse de la ultima Publicacion!!!")
            }
        }
    }


    fun jsonParse(data:String): LastPublicated?{
        try{
            val usuarioParseado = Klaxon()
                .parseArray<LastPublicated>(data)

            return usuarioParseado?.component1()

        }
        catch(err: Exception){
            Log.i("Exception","Exception")

        }

        return null
    }



}
