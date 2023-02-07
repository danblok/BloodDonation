package com.example.blooddonation;

import static com.example.blooddonation.Constants.USER_ID_FIELD;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ApplicationsViewModel extends ViewModel {

    private static final String TAG = "ApplicationsViewModel";

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference applicationsCollection =
            db.collection(Constants.APPLICATIONS_COLLECTION);

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<List<Application>> applications = new MutableLiveData<>();

    public ApplicationsViewModel() {
        loadApplications();
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<List<Application>> getApplications() {
        return applications;
    }

    public void removeApplication(String applicationId) {
        applicationsCollection.document(applicationId).delete()
                .addOnSuccessListener(unused -> {
                })
                .addOnFailureListener(e -> {
                    error.setValue(e.getMessage());
                });
    }

    public void loadApplications() {
        applicationsCollection
                .whereEqualTo(USER_ID_FIELD, auth.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        applications.setValue(task.getResult().toObjects(Application.class));
                    } else {
                        error.setValue(task.getException().getMessage());
                    }
                });
    }

    public void signOut() {
        auth.signOut();
    }
}
