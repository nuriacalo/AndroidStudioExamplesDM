package com.example.replicarusuarios


import android.app.Application
import com.example.replicarusuarios.data.AppContainer
import com.example.replicarusuarios.data.DefaultAppContainer

class ReplicarUsuariosAplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}