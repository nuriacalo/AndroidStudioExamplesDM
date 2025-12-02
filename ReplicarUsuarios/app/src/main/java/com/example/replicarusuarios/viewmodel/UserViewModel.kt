package com.example.replicarusuarios.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.replicarusuarios.ReplicarUsuariosAplication
import com.example.replicarusuarios.data.DummyJsonRepository
import com.example.replicarusuarios.data.MockApiRepository
import com.example.replicarusuarios.data.UserResponse
import com.example.replicarusuarios.model.ApiQueryState
import com.example.replicarusuarios.model.UserUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UsersViewModel(
    private val dummyJsonRepository: DummyJsonRepository,
    private val mockApiRepository: MockApiRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(UserUiState())
    val uiState: State<UserUiState> = _uiState


    fun inicializarUsuarios() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(
                apiQueryState = ApiQueryState.LOADING,
                message = "Cargando usuarios..."
            )
            try {
                val response = mockApiRepository.getUsers()
                _uiState.value = _uiState.value.copy(
                    apiQueryState = ApiQueryState.SUCCESS_GET,
                    message = "${response.size} Usuarios cargados"
                )

                var  borrados = 0;
                for (user in response) {
                    mockApiRepository.deleteUser(user.id)
                    delay(500)
                    borrados++
                    _uiState.value = _uiState.value.copy(
                        apiQueryState = ApiQueryState.SUCCESS_DELETE,
                        message = "Usuario ${user.id} eliminado. Total eliminados: $borrados"
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    apiQueryState = ApiQueryState.ERROR,
                    message = "Error cargando usuarios: ${e.message}"
                )
            }
        }
    }

    fun replicarUsuarios() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(
                apiQueryState = ApiQueryState.LOADING)
            try {
            val response: UserResponse = dummyJsonRepository.getUsers()
                var replicatedCount = 0
                for (user in response.users) {
                    mockApiRepository.createUser(user)
                    delay(500)
                    replicatedCount++
                    _uiState.value = _uiState.value.copy(
                        apiQueryState = ApiQueryState.SUCCESS_POST,
                        message = "Usuario ${user.id} replicado."
                    )
                }
                _uiState.value = _uiState.value.copy(
                    apiQueryState = ApiQueryState.SUCCESS_FINISH,
                    message = "Replicación completada: ${response.users.size} usuarios replicados."
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    apiQueryState = ApiQueryState.ERROR,
                    message = "Error replicando usuarios: ${e.message}"
                )
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ReplicarUsuariosAplication)
                val dummy = application.container.dummyJsonRepository
                val mock = application.container.mockApiRepository
                UsersViewModel(dummyJsonRepository = dummy, mockApiRepository = mock)
            }
        }
    }

}

