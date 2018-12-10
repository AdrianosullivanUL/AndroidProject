package com.example.maryc.egt;

import android.content.Context;
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
    private final List<Engine> mEngines = new ArrayList<Engine>();
    private final List<EGTResult> mEGTResult = new ArrayList<>();
    private Context context;

    public DBHelper(Context context, FirebaseFirestore db) {
        this.db = db;
        this.context = context;

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

    public void populateEngines() {
        int i = 0;
        mEngines.clear();
        db.collection("engine")
                // .whereEqualTo("capital", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Constants.TAG, document.getId() + " => " + document.getData());
                                Engine engine = new Engine();
                                engine.setESN(document.get(Constants.KEY_ESN).toString());
                                engine.setEngineModel(document.get(Constants.KEY_ENGINE_MODEL).toString());
                                mEngines.add(engine);
                            }
                            MainActivity mainActivity = (MainActivity)context;
                            mainActivity.setESNAdaptor();
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
    public List<String> getESNs() {
        List<String> esns = new ArrayList<>();
        for (Engine engine : mEngines)
        {
            esns.add(engine.getESN());
        }
        return esns;
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
        addEngineIfNotExists(engineRecord.getESN(), engineRecord.getEngineModel());

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
    public void generateResults(DocumentSnapshot document)
    {
                                Log.d(Constants.TAG, document.getId() + " => " + document.getData());
                                String engineDesignation = document.get(Constants.KEY_ENGINE_DESIGNATION).toString();
                                String engineModel =  document.get(Constants.KEY_ENGINE_MODEL).toString();
        int cyclesSinceNew = Integer.parseInt(document.get(Constants.KEY_CYCLES_SINCE_NEW).toString());
        int currentEGT = 0;
        try {
            String currentEGTString = document.get(Constants.KEY_CURRENT_EGT).toString() ;
                    currentEGT = Integer.parseInt(currentEGTString);
                    }
        catch   (Exception ex)
        {}
        buildEGTResults(engineModel,engineDesignation, currentEGT, cyclesSinceNew);
    }

    private void buildEGTResults(String engineModel, String engineDesignation,
                                         final int CurrentCycles, final int CurrentEGT) {
        mEGTResult.clear();
        db.collection(Constants.COLLECTION_ENGINE_DESIGNATION)
                .whereEqualTo(Constants.KEY_ENGINE_MODEL, engineModel)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Constants.TAG, document.getId() + " => " + document.getData());
                                int cyclesPerDegree = Integer.parseInt(document.get(Constants.KEY_CYCLES_PER_DEGREE).toString());
                                int redLineTemperature = Integer.parseInt(document.get(Constants.KEY_RED_LINE_TEMRERATURE).toString());
                                int thrustInK = Integer.parseInt(document.get(Constants.KEY_THRUST_IN_K).toString());
                                int averageCyclesPerYear = Integer.parseInt(document.get(Constants.KEY_AVERAGE_CYCLES_PER_YEAR).toString());

                                int remainingCycles  = CurrentCycles ;

                                int egtMargin = redLineTemperature - CurrentEGT;
                                int shopVisitYear = egtMargin * cyclesPerDegree / averageCyclesPerYear;
                                EGTResult egtResult = new EGTResult(thrustInK,remainingCycles,shopVisitYear,egtMargin);
                                mEGTResult.add(egtResult);
                            }
                            EGTMarginDetailActivity egtMarginDetailActivity = (EGTMarginDetailActivity)context;
                            egtMarginDetailActivity.populateTableResults(mEGTResult);


                        } else {
                            Log.d(Constants.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public EngineRecord getEngineRecord(String EngineRecordId) {
        EngineRecord engineRecord = new EngineRecord();
        engineRecord.setESN("123456");
        engineRecord.setEngineModel("CFM56-7B");
        //engineRecord.setRecordDate(New GregorianCalendar.);
        engineRecord.setCurrentEGT(796);
        return engineRecord;
    }
    private void addEngineIfNotExists(final String ESN, final String EngineModel)
    {
        db.collection("engine")
                 .whereEqualTo("ESN", ESN)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean found = false;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              found = true;
                            }
                            if (found == false)
                            {
                                // Add this ESN to the list of engines
                                Map<String, Object> mEngine = new HashMap<>();
                                mEngine.put(Constants.KEY_ESN,ESN);
                                mEngine.put(Constants.KEY_ENGINE_MODEL, EngineModel);

                                // Add a new document with a generated ID
                                db.collection("engine")
                                        .add(mEngine)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(Constants.TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                // Refresh the Engine spinner on the front page
                                                populateEngines();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(Constants.TAG, "Error adding document", e);
                                            }
                                        });
                            }
                        } else {
                            Log.d(Constants.TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
