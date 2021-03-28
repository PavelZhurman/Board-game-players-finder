package by.it.academy.boardgameplayersfinder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.repositories.AuthAppRepository
import by.it.academy.boardgameplayersfinder.repositories.FirestoreDatabaseRepository

class ChangeInformationAboutGameEventByOwnerViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val authAppRepository = AuthAppRepository(application)
    private val userLiveData = authAppRepository.getUserLiveData()
    private val firestoreDatabaseRepository = FirestoreDatabaseRepository(application)

    private val gameEventItemMutableLiveData = MutableLiveData<GameEventItem>()

    fun postValue(gameEventItem: GameEventItem) {
        gameEventItemMutableLiveData.postValue(gameEventItem)
    }

    fun getGameEventItem() = gameEventItemMutableLiveData

    fun getUserLiveData() = userLiveData

    fun updateGameEventItem(gameEventItem: GameEventItem) {
        firestoreDatabaseRepository.updateGameItem(gameEventItem)
    }
}