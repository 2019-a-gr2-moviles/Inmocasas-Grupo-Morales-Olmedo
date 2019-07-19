package com.example.inmocasas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.inmocasas.Adapters.AdaptadorPublicacion
import com.example.inmocasas.Adapters.AdaptadorPublicacionAdministrador
import com.example.inmocasas.NavegacionUsuario.InmuebleActivity
import com.example.inmocasas.NavegacionUsuario.PublicacionesActivity
import com.example.inmocasas.dto.Publicacion
import com.example.inmocasas.services.http.GlobalsVariables
import com.example.inmocasas.services.http.HttpPublicacion
import kotlinx.android.synthetic.main.activity_administrador_main.*
import kotlinx.android.synthetic.main.activity_publicaciones.*

class AdministradorMainActivity : AppCompatActivity() {
    var globals = GlobalsVariables
    var url = globals.url
    var publicacionesPublicas:List<Publicacion>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador_main)
        crear()
        btn_Actualizar.setOnClickListener {
            crear()
        }

    }
    fun crear(){

        var publicacionHttp: HttpPublicacion = HttpPublicacion(url,"Publicacion")
        var response = publicacionHttp.getByQuery("estado=pendiente&populate=inmueblesDePublicacion")
        Log.i("login","las publicaciones son  ${response}")
        if(response == "[]"){
            Log.i("ERROR","ERRORRRR")
            Toast.makeText(this, "No existen Publicaciones", Toast.LENGTH_SHORT).show()
        }
        else{
            var publicaiones:List<Publicacion>? = jsonParse(response)

            if (publicaiones != null){
                publicacionesPublicas = publicaiones
                Log.i("Pubs","PUBLICACIONES SON ${publicacionesPublicas}")
                iniciarRecylerView(publicacionesPublicas!!,this,recycler_view_Usuario)

            }
            else{
                Log.i("Error Parse","errorP Parseee Publicaciones!!!")
            }
        }
    }



    fun jsonParse(data:String): List<Publicacion>?{
        try{
            val publicacionesParseado = Klaxon()
                .parseArray<Publicacion>(data)

            return publicacionesParseado

        }
        catch(err: Exception){
            Log.i("Exception","Exception")

        }

        return null
    }

    fun iniciarRecylerView(
        lista: List<Publicacion>,
        actividad:  AdministradorMainActivity,
        recycler_view: androidx.recyclerview.widget.RecyclerView
    ) {
        val adaptadorPublicacion = AdaptadorPublicacionAdministrador(
            lista,
            actividad,
            recycler_view
        )
        recycler_view.adapter = adaptadorPublicacion
        recycler_view.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)

        adaptadorPublicacion.notifyDataSetChanged()
    }

    fun irAInmuebleActivity(idPublicacion: Int){
        val intentExplicito = Intent(
            this,
            InmuebleActivity::class.java
        )
        intentExplicito.putExtra("idPublicacion",idPublicacion)
        startActivity(intentExplicito)

    }
    fun hacerPublico(id: Int){
        var publicacionHttp:HttpPublicacion = HttpPublicacion(url,"Publicacion")

        var parametrosPublicacionAEnviar = listOf(
            "estado" to "publico"
        )
        try{
            publicacionHttp.patch(parametrosPublicacionAEnviar,id)
            Thread.sleep(1000)
            Toast.makeText(this, "Acción Publicación Exitosa", Toast.LENGTH_SHORT).show()
            crear()
        }
        catch(e:Exception){
            Toast.makeText(this, "Error Al Hacer Publico", Toast.LENGTH_SHORT).show()

        }


    }
}
