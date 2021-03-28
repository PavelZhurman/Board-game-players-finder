package by.it.academy.boardgameplayersfinder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.repositories.AuthAppRepository
import by.it.academy.boardgameplayersfinder.repositories.FirestoreDatabaseRepository

class CreateGaveEventViewModel(application: Application) : AndroidViewModel(application) {

    private val firestoreDatabaseRepository = FirestoreDatabaseRepository(application)
    private val authAppRepository = AuthAppRepository(application)
    private val userLiveData = authAppRepository.getUserLiveData()

    fun createGameItem(gameEventItem: GameEventItem) {
        firestoreDatabaseRepository.createGameItem(gameEventItem)
    }

    fun getUserLiveData() = userLiveData
}