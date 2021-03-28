package by.it.academy.boardgameplayersfinder.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.functions.DatePickerFragment
import by.it.academy.boardgameplayersfinder.functions.TimePickerFragment
import by.it.academy.boardgameplayersfinder.viewmodel.CreateGaveEventViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar


class CreateGameEventFragment : Fragment(R.layout.fragment_create_game_event) {


    private lateinit var createGaveEventViewModel: CreateGaveEventViewModel
    private lateinit var gameEventOwner: String

    private lateinit var editTextGameName: EditText
    private lateinit var editTextPlayersAvailable: EditText
    private lateinit var editTextMaximumNumberOfPlayers: EditText

    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: TextView
    private lateinit var buttonCreateGameEvent: Button
    private lateinit var editTextDescription: EditText
    private lateinit var autocompleteFragment: AutocompleteSupportFragment


    private lateinit var gameName: String
    private var placeToPlay = ""
    private var maximumNumberOfPlayers = 0
    private var playersAvailable = 0
    private var dateOfEvent: String = ""
    private var timeOfEvent: String = ""
    private var id: String = ""
    private var description: String = ""
    private val apiKey = "AIzaSyADO0T-aMiXk4hGemxXlBzBe2qy4tOw_ps"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            buttonCreateGameEvent = findViewById(R.id.buttonCreateGameEvent)
            editTextDate = findViewById(R.id.editTextDate)
            editTextTime = findViewById(R.id.editTextTime)
            editTextGameName = findViewById(R.id.editTextGameName)
            editTextPlayersAvailable = findViewById(R.id.editTextPlayersAvailable)
            editTextMaximumNumberOfPlayers = findViewById(R.id.editTextMaximumNumberOfPlayers)
            editTextDescription = findViewById(R.id.editTextDescription)

        }
        createGaveEventViewModel = ViewModelProvider(this).get(CreateGaveEventViewModel::class.java)


        val dpf = DatePickerFragment()
        val tpf = TimePickerFragment()

        editTextDate.setOnClickListener {
            dpf.show(childFragmentManager, "datePicker")
            dpf.onDateSetListener = { date: String ->
                dateOfEvent = date
                editTextDate.setText(dateOfEvent)
            }
        }

        editTextTime.setOnClickListener {
            tpf.show(childFragmentManager, "timePicker")
            tpf.onTimeSetListener = { time: String ->
                timeOfEvent = time
                editTextTime.text = timeOfEvent
            }
        }

        buttonCreateGameEvent.setOnClickListener {
            checkForCompletenessOfFieldsAndFinishActivityIfSuccess()
        }

        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = AutocompleteSupportFragment()
        autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        if (!Places.isInitialized()) {
            context?.let { Places.initialize(it, apiKey) }
        }
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(
            object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    placeToPlay = place.address.toString()
                    autocompleteFragment.setHint(placeToPlay)
                }

                override fun onError(status: Status) {
                    Toast.makeText(context, "An error occurred: $status", Toast.LENGTH_SHORT).show()
                    Log.i("TAG_TAG", "An error occurred: $status")
                }
            })

    }

    private fun createGameEventItem() {

        createGaveEventViewModel.getUserLiveData().observe(viewLifecycleOwner) { firebaseUser ->
            gameEventOwner = firebaseUser?.email.toString()
        }
        val gameEventItem = GameEventItem(
            id, gameName = gameName,
            gameEventOwner = gameEventOwner,
            playersAvailable = playersAvailable,
            maximumNumberOfPlayers = maximumNumberOfPlayers,
            placeToPlayAddress = placeToPlay,
            dateOfEvent = dateOfEvent,
            timeOfEvent = timeOfEvent,
            listOfPlayers = mutableMapOf(gameEventOwner to "eventOwner"),
            description = description
        )
        createGaveEventViewModel.createGameItem(gameEventItem)
        view?.let { it1 ->
            Navigation.findNavController(it1)
                .navigate(R.id.action_createGameEventFragment_to_listOfMyGamesFragment)
        }
    }

    private fun checkForCompletenessOfFieldsAndFinishActivityIfSuccess() {

        if (editTextMaximumNumberOfPlayers.text.toString().isEmpty()) {
            Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_enter_the_required_number_of_the_players),
                Snackbar.LENGTH_SHORT
            ).show()
            maximumNumberOfPlayers = 0
        } else {
            maximumNumberOfPlayers = editTextMaximumNumberOfPlayers.text.toString().toInt()
        }

        if (editTextPlayersAvailable.text.toString().isEmpty()) {
            Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_enter_the_required_number_of_the_players),
                Snackbar.LENGTH_SHORT
            ).show()
            playersAvailable = 0
        } else {
            playersAvailable = editTextPlayersAvailable.text.toString().toInt()

        }

        gameName = editTextGameName.text.toString()
        description = editTextDescription.text.toString()

        when {
            gameName.isEmpty() -> Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_enter_the_name_of_the_game),
                Snackbar.LENGTH_SHORT
            ).show()

            playersAvailable < 1 || playersAvailable > 100 || maximumNumberOfPlayers <= playersAvailable || maximumNumberOfPlayers > 100 -> {
                Snackbar.make(
                    buttonCreateGameEvent,
                    getString(R.string.please_enter_the_correct_number_of_players),
                    Snackbar.LENGTH_SHORT
                ).show()

            }

            placeToPlay.isEmpty() -> Snackbar.make(
                buttonCreateGameEvent,
                getString(R.string.please_enter_the_address_where_the_event_will_take_place),
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