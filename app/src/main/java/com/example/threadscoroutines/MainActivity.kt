package com.example.threadscoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.threadscoroutines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var pararThread = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnTela.setOnClickListener {
            startActivity(
                Intent(this, SegundaActivity::class.java)
            )
        }

        binding.btnParar.setOnClickListener {
            pararThread = true
            binding.btnIniciar.text = "Reiniciar execução"
            binding.btnIniciar.isEnabled = true
        }

        binding.btnIniciar.setOnClickListener {

//            MinhaClasse().start()
            Thread(MinhaRunnable()).start()
            /*Thread{
                repeat(30){indice ->
                    Log.i("info_thread","MinhaClasse: $indice T: ${Thread.currentThread().name}")
                    runOnUiThread{
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                        binding.btnIniciar.isEnabled = false
                        if(indice == 29){
                            binding.btnIniciar.text = "Reiniciar execução"
                            binding.btnIniciar.isEnabled = true
                        }
                    }
                    Thread.sleep(1000)
                }
            }.start()*/

        }

    }

    inner class MinhaRunnable : Runnable{
        override fun run() {

            repeat(30){indice ->
                if (pararThread){
                    pararThread = false
                    return
                }

                Log.i("info_thread","MinhaClasse: $indice T: ${Thread.currentThread().name}")
                runOnUiThread{
                    binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if(indice == 29){
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                Thread.sleep(1000)
            }

        }
    }



    inner class MinhaClasse : Thread(){
        override fun run() {
            super.run()

            repeat(30){indice ->
                Log.i("info_thread","MinhaClasse: $indice T: ${currentThread().name}")
                runOnUiThread{
                    binding.btnIniciar.text = "Executando: $indice T: ${currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if(indice == 29){
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                Thread.sleep(1000)
            }

        }
    }

}