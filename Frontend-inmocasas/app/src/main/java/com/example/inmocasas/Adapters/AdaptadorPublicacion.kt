package com.example.inmocasas.Adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.inmocasas.NavegacionUsuario.PublicacionesActivity
import com.example.inmocasas.R
import com.example.inmocasas.dto.Publicacion

class AdaptadorPublicacion(private val listaPublicaciones:List<Publicacion>,
                           private val contexto: PublicacionesActivity,
                           private val recyclerview: androidx.recyclerview.widget.RecyclerView
): androidx.recyclerview.widget.RecyclerView.Adapter<AdaptadorPublicacion.MyViewHolder>() {

    inner class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        var imagen_publicacion: ImageView
        var txt_titulo: TextView
        var txt_fecha: TextView
        var txt_descripcion: TextView
        var txt_precio: TextView
        var idPublicacion: Int = 0


        init {
            imagen_publicacion = view.findViewById(R.id.img_publicacion) as ImageView
            txt_titulo = view.findViewById(R.id.txt_titulo) as TextView
            txt_fecha = view.findViewById(R.id.txt_fecha) as TextView
            txt_descripcion = view.findViewById(R.id.txt_descripcion) as TextView
            txt_precio = view.findViewById(R.id.txt_precio) as TextView


            val layout = view.findViewById(R.id.publicaciones_layout) as RelativeLayout

            layout.setOnClickListener {
                // val mensaje = Mensaje(txt_autor_mensaje.text.toString(),txt_mensaje.text.toString(),txt_hora.text.toString(),id_usuario)
                //irAChatActivity(mensaje)
                // Log.i("layput","Layout presionado con el id ${id_usuario}")  }
                    irAInmuebleActivity(idPublicacion)

            }

        }


    }
    fun irAInmuebleActivity(idPublicacion: Int){
        contexto.irAInmuebleActivity(idPublicacion)

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(p0.context)
            .inflate(
                R.layout.publicaciones_layout,
                p0,
                false
            )

        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {

        return listaPublicaciones.size

    }

    override fun onBindViewHolder(myViewHolder: AdaptadorPublicacion.MyViewHolder, position: Int) {
        val publicaciones = listaPublicaciones[position]
        myViewHolder.txt_titulo.text = publicaciones.titulo
        myViewHolder.txt_descripcion.text = publicaciones.descripcion
        myViewHolder.txt_precio.text = publicaciones.precio.toString()
        myViewHolder.txt_fecha.text = publicaciones.fechaCreacion.toString()
        myViewHolder.imagen_publicacion.setImageResource(R.drawable.c1)
        myViewHolder.idPublicacion = publicaciones.id



    }



}