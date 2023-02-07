package com.example.blooddonation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationViewModel extends ViewModel {

    private static final String USERS_COLLECTION = "Users";
    private static final String TAG = "RegistrationViewModel";

    private final FirebaseAuth auth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection(USERS_COLLECTION);

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>();
    private final MutableLiveData<User> user = new MutableLiveData<>();

    public RegistrationViewModel() {
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseUser.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getFirebaseUser() {
        return firebaseUser;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void signUp(User newUser) {
        auth.createUserWithEmailAndPassword(newUser.getEmail(), newUser.getPassword())
                .addOnSuccessListener(authResult -> {
                    FirebaseUser authUser = authResult.getUser();
                    if (authUser == null) {
                        return;
                    }
                    firebaseUser.setValue(authUser);
                })
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }

    public void createUser(User newUser) {
        usersCollection
                .document(newUser.getId())
                .set(newUser)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "A new user was added");
                    user.setValue(newUser);
                })
                .addOnFailureListener(e -> {
                    error.setValue(e.getMessage());
                });
    }
}
