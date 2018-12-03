package com.example.maryc.egt;

import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapshot;
    private final FirebaseFirestore db;
    private final List<String> mEngineModels = new ArrayList<String>();
    private final List<EngineDesignation> mEngineDesignations = new ArrayList<EngineDesignation>();

    public DBHelper() {
        db = FirebaseFirestore.getInstance();

    }

    public void populateEngineModels() {
        int i = 0;
        mEngineModels.add("");
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

    public void populateEngineDesignations() {
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

    public List<String> getEngineModels() {
        return mEngineModels;
    }

    public List<String> getEngineConfigurationsForEngineModel(String engineModel) {
        List<String> mDesignations = new ArrayList<>();
        mDesignations.add("");
        for (EngineDesignation engineDesignation : mEngineDesignations
                ) {
            if (engineDesignation.getEngineModel().equals(engineModel.toString()) || engineModel == null)
                mDesignations.add(engineDesignation.getConfiguration());
        }
        return mDesignations;
    }
    public EngineDesignation getEngineDesignation(String engineModel, String engineDesignation)
    {
        EngineDesignation mEngineDesignation = null;
        for (EngineDesignation engineDesig : mEngineDesignations
                ) {
            if (engineDesig.getEngineModel().equals(engineModel.toString()) && engineDesig.getConfiguration().equals(engineDesignation.toString())) {
                mEngineDesignation = engineDesig;
                break;
            }
        }
        return mEngineDesignation;

    }

    public void addEngineRecord(EngineRecord engineRecord) {
        // Create a new user with a first and last name
        Map<String, Object> mEngineRecord = new HashMap<>();
        mEngineRecord.put(Constants.KEY_ESN, engineRecord.getESN());
        mEngineRecord.put(Constants.KEY_RECORD_DATE, engineRecord.getRecordDate());
        mEngineRecord.put(Constants.KEY_ENGINE_MODEL, engineRecord.getEngineModel());
        mEngineRecord.put(Constants.KEY_OPERATOR, engineRecord.getOperator());
        mEngineRecord.put(Constants.KEY_ENGINE_DESIGNATION, engineRecord.getEngineDesignation());
        mEngineRecord.put(Constants.KEY_THRUST_IN_K, engineRecord.getThrustInK());
        mEngineRecord.put(Constants.KEY_CURRENT_EGT, engineRecord.getCurrentEGT());
        mEngineRecord.put(Constants.KEY_HOURS_SINCE_LAST_SHOP_VISIT, engineRecord.getHoursSinceLastShopVisit());
        mEngineRecord.put(Constants.KEY_HOURS_SINCE_NEW, engineRecord.getHoursSinceNew());
        mEngineRecord.put(Constants.KEY_CYCLES_SINCE_NEW, engineRecord.getCyclesSinceNew());
       // mEngineRecord.put(Constants.KEY_ESTIMATED_TIME_TO_SHOP_VISIT, engineRecord.getEstimatedTimeToShopVisit());


// Add a new document with a generated ID
        db.collection("engine_record")
                .add(mEngineRecord)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(Constants.TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Constants.TAG, "Error adding document", e);
                    }
                });
    }
}
