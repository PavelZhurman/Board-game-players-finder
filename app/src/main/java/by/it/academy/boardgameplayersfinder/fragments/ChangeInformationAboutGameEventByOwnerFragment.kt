package by.it.academy.boardgameplayersfinder.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.functions.DatePickerFragment
import by.it.academy.boardgameplayersfinder.functions.TimePickerFragment
import by.it.academy.boardgameplayersfinder.viewmodel.ChangeInformationAboutGameEventByOwnerViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar

class ChangeInformationAboutGameEventByOwnerFragment :
    Fragment(R.layout.fragment_change_information_about_game_event_by_owner) {

    private lateinit var etGameNameValues: EditText
    private lateinit var etMaxNumberOfPlayersValues: EditText
    private lateinit var etPlayersAvailableValues: EditText
    private lateinit var etDateValues: EditText
    private lateinit var etTimeValues: EditText
    private lateinit var etDescriptionValues: EditText
    private lateinit var bApply: Button
    private lateinit var joinedPlayers: Button

    private lateinit var gameName: String
    private var placeToPlay = ""
    private var maximumNumberOfPlayers = 5
    private var playersAvailable = 4
    private var dateOfEvent: String = ""
    private var timeOfEvent: String = ""
    private var description: String = ""

    private val changeInformationAboutGameEventByOwnerViewModel: ChangeInformationAboutGameEventByOwnerViewModel by activityViewModels()
    private lateinit var gameEventItem: GameEventItem
    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private val apiKey = "AIzaSyADO0T-aMiXk4hGemxXlBzBe2qy4tOw_ps"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {

            etGameNameValues = findViewById(R.id.etGameNameValues)
            etMaxNumberOfPlayersValues = findViewById(R.id.etMaxNumberOfPlayersValues)
            etPlayersAvailableValues = findViewById(R.id.etPlayersAvailableValues)
            etDateValues = findViewById(R.id.etDateValues)
            etTimeValues = findViewById(R.id.etTimeValues)
            etDescriptionValues = findViewById(R.id.etDescriptionValues)
            bApply = findViewById(R.id.bApply)
            joinedPlayers = findViewById(R.id.joinedPlayers)
        }

        gameEventItem = changeInformationAboutGameEventByOwnerViewModel.getGameEventItem().value!!

        setEditTexts()
        setValuesInTextViews(gameEventItem)

        joinedPlayers.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_changeInformationAboutGameEventFragment_to_joinedPlayersFragment2)
        }

        bApply.setOnClickListener {
            checkForCompletenessOfFieldsAndFinishActivityIfSuccess()
        }
    }

    private fun setEditTexts() {
        val dpf = DatePickerFragment()
        val tpf = TimePickerFragment()

        etDateValues.setOnClickListener {
            dpf.show(childFragmentManager, "datePicker")
            dpf.onDateSetListener = { date: String ->
                gameEventItem.dateOfEvent = date
                etDateValues.setText(date)
            }
        }
        etTimeValues.setOnClickListener {
            tpf.show(childFragmentManager, "timePicker")
            tpf.onTimeSetListener = { time: String ->
                gameEventItem.timeOfEvent = time
                etTimeValues.setText(time)
            }
        }

        initializeTheAutocompleteSupportFragment()

    }

    private fun setValuesInTextViews(gameEventItem: GameEventItem) {
        with(gameEventItem) {
            etGameNameValues.setText(gameName)
            etPlayersAvailableValues.setText(playersAvailable.toString())
            etMaxNumberOfPlayersValues.setText(maximumNumberOfPlayers.toString())
            autocompleteFragment.setHint(placeToPlayAddress)
            etDateValues.setText(dateOfEvent)
            etTimeValues.setText(timeOfEvent)
            etDescriptionValues.setText(description)
        }
    }

    private fun initializeTheAutocompleteSupportFragment() {
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
                    gameEventItem.placeToPlayAddress = place.address.toString()
                    autocompleteFragment.setHint(gameEventItem.placeToPlayAddress)
                }

                override fun onError(status: Status) {
                    Toast.makeText(context, "An error occurred: $status", Toast.LENGTH_SHORT).show()
                    Log.i("TAG_TAG", "An error occurred: $status")
                }
            })
    }

    private fun checkForCompletenessOfFieldsAndFinishActivityIfSuccess() {

        gameName = etGameNameValues.text.toString()
        maximumNumberOfPlayers = etMaxNumberOfPlayersValues.text.toString().toInt()
        playersAvailable = etPlayersAvailableValues.text.toString().toInt()
        placeToPlay = gameEventItem.placeToPlayAddress
        description = etDescriptionValues.text.toString()
        dateOfEvent = gameEventItem.dateOfEvent
        timeOfEvent = gameEventItem.timeOfEvent

        when {
            gameName.isEmpty() -> Snackbar.make(
                bApply,
                getString(R.string.please_enter_the_name_of_the_game),
                Snackbar.LENGTH_SHORT
            ).show()

            etPlayersAvailableValues.text.toString().isEmpty() -> Snackbar.make(
                bApply,
                getString(R.string.please_enter_the_required_number_of_the_players),
                Snackbar.LENGTH_SHORT
            ).show()

//            editTextPlayersNeedToFind.text.toString().toInt() < 1 -> {
//                Snackbar.make(
//                    buttonCreateGameEvent,
//                    getString(R.string.please_enter_the_required_number_of_the_players),
//                    Snackbar.LENGTH_SHORT
//                ).show()
//                playersNeedToFind = editTextPlayersNeedToFind.text.toString().toInt()
//            }

            placeToPlay.isEmpty() -> Snackbar.make(
                bApply,
                getString(R.string.please_enter_the_address_where_the_event_will_take_place),
                Snackbar.LENGTH_SHORT
            ).show()

            dateOfEvent.isEmpty() -> Snackbar.make(
                bApply,
                getString(R.string.please_enter_date),
                Snackbar.LENGTH_SHORT
            ).show()

            timeOfEvent.isEmpty() -> Snackbar.make(
                bApply,
                getString(R.string.please_enter_time),
                Snackbar.LENGTH_SHORT
            ).show()

            else -> updateInformationAboutGameAndBackToListOfMyGames()

        }
    }

    private fun updateInformationAboutGameAndBackToListOfMyGames() {

        val gameEventItem1 = GameEventItem(
            id = gameEventItem.id,
            gameName = gameName,
            gameEventOwner = gameEventItem.gameEventOwner,
            playersAvailable = playersAvailable,
            maximumNumberOfPlayers = maximumNumberOfPlayers,
            placeToPlayAddress = placeToPlay,
            dateOfEvent = dateOfEvent,
            timeOfEvent = timeOfEvent,
            listOfPlayers = gameEventItem.listOfPlayers,
            description = description
        )
        changeInformationAboutGameEventByOwnerViewModel.updateGameEventItem(gameEventItem1)
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_changeInformationAboutGameEventFragment_to_listOfMyGamesFragment)
        }
    }


}