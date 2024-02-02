package com.example.taskfulllayout

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView

import android.content.ActivityNotFoundException
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import java.util.Locale



class MainActivity : AppCompatActivity() {
    //Declaramos todas las variables empleadas
    private lateinit var tiempo: Button
    private lateinit var perfil: ImageButton
    private lateinit var listamusica: CardView
    private lateinit var llamar: CardView
    private lateinit var mp: MediaPlayer
    private lateinit var txv1: TextView
    private lateinit var txv2: TextView

    private val REQ_CODE = 100
    private lateinit var bottom_navigation: View
    private lateinit var desplegado: View
    private lateinit var btnDesplegable: ImageButton
    private lateinit var btnDesplegable2: ImageButton
    private lateinit var botonNavigate: CardView
    private lateinit var btnMicro: Button
    private lateinit var botonGoogle: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //asignamos el MainActivity.kt al activity_main.xml correspondiente
        setContentView(R.layout.activity_main)

        //guardamos las referencias a los objetos del activity_main.xml en las variables previamente declaradas
        tiempo=findViewById(R.id.botonTiempo)
        perfil=findViewById(R.id.botonImagen)
        listamusica=findViewById(R.id.CVbotonPlay)
        llamar=findViewById(R.id.CVbotonCall)
        txv1=findViewById(R.id.textView6)
        txv2=findViewById(R.id.textView7)

        btnMicro=findViewById(R.id.btnMicro)
        botonGoogle=findViewById(R.id.botonGoogle)
        bottom_navigation=findViewById(R.id.bottom_navigation)
        desplegado=findViewById(R.id.desplegado)
        btnDesplegable=findViewById(R.id.btnDesplegable)
        btnDesplegable2=findViewById(R.id.btnDesplegable2)
        botonNavigate=findViewById(R.id.CVbotonNavigate)


        //Al pulsar el icono del tiempo nos manda a la 2 actividad
        tiempo.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity2::class.java)
            startActivity(intent)
        }

        //Al pulsar el icono del perfil nos manda a la 3 actividad
        perfil.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity3::class.java)
            startActivity(intent)
        }

        //Al pulsar el icono del telefono nos manda a la 4 actividad
        llamar.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity4::class.java)
            startActivity(intent)
        }

        //Declaramos una variable con un array con las referencias a todos los recursos de audio de la carpeta raw
        var arrayCanciones = arrayOf(R.raw.barbie_girl,R.raw.call_me_maybe,R.raw.avicii_wake_me_up)
        mp= MediaPlayer.create(this, arrayCanciones.random())
        listamusica.setOnClickListener {
            //si esta sonando ya se para
            if(mp.isPlaying){
                mp.stop()
            }else{
                //si esta parada o no esta sonando saca otro al azar del array y empieza a sonar
                var cancion = arrayCanciones.random()
                mp= MediaPlayer.create(this, cancion)
                mp.start()
                //guarda el nombre del recurso en el textview1
                txv1.text= resources.getResourceEntryName(cancion)
                //guarda el texto de la duracion en milisegundos (convertida a minutos al dividir entre 60000) del recurso en el textview2
                txv2.text=(mp.duration/60000).toString()+" mins"
            }
        }


        //UTILIDAD DEL BOTON NAVIGATE
        botonNavigate.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity5::class.java)
            startActivity(intent)
        }

        //UTILIDAD DEL BOTON DEL MICRO
        btnMicro.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable usted")
            try {
                startActivityForResult(intent, REQ_CODE)
            } catch (a: ActivityNotFoundException) {
                //por si no tuvieramos opcion de micro en nuestro dispositivo
                Toast.makeText(applicationContext, "Repite de nuevo", Toast.LENGTH_SHORT).show()
            }

        }

        //UTILIDAD DEL BOTON  DE GOOGLE
        botonGoogle.setOnClickListener {

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable usted")
            try {
                startActivityForResult(intent, REQ_CODE)
            } catch (a: ActivityNotFoundException) {
                //por si no tuvieramos opcion de micro en nuestro dispositivo
                Toast.makeText(applicationContext, "Repite de nuevo", Toast.LENGTH_SHORT).show()
            }

        }

        //UTILIDAD DEL DESPLEGABLE
        btnDesplegable.setOnClickListener {
            if (bottom_navigation.visibility == View.VISIBLE) { bottom_navigation.visibility = View.GONE }
            if (desplegado.visibility == View.GONE) { desplegado.visibility = View.VISIBLE }

        }

        //UTILIDAD DEL DESPLEGABLE 2
        btnDesplegable2.setOnClickListener {
            if (bottom_navigation.visibility == View.GONE) { bottom_navigation.visibility = View.VISIBLE }
            if (desplegado.visibility == View.VISIBLE) { desplegado.visibility = View.GONE }

        }

    }

    //FUNCIONALIDAD DEL SPEECH TO TEXT
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE && resultCode == RESULT_OK){

            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (results != null && results.isNotEmpty()) {
                val spokenText = results[0].toLowerCase()

                // Verificar si la frase contiene "actividad 1"
                if (spokenText.contains("tiempo")) {
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                } else  if (spokenText.contains("musica")) {
                    val intent = Intent(this, listamusica::class.java)
                    startActivity(intent)
                } else
                    if (spokenText.contains("musica")) {
                        val intent = Intent(this, listamusica::class.java)
                        startActivity(intent) }
                    else {
                        Toast.makeText(applicationContext, "Acción no reconocida", Toast.LENGTH_SHORT).show()
                    }
            }


        }
    }
}