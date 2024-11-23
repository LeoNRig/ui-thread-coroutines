package com.example.threadscoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.threadscoroutines.api.EnderecoApi
import com.example.threadscoroutines.api.PostagemApi
import com.example.threadscoroutines.api.RetrofitHelper
import com.example.threadscoroutines.databinding.ActivityMainBinding
import com.example.threadscoroutines.model.Comentario
import com.example.threadscoroutines.model.Endereco
import com.example.threadscoroutines.model.Foto
import com.example.threadscoroutines.model.Postagem
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.Response
import retrofit2.create
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val retrofit by lazy{
        RetrofitHelper.retrofit
    }

    private var pararThread = false
    private var job: Job? = null

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.btnTela.setOnClickListener {
            startActivity(
                Intent(this, SegundaActivity::class.java)
            )
            finish()
        }

        binding.btnParar.setOnClickListener {
//            pararThread = true
            job?.cancel()
            binding.btnIniciar.text = "Reiniciar execução"
            binding.btnIniciar.isEnabled = true
        }

        binding.btnIniciar.setOnClickListener {
/*//            MainScope().launch{
//            CoroutineScope(Dispatchers.IO).launch{
//            GlobalScope.launch {
//            lifecycleScope.launch {
//                repeat(15){ indice ->
//                    binding.btnIniciar.text="Executando $indice"
//
//                    Log.i("info_coroutine","Executando: $indice T: ${Thread.currentThread().name}")
//                    delay(1000L)
//                }
//            }

           /* job = CoroutineScope(Dispatchers.IO).launch {
//                binding.btnIniciar.text = "Executou"
                *//*repeat(15){indice ->
                    Log.i("info_coroutine","Executando: $indice T: ${Thread.currentThread().name}")

                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                    }
                    delay(1000)
                }*//*
//                recuperarUsuarioLogado()
                *//*withTimeout(7000L){
                    executar()
                }*//*
                val tempo = measureTimeMillis {

                    val resultado1 = async {tarefa1()}

                    val resultado2 = async {tarefa2()}

                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "${resultado1.await()}"
                        binding.btnParar.text = "${resultado2.await()}"
                    }

                    Log.i("info_coroutine","resultado1: ${resultado1.await()} ")
                    Log.i("info_coroutine","resultado1: ${resultado2.await()} ")
                }
                Log.i("info_coroutine","Tempo: $tempo")
            }*/


//            MinhaClasse().start()
//            Thread(MinhaRunnable()).start()
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
*/
            CoroutineScope(Dispatchers.IO).launch {
//                recuperarEndereco()
//                recuperarPostagens()
//                recuperarPostagemUnica()
//                recuperarSalvarPostagem()
//                recuperarComentariosPostagem()
//                salvarPostagemFormEncoded()
//                atualizarPostagem()
//                deletarPostagem()
                recuperarFotoUnica()
            }

        }

    }
    private suspend fun recuperarFotoUnica() {
        var retorno: Response<Foto>? = null

        try {
            val postagemApi = retrofit.create(PostagemApi::class.java)
            retorno = postagemApi.recuperarFoto(5)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){
                val foto = retorno.body()
                val resultado = "[${retorno.code()}] ${foto?.id},${foto?.url}"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                    Picasso.get()
                        .load(R.drawable.picasso)
                        .resize(100, 200)
//                        .centerInside()
//                        .centerCrop()
                        .placeholder(R.drawable.carregando)
//                        .error(R.drawable.ic_launcher_background)
                        .into(binding.imgFoto)
                }

                Log.i("info_jsonplace",resultado)
            }else{
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun deletarPostagem() {

        var retorno: Response<Unit>? = null

        try {
            val postagemApi = retrofit.create(PostagemApi::class.java)
            retorno = postagemApi.deletarPostagem(1)
            /*retorno = postagemApi.atualizarPostagemPut(
                1,
                Postagem("Corpo da postagem",-1,"Titulo",1090)
            )*/
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){

                var resultado = "[${retorno.code()}] Removido com Sucesso!"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }
            }else{
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun atualizarPostagem() {

        var retorno: Response<Postagem>? = null

        try {
            val postagemApi = retrofit.create(PostagemApi::class.java)
            retorno = postagemApi.atualizarPostagemPatch(
                1,
                Postagem("Corpo da postagem",-1,"Titulo",1090)
            )
            /*retorno = postagemApi.atualizarPostagemPut(
                1,
                Postagem("Corpo da postagem",-1,"Titulo",1090)
            )*/
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){
                val postagem = retorno.body()

                val id = postagem?.id
                val titulo = postagem?.title
                val userId = postagem?.userId
                val corpo = postagem?.body

                var resultado = "[${retorno.code()}] $id - $titulo - $userId - $corpo"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }
            }else{
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun salvarPostagemFormEncoded() {

        var retorno: Response<Postagem>? = null

        val postagem = Postagem(
            "Corpo da postagem",
            -1,
            "Titulo da postagem",
            1090
        )

        try {
            val postagemApi = retrofit.create(PostagemApi::class.java)
//            retorno = postagemApi.salvarPostagem(postagem)
            retorno = postagemApi.salvarPostagemFormEncoded(
                1090,
                -1,
                "Titulo da postagem",
                "Body"
            )
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){
                val postagem = retorno.body()

                val id = postagem?.id
                val titulo = postagem?.title
                val userId = postagem?.userId
                var resultado = "[${retorno.code()}] $id - $titulo - $userId"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }
            }else{
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun recuperarSalvarPostagem() {

        var retorno: Response<Postagem>? = null

        val postagem = Postagem(
            "Corpo da postagem",
            -1,
            "Titulo da postagem",
            1090
        )

        try {
            val postagemApi = retrofit.create(PostagemApi::class.java)
            retorno = postagemApi.salvarPostagem(postagem)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){
                val postagem = retorno.body()

                val id = postagem?.id
                val titulo = postagem?.title
                val userId = postagem?.userId
                var resultado = "[${retorno.code()}] $id - $titulo - $userId"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }
            }else{
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun recuperarComentariosPostagem() {

        var retorno: Response<List<Comentario>>? = null

        try {
            val postagemApi = retrofit.create(PostagemApi::class.java)
            retorno = postagemApi.recuperarComentariosPostagem(1)
            retorno = postagemApi.recuperarComentariosPostagemQuery(1)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){
                val listaComentarios = retorno.body()
                var resultado = ""
                listaComentarios?.forEach {comentario ->
                    val idComentario = comentario.id
                    val email = comentario.email
                    val comentarioResultado = "$idComentario - $email \n"
                    resultado += comentarioResultado
                }
                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }
            }
        }
    }

    private suspend fun recuperarPostagemUnica() {
        var retorno: Response<Postagem>? = null

        try {
            val postagemApi = retrofit.create(PostagemApi::class.java)
            retorno = postagemApi.recuperarPostagemUnica(1)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){
                val postagem = retorno.body()
                val resultado = "${postagem?.id},${postagem?.title}"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }

                Log.i("info_jsonplace",resultado)
            }
        }
    }

    private suspend fun recuperarPostagens() {
        var retorno: Response<List<Postagem>>? = null

        try {
            val postagemApi = retrofit.create(PostagemApi::class.java)
            retorno = postagemApi.recuperarPostagens()
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){
                val listaPostagens = retorno.body()
                listaPostagens?.forEach {postagem ->
                    val id = postagem.id
                    val title = postagem.title
                    Log.i("info_jsonplace","$id, $title")
                }
            }
        }
    }

    private suspend fun recuperarEndereco(){
        var retorno: Response<Endereco>? = null
        val cepDigitado = "01001000"

        try {
            val enderecoApi = retrofit.create(EnderecoApi::class.java)
            enderecoApi.recuperarEndereco(cepDigitado)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_endereco","Erro ao Recuperar")
        }

        if(retorno != null){
            if(retorno.isSuccessful){
                val endereco = retorno.body()
                val rua = endereco?.logradouro
                val cidade = endereco?.localidade
                Log.i("info_endereco","Endereco: $rua, $cidade")
            }
        }

    }

    private suspend fun tarefa1(): String{
        repeat(5){indice ->
            Log.i("info_coroutine","tarefa1: $indice T: ${Thread.currentThread().name}")
            delay(1000L)
        }
        return "Executou tarefa 1"
    }

    private suspend fun tarefa2(): String{
        repeat(3){indice ->
            Log.i("info_coroutine","tarefa2: $indice T: ${Thread.currentThread().name}")
            delay(1000L)
        }
        return "Executou tarefa 2"
    }
    private suspend fun executar(){
        repeat(15){indice ->
            Log.i("info_coroutine","Executando: $indice T: ${Thread.currentThread().name}")

            withContext(Dispatchers.Main){
                binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                binding.btnIniciar.isEnabled = false
            }
            delay(1000L)
        }
    }

    private suspend fun dadosUsuarios(){
        val usuario = recuperarUsuarioLogado()
        Log.i("info_coroutine","Usuario: ${usuario.nome} T: ${Thread.currentThread().name}")
        val postagem = recuperarPostagensPeloId(usuario.id)
        Log.i("info_coroutine","postagens: ${postagem.size} T: ${Thread.currentThread().name}")
    }
    private suspend fun recuperarPostagensPeloId(idUsuario: Int): List<String>{
        delay(2000)
        return listOf(
            "Viagem",
            "Estudando",
            "Jantando"
        )
    }
    private suspend fun recuperarUsuarioLogado(): Usuario{

        delay(2000)
        return Usuario(1020,"Leonardo Rigonini")

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