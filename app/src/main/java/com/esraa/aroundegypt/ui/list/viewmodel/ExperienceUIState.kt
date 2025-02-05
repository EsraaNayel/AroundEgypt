package com.esraa.aroundegypt.ui.list.viewmodel

sealed class ExperienceUIState {
    data object Loading : ExperienceUIState()
    data class Success(val message: String) : ExperienceUIState()
    data class Error(val message: String) : ExperienceUIState()
}
