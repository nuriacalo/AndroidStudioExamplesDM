package com.example.gestionproductos

import android.app.Application
import com.example.gestionproductos.data.AppContainer
import com.example.gestionproductos.data.DefaultAppContainer

class GestionProductosApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()

    }

}