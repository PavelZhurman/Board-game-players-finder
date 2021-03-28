package by.it.academy.boardgameplayersfinder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.viewmodel.ApplyForGameEventItemViewModel

class ApplyForGameEventItemFragment : Fragment(R.layout.fragment_apply_for_game_event_item) {

    private lateinit var tvGameNameValues: TextView
    private lateinit var tvNumberOfPlayersValues: TextView
    private lateinit var tvEventAddressValues: TextView
    private lateinit var tvDateValues: TextView
    private lateinit var tvTimeValues: TextView
    private lateinit var tvDescriptionValues: TextView
    private lateinit var bApply: Button
    private lateinit var etCommentsOnRequestSubmission: EditText

    private val applyForGameEventItemViewModel: ApplyForGameEventItemViewModel by activityViewModels()
    private lateinit var gameEventItem: GameEventItem


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


        applyForGameEventItemViewModel.getGameEventItem()
            .observe(viewLifecycleOwner, { _gameEventItem ->
                gameEventItem = _gameEventItem
                setValuesInTextViews(gameEventItem)
            })

        bApply.setOnClickListener {
            val userEmail = applyForGameEventItemViewModel.getUserLiveData().value?.email
            if (userEmail != null) {
                val map = gameEventItem.listOfPlayers
                map[userEmail] = "guest"
                val comment = etCommentsOnRequestSubmission.text.toString()
                gameEventItem.listOfPlayers[userEmail] = comment

                var playersAvailable = gameEventItem.playersAvailable
                ++playersAvailable
                val maximumNumberOfPlayers = gameEventItem.maximumNumberOfPlayers
                if (playersAvailable <= maximumNumberOfPlayers) {
                    gameEventItem.playersAvailable = playersAvailable
                    applyForGameEventItemViewModel.applyForGameEventItem(gameEventItem)
                    Navigation.findNavController(view)
                        .navigate(R.id.action_applyForGameEventItemFragment_to_listOfMyGamesFragment)
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.the_maximum_number_of_players_in_this_event_has_been_reached),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
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
        }
    }
}