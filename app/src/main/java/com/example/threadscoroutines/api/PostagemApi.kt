package com.example.threadscoroutines.api

import com.example.threadscoroutines.model.Comentario
import com.example.threadscoroutines.model.Postagem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostagemApi {
//    https://jsonplaceholder.typicode.com/posts

    @GET("posts")
    suspend fun recuperarPostagens(): Response<List<Postagem>>

    @GET("posts/{id}")
    suspend fun recuperarPostagemUnica(
        @Path("id") id: Int
    ): Response<Postagem>

    @GET("posts/{id}/comments")
    suspend fun recuperarComentariosPostagem(
        @Path("id") id: Int
    ): Response<List<Comentario>>


}