package by.it.academy.boardgameplayersfinder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import by.it.academy.boardgameplayersfinder.repositories.AuthAppRepository
import by.it.academy.boardgameplayersfinder.repositories.FirestoreDatabaseRepository
import com.google.firebase.auth.FirebaseUser

class ListOfMyGamesViewModel(application: Application) : AndroidViewModel(application) {

    private val authAppRepository = AuthAppRepository(application)
    private val userLiveData = authAppRepository.getUserLiveData()
    private val loggedOutLiveData = authAppRepository.getLoggedOutLiveData()

    private val firestoreDatabaseRepository = FirestoreDatabaseRepository(application)

    fun logOut() {
        authAppRepository.logOut()
    }

    fun getUserLiveData() = userLiveData

    fun getLoggedOutLiveData() = loggedOutLiveData

    fun getAllGameEvents() = firestoreDatabaseRepository.getAllGameEvents()

    fun getMyGameEvents(gameEventOwner:String) = firestoreDatabaseRepository.getMyGameEvents(gameEventOwner)

}