package com.example.inmocasas.services.http


import android.util.Log
import com.beust.klaxon.Klaxon

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.util.*

open class HttpEntity<Entity>(var url:String,var modelo:String ) {
    fun get(id:Int):String{
        var url = "${this.url}${this.modelo}/${id}";
        var returned = ""
        url.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    returned = "${ex}"
                }
                is Result.Success -> {
                    val data = result.get()
                    returned = data
                }
            }

        }
        Thread.sleep(1500)
        Log.i("RETORNADOOOOOO","EEEEES RETORNADOOO despues de 2 segundo ses :${returned}")
        return returned
    }
    fun getPopulated(id:Int,toPopulate:String):String{
        var url = "${this.url}${this.modelo}/${id}?populate=${toPopulate}";
        var returned = ""
        url.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    returned = "${ex}"
                }
                is Result.Success -> {
                    val data = result.get()
                    returned = data
                }
            }

        }
        Thread.sleep(1500)
        Log.i("RETORNADOOOOOO","EEEEES RETORNADOOO despues de 2 segundo ses :${returned}")
        return returned
    }

    fun getByQuery(query:String):String{
        var url = "${this.url}${this.modelo}?${query}";
        var returned = ""
        url.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    returned = "${ex}"
                }
                is Result.Success -> {
                    val data = result.get()
                    returned = data
                }
            }

        }
        Thread.sleep(1500)
        Log.i("RETORNADOOOOOO","EEEEES RETORNADOOO despues de 2 segundo ses :${returned}")
        return returned
    }
}