package by.it.academy.boardgameplayersfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.boardgameplayersfinder.adapters.MyGamesAdapter
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*

private const val GAME_EVENT = "GameEvent"

class ListOfMyGamesActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var myGamesAdapter: MyGamesAdapter
    private lateinit var recyclerView: RecyclerView

    private val values:MutableList<GameEventItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_my_games)

        database = FirebaseDatabase.getInstance().getReference(GAME_EVENT)

        val valueEventListener: ValueEventListener = object : ValueEventListener {
            lateinit var gameEventItem:GameEventItem
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (values.size !=0) values.clear()
                for (item in dataSnapshot.children) {
                    item.getValue(GameEventItem::class.java).also {
                        if (it != null) {
                            gameEventItem = it

                        }
                    }
                    values.add(gameEventItem)
                }
                myGamesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(valueEventListener)








        recyclerView = findViewById(R.id.recyclerViewMyGames)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        myGamesAdapter = MyGamesAdapter(values)
        recyclerView.adapter = myGamesAdapter

        findViewById<Button>(R.id.buttonFindPeopleToPlay).setOnClickListener {
            val intent = Intent(this, FindPeopleToPlayActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonFindGameToPlay).setOnClickListener {
            val intent = Intent(this, FindGameToPlayActivity::class.java)
            startActivity(intent)
        }


    }

    private fun initTestData(): MutableList<GameEventItem> {
        val game = GameEventItem(
            "1",
            "gamename",
            3,
            "Belarus, Grodno. Sovetskaya-street 7-18",
            "14 февраля 2021",
            "17:17"
        )
        val game1 = GameEventItem(
            "2",
            "gamename",
            3,
            "Belarus, Grodno. Sovetskaya-street 7-18",
            "14 февраля 2021",
            "17:17"
        )
        val game2 = GameEventItem(
            "2",
            "gamename",
            3,
            "Belarus, Grodno. Sovetskaya-street 7-18",
            "14 февраля 2021",
            "17:17"
        )
        val game3 = GameEventItem(
            "2",
            "gamename",
            3,
            "Belarus, Grodno. Sovetskaya-street 7-18",
            "14 февраля 2021",
            "17:17"
        )
        return mutableListOf(game, game1, game2, game3)
    }
}