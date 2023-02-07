package com.example.blooddonation;

import static com.example.blooddonation.Constants.APPLICATIONS_COLLECTION;
import static com.example.blooddonation.Constants.USERS_COLLECTION;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ApplicationManagementViewModel extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference applicationsCollection = db.collection(APPLICATIONS_COLLECTION);
    private final CollectionReference usersCollection = db.collection(USERS_COLLECTION);
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Application> application = new MutableLiveData<>();
    private final MutableLiveData<User> user = new MutableLiveData<>();

    public ApplicationManagementViewModel() {
        auth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                loadUser(firebaseAuth.getUid());
            }
        });
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Application> getApplication() {
        return application;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void loadApplication(String applicationId) {
        applicationsCollection.document(applicationId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    application.setValue(documentSnapshot.toObject(Application.class));
                })
                .addOnFailureListener(e -> {
                    error.setValue(e.getMessage());
                });

        applicationsCollection.document(applicationId)
                .addSnapshotListener((value, e) -> {
            if (e != null) {
                error.setValue(e.getMessage());
                return;
            }

            if (value != null && value.exists()) {
                application.setValue(value.toObject(Application.class));
            }
        });
    }

    private void loadUser(String userId) {
        usersCollection.document(userId)
                .get()
                .addOnFailureListener(e -> {
                    error.setValue(e.getMessage());
                })
                .addOnSuccessListener(documentSnapshot -> {
                    user.setValue(documentSnapshot.toObject(User.class));
                });
    }

    public void updateStatus(String applicationId, String status) {
        applicationsCollection.document(applicationId)
                .update("status", status)
                .addOnSuccessListener(unused -> {
                })
                .addOnFailureListener(e -> {
                    error.setValue(e.getMessage());
                });
    }
}
