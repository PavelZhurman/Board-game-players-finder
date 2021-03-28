package by.it.academy.boardgameplayersfinder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

const val GAME_EVENT = "GameEvents"

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController


//        supportFragmentManager.beginTransaction()
//            .add<AuthorizationFragment>(R.id.mainFragmentContainer)
//            .setReorderingAllowed(true)
//            .addToBackStack(null)
//            .commit()


//    override fun onFragmentChange(fragmentClass: Class<out Fragment>, bundle: Bundle) {
//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            addToBackStack(null)
//            replace(R.id.mainFragmentContainer, fragmentClass, bundle)
//        }
//    }


    }
}


