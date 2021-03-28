package by.it.academy.boardgameplayersfinder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.entity.JoinedPlayerItem

class JoinedPlayersAdapter(private val values: MutableList<JoinedPlayerItem>) :
    RecyclerView.Adapter<JoinedPlayersAdapter.JoinedPlayersItemViewHolder>() {

    class JoinedPlayersItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvPlayersEmailValue: TextView = itemView.findViewById(R.id.tvPlayersEmailValue)
        val tvComments: TextView = itemView.findViewById(R.id.tvComments)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinedPlayersItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_joined_player, parent, false)
        return JoinedPlayersItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JoinedPlayersItemViewHolder, position: Int) {
        val joinedPlayerItem = values[position]

        with(holder) {
            tvPlayersEmailValue.text = joinedPlayerItem.userEmail
            tvComments.text = joinedPlayerItem.comment
        }
    }

    override fun getItemCount() = values.size
}