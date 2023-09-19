package com.jetbrains.handson.kmm.shared.network

import com.example.kmmapp.model.RocketLaunch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SpaceXApi {
    private val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllLaunches(): List<RocketLaunch> {
        val httpResponse: HttpResponse = httpClient.get("https://api.spacexdata.com/v5/launches")
        val listOfRocketLaunches: List<RocketLaunch> = httpResponse.body<List<RocketLaunch>>()
        println("listOfRocketLaunches = $listOfRocketLaunches")
        return listOfRocketLaunches
    }
}