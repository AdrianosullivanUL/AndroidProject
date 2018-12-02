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
    private final List<EngineDesignation> mEngineDesignations = new ArrayList<EngineDesignation>();

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
    public void populateEngineDesignations()
    {
        int i = 0;
        db.collection("engine_designation")
                // .whereEqualTo("capital", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Constants.TAG, document.getId() + " => " + document.getData());
                                String mEngineModel = document.get(Constants.KEY_ENGINE_MODEL).toString();
                                String mEngineDesignation = document.get(Constants.KEY_ENGINE_DESIGNATION).toString();
                                int mThrustInK = Integer.parseInt(document.get(Constants.KEY_THRUST_IN_K).toString());
                                int mRedLineTemperature = Integer.parseInt(document.get(Constants.KEY_RED_LINE_TEMRERATURE).toString());
                                int mCyclesPerDegree = Integer.parseInt(document.get(Constants.KEY_CYCLES_PER_DEGREE).toString());

                                mEngineDesignations.add(new EngineDesignation(mEngineModel, mEngineDesignation, mThrustInK, mRedLineTemperature, mCyclesPerDegree));
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
    public List<String> getEngineConfigurationsForEngineModel(String engineModel)
    {
        List<String> mDesignations = new ArrayList<>();

        for (EngineDesignation engineDesignation: mEngineDesignations
             ) {
            if (engineDesignation.getEngineModel() == engineModel || engineModel == null)
                mDesignations.add(engineDesignation.getConfiguration());
        }
        return mDesignations;
    }

}
