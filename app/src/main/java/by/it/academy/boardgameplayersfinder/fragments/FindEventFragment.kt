package by.it.academy.boardgameplayersfinder.fragments

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.adapters.MyGamesAdapter
import by.it.academy.boardgameplayersfinder.entity.GameEventItem
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import by.it.academy.boardgameplayersfinder.viewmodel.ApplyForGameEventItemViewModel
import by.it.academy.boardgameplayersfinder.viewmodel.FindEventViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale


class FindEventFragment : Fragment(R.layout.fragment_find_event) {

    private lateinit var editTextGameName: TextInputEditText
    private lateinit var editTextLocation: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var findEventViewModel: FindEventViewModel
    private lateinit var myGamesAdapter: MyGamesAdapter

    private val values = mutableListOf<GameEventItem>()
    private var allValues = mutableListOf<GameEventItem>()

    private val applyForGameEventItemViewModel: ApplyForGameEventItemViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            editTextGameName = findViewById(R.id.editTextGameName)
            editTextLocation = findViewById(R.id.editTextLocation)
            recyclerView = findViewById(R.id.recyclerView)

        }

        findEventViewModel = ViewModelProvider(this).get(FindEventViewModel::class.java)
        findEventViewModel.getAllGameEvents().observe(viewLifecycleOwner) { list ->
            if (!list.isNullOrEmpty()) {
                allValues = list
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        myGamesAdapter = MyGamesAdapter(values)
        myGamesAdapter.onEventItemClickListener = { gameEventItem ->
            applyForGameEventItemViewModel.postValue(gameEventItem)
            Navigation.findNavController(view)
                .navigate(R.id.action_findEventFragment_to_applyForGameEventItemFragment)

        }
        recyclerView.adapter = myGamesAdapter

        editTextGameName.doAfterTextChanged { enteredText ->
            if (enteredText.isNullOrEmpty()) {
                values.clear()
            } else {
                setEditTextGameNameChangeListener(enteredText)
            }
        }

        editTextLocation.doAfterTextChanged { enteredText ->
            if (enteredText.isNullOrEmpty()) {
                values.clear()
            } else {
                setEditTextLocationChangeListener(enteredText)
            }
        }

    }


    private fun setEditTextGameNameChangeListener(enteredTextGameName: Editable) {
        values.clear()
        val enteredStringGameName = enteredTextGameName.toString().toLowerCase(Locale.ROOT)

        for (gameEventItem in allValues) {
            if (gameEventItem.gameName.toLowerCase(Locale.ROOT).contains(enteredStringGameName)) {
                values.add(gameEventItem)
            }
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun setEditTextLocationChangeListener(enteredTextLocation: Editable) {
        values.clear()
        val enteredStringLocation = enteredTextLocation.toString().toLowerCase(Locale.ROOT)

        for (gameEventItem in allValues) {
            if (gameEventItem.placeToPlayAddress.toLowerCase(Locale.ROOT)
                    .contains(enteredStringLocation)
            ) {
                values.add(gameEventItem)
            }
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }
}