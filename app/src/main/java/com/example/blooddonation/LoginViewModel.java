package com.example.blooddonation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends ViewModel {

    private static final String USERS_COLLECTION = "Users";

    private final FirebaseAuth auth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference usersCollection = db.collection(USERS_COLLECTION);

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>();
    private final MutableLiveData<User> user = new MutableLiveData<>();

    public LoginViewModel() {
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

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                })
                .addOnFailureListener(e -> error.setValue(e.getMessage()));
    }

    public void pullUserDocument(String userId) {
        usersCollection.document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    user.setValue(documentSnapshot.toObject(User.class));
                });
    }
}
