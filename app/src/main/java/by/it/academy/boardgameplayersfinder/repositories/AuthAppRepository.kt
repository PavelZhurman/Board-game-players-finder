package by.it.academy.boardgameplayersfinder.repositories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import by.it.academy.boardgameplayersfinder.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthAppRepository(private val application: Application) {

    private var firebaseAuth = FirebaseAuth.getInstance()
    private var userLiveData = MutableLiveData<FirebaseUser>()
    private var loggedOutLiveData = MutableLiveData<Boolean>()

    init {
        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }

    fun getUserLiveData() = userLiveData

    fun getLoggedOutLiveData() = loggedOutLiveData

    fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(MainActivity()) { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {

                    Toast.makeText(
                        application.applicationContext,
                        "Registration Failure: " + task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(MainActivity()) { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Toast.makeText(
                        application.applicationContext,
                        "Login Failure: " + task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }



}