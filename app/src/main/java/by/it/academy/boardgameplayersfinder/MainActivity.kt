package by.it.academy.boardgameplayersfinder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var buttonSignIn: Button

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignOut: Button


//    private lateinit var binding: ActivityGoogleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignOut = findViewById(R.id.buttonSignOut)

        auth = Firebase.auth

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        findViewById<Button>(R.id.ButtonListOFGames).setOnClickListener {
            val intent = Intent(this, ListOfMyGamesActivity::class.java)

            startActivity(intent)
        }

        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val user = auth.currentUser
            if (email.isEmpty() && password.isEmpty()) {
                Snackbar.make(buttonSignIn, "Enter valid email and password", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Snackbar.make(
                                buttonSignIn,
                                "Authentication successful.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            buttonSignOut.visibility = View.VISIBLE
                            if (user.isEmailVerified) {
                                
                            }
                        } else {
                            Snackbar.make(
                                buttonSignIn,
                                "Authentication failed.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                    }
            }
        }




        findViewById<Button>(R.id.buttonSignUp).setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (email.isEmpty() && password.isEmpty()) {
                Snackbar.make(buttonSignIn, "Enter valid email and password", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Snackbar.make(
                                buttonSignIn,
                                "Registration successful.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            sendEmailVerification()
                        } else {
                            Snackbar.make(
                                buttonSignIn,
                                "Registration failed.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                    }

            }
        }

        buttonSignOut.setOnClickListener {
            auth.signOut()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
//            reload();
            buttonSignOut.visibility = View.VISIBLE
            Snackbar.make(editTextEmail, "user not null", Snackbar.LENGTH_SHORT).show()
        } else {
            buttonSignOut.visibility = View.GONE
        }
    }

    private fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Snackbar.make(
                    buttonSignIn,
                    "Check your email to verify the address",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                Snackbar.make(buttonSignIn, "Error sending email", Snackbar.LENGTH_LONG).show()
            }
        }
    }


    companion object {

        private const val RC_SIGN_IN = 123
    }
}

