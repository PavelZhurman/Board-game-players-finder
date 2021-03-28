package by.it.academy.boardgameplayersfinder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import by.it.academy.boardgameplayersfinder.repositories.AuthAppRepository
import com.google.firebase.auth.FirebaseUser

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    private val authAppRepository = AuthAppRepository(application)
    private val userLiveData = authAppRepository.getUserLiveData()

    fun login(email: String, password: String) {
        authAppRepository.login(email, password)
    }

    fun register(email: String, password: String) {
        authAppRepository.register(email, password)
    }

    fun getUserLiveData() = userLiveData
}