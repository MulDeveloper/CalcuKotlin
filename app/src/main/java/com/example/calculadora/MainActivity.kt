package com.example.calculadora

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.WebViewClient
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    //Vamos a editar nuestra calculadora planteada en la version 1 y vamos a hacer una mas dinamica con array

    var mem=0.0
    var mostrarMemoria = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide(); // hide the title bar
        setContentView(R.layout.activity_main)


        //controlamos orientacion
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            //navegador web
            web.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            web.loadUrl("https://www.google.es/")
        }



    }

    fun cargarUrl(v: View){
        var substring = url.text.toString().substring(0,7)
        if (substring.equals("http://") || substring.equals("https://")){
            web.loadUrl(url.text.toString())
        }
        else{
            web.loadUrl("https://www.google.es/search?q="+url.text.toString())
        }
    }

    fun activarBotones(activar: Boolean){

        suma.isEnabled=activar
        multi.isEnabled=activar
        dividir.isEnabled=activar
        restar.isEnabled=activar
        igual.isEnabled=activar
        memo.isEnabled=activar
        ce.isEnabled=activar
    }


    fun numero(v: View) {
        val botonPulsado = findViewById<Button>(v.id)

        if (botonPulsado!=suma && botonPulsado!=igual && botonPulsado!=restar
            && botonPulsado!=multi && botonPulsado!=dividir && botonPulsado!=memo){

            activarBotones(true)

        }



        if (botonPulsado == igual) {
            operar()
        }
        else{

            resultado.text = resultado.text.toString() + botonPulsado.text.toString()
        }



        if (botonPulsado==ce){
            activarBotones(false)
            resultado.text =""
        }

        if (botonPulsado==memo){
            memoria()
        }



        }

    fun operar(){
        val exp = ExpressionBuilder(resultado.text.toString()).build()
        try {
            // evaluamos la exp
            val res = exp.evaluate()
            resultado.text = res.toString()
        } catch (ex: ArithmeticException) {
            resultado.text = "Error"
        }

    }

    fun memoria(){
        //falta controlar memoria
        if (mostrarMemoria){
            val index = resultado.text.length
            mem = mem + (resultado.text.toString().substring(0,index-2).toDouble())
            resultado.text = mem.toString()
        }
        else{
            val index = resultado.text.length
            mem = resultado.text.toString().substring(0,index-2).toDouble()
            resultado.text = ""
            mostrarMemoria = true
        }

    }


}
