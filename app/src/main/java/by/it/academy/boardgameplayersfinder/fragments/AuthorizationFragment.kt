package by.it.academy.boardgameplayersfinder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.it.academy.boardgameplayersfinder.MainActivity
import by.it.academy.boardgameplayersfinder.R
import by.it.academy.boardgameplayersfinder.viewmodel.AuthorizationViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {


//    private lateinit var googleSignInClient: GoogleSignInClient
    //    private lateinit var auth: FirebaseAuth

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var bLoginWithEmail: Button
    private lateinit var bRegisterWithEmail: Button
//    private lateinit var buttonListOFGames: Button
//    private lateinit var buttonSignInWithGS:Button

    private lateinit var authorizationViewModel: AuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authorizationViewModel = ViewModelProvider(this).get(AuthorizationViewModel::class.java)
        authorizationViewModel.getUserLiveData().observe(this) { firebaseUser ->
            if (firebaseUser != null) {
                (activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_listOfMyGamesFragment)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            editTextEmail = findViewById(R.id.editTextEmail)
            editTextPassword = findViewById(R.id.editTextPassword)
            bLoginWithEmail = findViewById(R.id.bLoginWithEmail)
            bRegisterWithEmail = findViewById(R.id.bRegisterWithEmail)
//            buttonListOFGames = findViewById(R.id.buttonListOFGames)
//            buttonSignInWithGS = findViewById(R.id.signInButton)
        }

//        buttonSignInWithGS

        bLoginWithEmail.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.email_address_and_password_must_be_entered),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                authorizationViewModel.login(email, password)
            }
        }

        bRegisterWithEmail.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.email_address_and_password_must_be_entered),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                authorizationViewModel.register(email, password)
            }

        }
    }

//    override fun onStart() {
//        super.onStart()
//        buttonListOFGames.setOnClickListener {
//            (activity as MainActivity).navController.navigate(R.id.action_authorizationFragment_to_listOfMyGamesFragment)
//        }
//    }

/*    private fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        buttonSignIn,
                        "Check your email to verify the address",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    view?.let {
                        Snackbar.make(it, "Error sending email", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
    }*/


}