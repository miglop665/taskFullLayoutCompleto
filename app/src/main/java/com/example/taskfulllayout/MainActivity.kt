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
        setContentView(R.layout.activity_main)
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


        tiempo.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity2::class.java)
            startActivity(intent)
        }

        perfil.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity3::class.java)
            startActivity(intent)
        }

        llamar.setOnClickListener {
            val intent = Intent(this@MainActivity,MainActivity4::class.java)
            startActivity(intent)
        }

        var arrayCanciones = arrayOf(R.raw.barbie_girl,R.raw.call_me_maybe,R.raw.avicii_wake_me_up)
        mp= MediaPlayer.create(this, arrayCanciones.random())
        listamusica.setOnClickListener {
            if(mp.isPlaying){
                mp.stop()
            }else{
                var cancion = arrayCanciones.random()
                mp= MediaPlayer.create(this, cancion)
                mp.start()
                txv1.text= resources.getResourceEntryName(cancion)
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