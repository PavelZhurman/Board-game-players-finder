package by.it.academy.boardgameplayersfinder.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.adapters.JoinedPlayersAdapter
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.entity.JoinedPlayerItem
import by.it.academy.boardgameplayersfinder.viewmodel.ChangeInformationAboutGameEventByOwnerViewModel

class JoinedPlayersFragment : Fragment(R.layout.fragment_joined_players) {

    private lateinit var joinedPlayersAdapter: JoinedPlayersAdapter
    private lateinit var recyclerView: RecyclerView
    private val changeInformationAboutGameEventByOwnerViewModel: ChangeInformationAboutGameEventByOwnerViewModel by activityViewModels()
    private lateinit var gameEventItem: GameEventItem
    private var valuesForAdapter = mutableListOf<JoinedPlayerItem>()
    private lateinit var joinedPlayerItem: JoinedPlayerItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            recyclerView = findViewById(R.id.recyclerView)

        }

        gameEventItem = changeInformationAboutGameEventByOwnerViewModel.getGameEventItem().value!!
        val map = gameEventItem.listOfPlayers

        for (i in map) {
            joinedPlayerItem = JoinedPlayerItem(i.key, i.value)
            valuesForAdapter.add(joinedPlayerItem)
        }

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        joinedPlayersAdapter = JoinedPlayersAdapter(valuesForAdapter)
        recyclerView.adapter = joinedPlayersAdapter

    }
}