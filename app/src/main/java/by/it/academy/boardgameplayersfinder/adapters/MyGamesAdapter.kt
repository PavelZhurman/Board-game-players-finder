package by.it.academy.boardgameplayersfinder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.entity.GameEventItem

class MyGamesAdapter(private val values: MutableList<GameEventItem>) :
    RecyclerView.Adapter<MyGamesAdapter.MyGamesItemViewHolder>() {

    lateinit var onEventItemClickListener: (gameEventItem: GameEventItem) -> Unit

    class MyGamesItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gameName: TextView = itemView.findViewById(R.id.textViewGameName)
        val placeToPlayAddress: TextView = itemView.findViewById(R.id.textViewPlaceToPlay)
        val dateOfEvent: TextView = itemView.findViewById(R.id.textViewDateOfEvent)
        val timeOfEvent: TextView = itemView.findViewById(R.id.textViewTimeOfEvent)
        val numberOfPeople: TextView = itemView.findViewById(R.id.textViewNumberOfPeople)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGamesItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_accepted_games, parent, false)
        return MyGamesItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyGamesItemViewHolder, position: Int) {

        val gameEventItem = values[position]
        val numberOfPlayers =
            "${gameEventItem.playersAvailable}/${gameEventItem.maximumNumberOfPlayers}"

        with(holder) {
            itemView.setOnClickListener { onEventItemClickListener.invoke(gameEventItem) }
            gameName.text = gameEventItem.gameName
            placeToPlayAddress.text = gameEventItem.placeToPlayAddress
            dateOfEvent.text = gameEventItem.dateOfEvent
            timeOfEvent.text = gameEventItem.timeOfEvent
            numberOfPeople.text = numberOfPlayers
        }
    }

    override fun getItemCount(): Int = values.size
}