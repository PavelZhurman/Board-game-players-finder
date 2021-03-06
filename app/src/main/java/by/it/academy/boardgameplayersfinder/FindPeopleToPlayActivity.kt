package by.it.academy.boardgameplayersfinder

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.functions.DatePickerFragment
import by.it.academy.boardgameplayersfinder.functions.TimePickerFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val GAME_EVENT = "GameEvent"

class FindPeopleToPlayActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var editTextGameName: EditText
    private lateinit var editTextPlayersNeedToFind: EditText
    private lateinit var editTextPlaceToPlay: EditText
    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: TextView
    private lateinit var buttonCreateGameEvent: Button

    private lateinit var gameName: String
    private lateinit var placeToPlay: String
    private var playersNeedToFind: Int = 0
    private var dateOfEvent: String = ""
    private var timeOfEvent: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_people_to_play)

        buttonCreateGameEvent = findViewById(R.id.buttonCreateGameEvent)
        editTextDate = findViewById(R.id.editTextDate)
        editTextTime = findViewById(R.id.editTextTime)
        editTextGameName = findViewById(R.id.editTextGameName)
        editTextPlayersNeedToFind = findViewById(R.id.editTextPlayersNeedToFind)
        editTextPlaceToPlay = findViewById(R.id.editTextPlaceToPlay)

        database = FirebaseDatabase.getInstance().getReference(GAME_EVENT)

        val dpf = DatePickerFragment()
        val tpf = TimePickerFragment()



        editTextDate.setOnClickListener {
            dpf.show(supportFragmentManager, "datePicker")
            dpf.onDateSetListener = { date: String ->
                dateOfEvent = date
                editTextDate.setText(dateOfEvent)
            }
        }

        editTextTime.setOnClickListener {
            tpf.show(supportFragmentManager, "timePicker")
            tpf.onTimeSetListener = { time: String ->
                timeOfEvent = time
                editTextTime.text = timeOfEvent
            }
        }

        buttonCreateGameEvent.setOnClickListener {
            checkForCompletenessOfFieldsAndFinishActivityIfSuccess()
        }
    }

    private fun createGameEventItem() {
        val id = database.key ?: ""
        val gameEventItem = GameEventItem(
            id, gameName = gameName,
            playersNeedToFind = playersNeedToFind,
            placeToPlayAddress = placeToPlay,
            dateOfEvent = dateOfEvent,
            timeOfEvent = timeOfEvent
        )

        database.push().setValue(gameEventItem)
        finish()
    }

    private fun checkForCompletenessOfFieldsAndFinishActivityIfSuccess() {

        gameName = editTextGameName.text.toString()
        placeToPlay = editTextPlaceToPlay.text.toString()

        when {
            gameName.isEmpty() -> Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_enter_the_name_of_the_game),
                Snackbar.LENGTH_SHORT
            ).show()

            editTextPlayersNeedToFind.text.toString().isEmpty() -> Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_enter_the_required_number_of_the_players),
                Snackbar.LENGTH_SHORT
            ).show()

            editTextPlayersNeedToFind.text.toString().toInt() < 1 -> {
                Snackbar.make(
                    buttonCreateGameEvent,
                    getString(R.string.please_enter_the_required_number_of_the_players),
                    Snackbar.LENGTH_SHORT
                ).show()
                playersNeedToFind = editTextPlayersNeedToFind.text.toString().toInt()
            }

            placeToPlay.isEmpty() -> Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_indicate_the_address_where_the_event_will_take_place),
                Snackbar.LENGTH_SHORT
            ).show()

            dateOfEvent.isEmpty() -> Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_enter_date),
                Snackbar.LENGTH_SHORT
            ).show()

            timeOfEvent.isEmpty() -> Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_enter_time),
                Snackbar.LENGTH_SHORT
            ).show()

            else -> createGameEventItem()

        }

    }

}


