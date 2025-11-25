package com.example.replicarusuarios.model


enum class ApiQueryState {
    LOADING,
    SUCCESS_GET,
    SUCCESS_POST,
    SUCCESS_FINISH,
    SUCCESS_DELETE,
    ERROR
}

data class UserUiState(
    val apiQueryState: ApiQueryState = ApiQueryState.SUCCESS_FINISH,
    val message: String = ""
)