package com.example.replicarusuarios.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import okhttp3.Call

class UserViewModel {
    private val dummyJsonRepository =


        fun inicializarUsuarios() {
            viewModelScope.launch {
                val usersFromApi = dummyJsonRepository.getUsers()
                _users.postValue(usersFromApi.users)
            }
        }


            companion object {
                val Factory: ViewModelProvider.Factory = viewModelFactory {

                }
            }
}