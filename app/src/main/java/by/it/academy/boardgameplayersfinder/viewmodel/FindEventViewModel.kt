package by.it.academy.boardgameplayersfinder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import by.it.academy.boardgameplayersfinder.repositories.FirestoreDatabaseRepository

class FindEventViewModel(application: Application):AndroidViewModel(application){

    private val firestoreDatabaseRepository = FirestoreDatabaseRepository(application)

    fun getAllGameEvents() = firestoreDatabaseRepository.getAllGameEvents()

}