package com.example.inmocasas.NavegacionUsuario

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.inmocasas.Adapters.AdaptadorPublicacion
import com.example.inmocasas.Adapters.AdaptadorVozPublications
import com.example.inmocasas.R
import com.example.inmocasas.dto.Publicacion
import com.example.inmocasas.services.http.GlobalsVariables
import com.example.inmocasas.services.http.HttpPublicacion
import kotlinx.android.synthetic.main.activity_administrador_main.*
import kotlinx.android.synthetic.main.activity_publicaciones.*
import kotlinx.android.synthetic.main.activity_voz.*
import java.util.*

class VozActivity : AppCompatActivity() {
    var globals = GlobalsVariables
    var url = globals.url
    var publicacionesPublicas:List<Publicacion>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voz)
        voiceBtn.setOnClickListener {
            speak()


        }
    }

    private fun speak(){
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hola, diga algo")
       try{
           startActivityForResult(mIntent,100)
       }
       catch(e: Exception){
           Toast.makeText(this,"\n" +
                   "Su dispositivo no es compatible con la entrada de voz", Toast.LENGTH_SHORT ).show()

       }


    }
    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            100 -> if (resultCode == Activity.RESULT_OK && data !=null){
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                textTv.text = result[0]
                consultar()


            }
        }
    }

    fun consultar(){
        if(textTv.text.toString()!=""){
            var publicacionHttp: HttpPublicacion = HttpPublicacion(url,"Publicacion")
            var response = publicacionHttp.getByQuery("estado=publico&populate=inmueblesDePublicacion&where={\"titulo\":{\"contains\":\"${textTv.text.toString().toLowerCase()}\"},\"estado\":{\"contains\":\"publico\"}}")
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
                    iniciarRecylerView(publicacionesPublicas!!,this,reciclerView_busqueda)

                }
                else{
                    Log.i("Error Parse","errorP Parseee Publicaciones!!!")
                }
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
        actividad: VozActivity,
        recycler_view: androidx.recyclerview.widget.RecyclerView
    ) {
        val adaptadorPublicacion = AdaptadorVozPublications(
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
}
