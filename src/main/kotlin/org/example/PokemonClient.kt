package org.example

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class PokemonClient {
    private val httpClient = HttpClient.newHttpClient()
    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun getPokemonInfo(name: String): PokemonInfo {
        val request = HttpRequest.newBuilder(URI(POKEMON_API + name.lowercase())).GET().build()
        return httpClient
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body()
            .let { objectMapper.readValue(it) }
    }
}

private const val POKEMON_API = "https://pokeapi.co/api/v2/pokemon/"

data class PokemonInfo(val weight: Int, val types: List<PokemonTypes>)
data class PokemonTypes(val type: TypeInfo)
data class TypeInfo(val name: String)
