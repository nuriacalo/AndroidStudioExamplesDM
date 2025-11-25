package com.example.replicarusuarios.data

class DefaultAppContainer : AppContainer {

    private val baseUrlDummyJson = "https://dummyjson.com/"

    private val dummyJsonApiService = DummyJsonApiService.create(baseUrlDummyJson)



}