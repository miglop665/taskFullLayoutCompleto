package com.example.taskfulllayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity3 : AppCompatActivity() {
    //Declaramos una variable de tipo Imagebutton
    private lateinit var volver: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //asignamos el MainActivity3.kt al activity_main3.xml correspondiente
        setContentView(R.layout.activity_main3)

        //guardamos la referencia a los objetos del activity_main2.xml en la variable previamente declarada
        volver=findViewById(R.id.botonVolver)

        //Al pulsar el icono de volver nos manda a la MainActivity
        volver.setOnClickListener {
            val intent = Intent(this@MainActivity3,MainActivity::class.java)
            startActivity(intent)
        }
    }
}