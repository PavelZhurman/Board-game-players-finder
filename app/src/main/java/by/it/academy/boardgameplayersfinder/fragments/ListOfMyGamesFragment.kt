package by.it.academy.boardgameplayersfinder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.adapters.MyGamesAdapter
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import by.it.academy.boardgameplayersfinder.viewmodel.AcceptedGameInformationForGuestViewModel
import by.it.academy.boardgameplayersfinder.viewmodel.ChangeInformationAboutGameEventByOwnerViewModel
import by.it.academy.boardgameplayersfinder.viewmodel.ListOfMyGamesViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ListOfMyGamesFragment : Fragment(R.layout.fragment_list_of_my_games) {

    private lateinit var database: FirebaseFirestore
    private lateinit var myGamesAdapter: MyGamesAdapter
    private lateinit var recyclerView: RecyclerView
    private var values: MutableList<GameEventItem> = mutableListOf()

    private lateinit var listOfMyGamesViewModel: ListOfMyGamesViewModel
    private val changeInformationAboutGameEventByOwnerViewModel: ChangeInformationAboutGameEventByOwnerViewModel by activityViewModels()
    private val acceptedGameInformationForGuestViewModel: AcceptedGameInformationForGuestViewModel by activityViewModels()
    private lateinit var textViewUserName: TextView
    private lateinit var buttonLogOut: Button
    private lateinit var buttonCreateGameEvent: Button
    private lateinit var buttonFindGameToPlay: Button
    private var userName: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        database = Firebase.firestore

        with(view) {
            textViewUserName = findViewById(R.id.textViewUserName)
            buttonLogOut = findViewById(R.id.buttonLogOut)
            buttonCreateGameEvent = findViewById(R.id.buttonCreateGameEvent)

            recyclerView = findViewById(R.id.recyclerViewMyGames)
            buttonFindGameToPlay = findViewById(R.id.buttonFindGameToPlay)
        }

        buttonCreateGameEvent.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_listOfMyGamesFragment_to_createGameEventFragment)
        }
        buttonFindGameToPlay.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_listOfMyGamesFragment_to_findEventFragment)
        }

        listOfMyGamesViewModel = ViewModelProvider(this).get(ListOfMyGamesViewModel::class.java)
        listOfMyGamesViewModel.getUserLiveData().observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                userName = firebaseUser.email ?: ""
                val textHelloUser =
                    getString(R.string.hello) + " " + userName
                textViewUserName.text = textHelloUser
                buttonLogOut.isEnabled = true
            } else {
                buttonLogOut.isEnabled = false
            }

            listOfMyGamesViewModel.getMyGameEvents(userName).observe(viewLifecycleOwner) { list ->
                if (list != null) {
                    values = list

                    recyclerView.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    myGamesAdapter = MyGamesAdapter(values)
                    myGamesAdapter.onEventItemClickListener = {
                        when (it.gameEventOwner) {
                            userName -> {
                                changeInformationAboutGameEventByOwnerViewModel.postValue(it)
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_listOfMyGamesFragment_to_changeInformationAboutGameEventFragment)
                            }
                            else -> {
                                acceptedGameInformationForGuestViewModel.postValue(it)
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_listOfMyGamesFragment_to_acceptedGameInformationForGuestFragment)
                            }
                        }

                    }
                    recyclerView.adapter = myGamesAdapter
                }
            }
        }

        listOfMyGamesViewModel.getLoggedOutLiveData().observe(viewLifecycleOwner) { loggedOut ->
            if (loggedOut) {
                Toast.makeText(context, "User logged out", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view)
                    .navigate(R.id.action_listOfMyGamesFragment_to_authorizationFragment)
            }
        }

        buttonLogOut.setOnClickListener {
            listOfMyGamesViewModel.logOut()
        }
    }

}