package com.example.blooddonation;

import static com.example.blooddonation.Constants.APPLICATIONS_COLLECTION;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ApplicationViewModel extends ViewModel {

    private static final String TAG = "ApplicationViewModel";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference applicationsCollection = db.collection(APPLICATIONS_COLLECTION);
    private FirebaseAuth auth;

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isApplicationAdded = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>();

    public ApplicationViewModel() {
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

    public LiveData<Boolean> getIsApplicationAdded() {
        return isApplicationAdded;
    }

    public LiveData<FirebaseUser> getFirebaseUser() {
        return firebaseUser;
    }

    public void createApplication(Application newApplication) {
        newApplication.setId(applicationsCollection.document().getId());
        applicationsCollection
                .document(newApplication.getId())
                .set(newApplication)
                .addOnSuccessListener(unused -> {

                })
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "A new application has been added");
                    isApplicationAdded.setValue(true);
                })
                .addOnFailureListener(e -> {
                    error.setValue(e.getMessage());
                });
    }
}
