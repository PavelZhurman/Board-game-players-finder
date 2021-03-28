package by.it.academy.boardgameplayersfinder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.viewmodel.AcceptedGameInformationForGuestViewModel

class AcceptedGameInformationForGuestFragment :
    Fragment(R.layout.fragment_accepted_game_information_for_guest) {

    private lateinit var tvGameNameValues: TextView
    private lateinit var tvNumberOfPlayersValues: TextView
    private lateinit var tvEventAddressValues: TextView
    private lateinit var tvDateValues: TextView
    private lateinit var tvTimeValues: TextView
    private lateinit var tvDescriptionValues: TextView
    private lateinit var bApply: Button
    private lateinit var etCommentsOnRequestSubmission: EditText

    private val acceptedGameInformationForGuestViewModel: AcceptedGameInformationForGuestViewModel by activityViewModels()
    private lateinit var gameEventItem: GameEventItem
    private lateinit var userEmail: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {

            tvGameNameValues = findViewById(R.id.tvGameNameValues)
            tvNumberOfPlayersValues = findViewById(R.id.tvNumberOfPlayersValues)
            tvEventAddressValues = findViewById(R.id.tvEventAddressValues)
            tvDateValues = findViewById(R.id.tvDateValues)
            tvTimeValues = findViewById(R.id.tvTimeValues)
            tvDescriptionValues = findViewById(R.id.tvDescriptionValues)
            bApply = findViewById(R.id.bApply)
            etCommentsOnRequestSubmission = findViewById(R.id.etCommentsOnRequestSubmission)
        }
        userEmail =
            acceptedGameInformationForGuestViewModel.getUserLiveData().value?.email.toString()

        gameEventItem = acceptedGameInformationForGuestViewModel.getGameEventItem().value!!
        setValuesInTextViews(gameEventItem)

        bApply.setOnClickListener {
            /*почему-то, если не создать отдельный мэп не хочет сохранять*/
            val map = gameEventItem.listOfPlayers
            map[userEmail] = "guest"
            val comment = etCommentsOnRequestSubmission.text.toString()
            gameEventItem.listOfPlayers[userEmail] = comment

            acceptedGameInformationForGuestViewModel.updateComment(gameEventItem)
            Navigation.findNavController(view)
                .navigate(R.id.action_acceptedGameInformationForGuestFragment_to_listOfMyGamesFragment)

        }
    }

    private fun setValuesInTextViews(gameEventItem: GameEventItem) {
        with(gameEventItem) {
            tvGameNameValues.text = gameName
            val numberOfPlayers = "$playersAvailable/$maximumNumberOfPlayers"
            tvNumberOfPlayersValues.text = numberOfPlayers
            tvEventAddressValues.text = placeToPlayAddress
            tvDateValues.text = dateOfEvent
            tvTimeValues.text = timeOfEvent
            tvDescriptionValues.text = description
            etCommentsOnRequestSubmission.setText(listOfPlayers[userEmail])
        }
    }
}