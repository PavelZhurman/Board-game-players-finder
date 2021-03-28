package by.it.academy.boardgameplayersfinder.repositories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import by.it.academy.boardgameplayersfinder.GAME_EVENT
import by.it.academy.boardgameplayersfinder.MainActivity
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreDatabaseRepository(application: Application) {

    private val database = Firebase.firestore
    private val allGameEventsLiveData = MutableLiveData<MutableList<GameEventItem>>()
    private val applicationBaseContext = application.baseContext

    fun createGameItem(gameEventItem: GameEventItem) {
        val document = database
            .collection(GAME_EVENT)
            .document()
        gameEventItem.id = document.id

        val set = document.set(gameEventItem)
        set.addOnFailureListener {
            Toast.makeText(
                applicationBaseContext,
                "Failure ${it.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun updateGameItem(gameEventItem: GameEventItem) {
        database.collection(GAME_EVENT)
            .document(gameEventItem.id)
            .set(gameEventItem)
            .addOnFailureListener {
                Toast.makeText(applicationBaseContext, "Failure ${it.message}", Toast.LENGTH_LONG)
                    .show()
            }
    }

    fun getAllGameEvents(): MutableLiveData<MutableList<GameEventItem>> {
        database.collection(GAME_EVENT)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val list: MutableList<GameEventItem> =
                    querySnapshot.toObjects(GameEventItem::class.java)
                allGameEventsLiveData.postValue(list)
            }
        return allGameEventsLiveData
    }

    fun getMyGameEvents(player: String): MutableLiveData<MutableList<GameEventItem>> {
        database.collection(GAME_EVENT)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val listOfAllEvents: MutableList<GameEventItem> =
                    querySnapshot.toObjects(GameEventItem::class.java)

                val listOfMyGames = mutableListOf<GameEventItem>()
                for (gameEventItem in listOfAllEvents) {
                    val listOfPlayers = gameEventItem.listOfPlayers
                    for (playerFromDB in listOfPlayers) {
                        if (playerFromDB.key == player) {
                            listOfMyGames.add(gameEventItem)
                        }
                    }
                }
                allGameEventsLiveData.postValue(listOfMyGames)
            }
        return allGameEventsLiveData
    }

}
