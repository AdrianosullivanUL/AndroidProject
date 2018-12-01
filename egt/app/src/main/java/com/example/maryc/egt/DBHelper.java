package com.example.maryc.egt;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapshot;
    private final FirebaseFirestore db;
    private final List<String> mEngineModels = new ArrayList<String>();

    public DBHelper ()
    {
        db = FirebaseFirestore.getInstance();

    }
    public void populateEngineModels()
    {
        int i = 0;
        db.collection("engine_model")
               // .whereEqualTo("capital", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Constants.TAG, document.getId() + " => " + document.getData());
                                String mEngineModel = document.get(Constants.KEY_ENGINE_MODEL).toString();
                                 mEngineModels.add(mEngineModel);
                            }
                        } else {
                            Log.d(Constants.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public List<String> getEngineModels()
    {
        return mEngineModels;
    }

}
