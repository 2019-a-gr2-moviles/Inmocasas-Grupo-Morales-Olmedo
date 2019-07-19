package com.example.inmocasas.NavegacionUsuario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.inmocasas.Adapters.AdaptadorEliminarPublicacion
import com.example.inmocasas.MainActivity
import com.example.inmocasas.R
import com.example.inmocasas.dto.Publicacion
import com.example.inmocasas.services.http.GlobalsVariables
import com.example.inmocasas.services.http.HttpPublicacion
import kotlinx.android.synthetic.main.activity_eliminar_publicacion.*

class EliminarPublicacionActivity : AppCompatActivity() {
    var globals = GlobalsVariables
    var url = globals.url
    var publicacionesPublicas:List<Publicacion>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_publicacion)
        crear()
        btn_Actualizar.setOnClickListener {
            crear()
        }

    }
    fun crear(){
        var main=MainActivity
        var publicacionHttp: HttpPublicacion = HttpPublicacion(url,"Publicacion")
        var response = publicacionHttp.getByQuery("populate=inmueblesDePublicacion&&fkUsuario=${main.currentUser!!.id}")
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
        actividad: EliminarPublicacionActivity,
        recycler_view: androidx.recyclerview.widget.RecyclerView
    ) {
        val adaptadorPublicacion = AdaptadorEliminarPublicacion(
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
    fun delete(id: Int){
        var publicacionHttp: HttpPublicacion = HttpPublicacion(url,"Publicacion")

        try{
            publicacionHttp.delete(id)

            Thread.sleep(1000)
            Toast.makeText(this, "Eliminación Exitosa", Toast.LENGTH_SHORT).show()
            crear()
        }
        catch(e:Exception){
            Toast.makeText(this, "Error Al Eliminar", Toast.LENGTH_SHORT).show()

        }


    }
}
