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
import java.util.Calendar;
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

       // Function to pre-populate the engine designations
       // CreateDesignations();
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
                            MainActivity mainActivity = (MainActivity) context;
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
        for (Engine engine : mEngines) {
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

    public EngineDesignation getEngineDesignation(String engineModel, String engineDesignation) {
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

    public void generateResults(DocumentSnapshot document) {
        Log.d(Constants.TAG, document.getId() + " => " + document.getData());
        String engineDesignation = document.get(Constants.KEY_ENGINE_DESIGNATION).toString();
        String engineModel = document.get(Constants.KEY_ENGINE_MODEL).toString();
        int cyclesSinceNew = Integer.parseInt(document.get(Constants.KEY_CYCLES_SINCE_NEW).toString());
        int currentEGT = 0;
        try {
            String currentEGTString = document.get(Constants.KEY_CURRENT_EGT).toString();
            float currentEGTFloat = Float.parseFloat((currentEGTString));
            currentEGT = (int)currentEGTFloat;
        } catch (Exception ex) {
            String x = ex.getMessage();
        }
        buildEGTResults(engineModel, engineDesignation, currentEGT, cyclesSinceNew);
    }

    private void buildEGTResults(String engineModel, String engineDesignation,final int CurrentEGT, final int CurrentCycles ) {
        mEGTResult.clear();
        db.collection(Constants.COLLECTION_ENGINE_DESIGNATION)
                .whereEqualTo(Constants.KEY_ENGINE_MODEL, engineModel)
                //.orderBy(Constants.KEY_ENGINE_DESIGNATION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(Constants.TAG, document.getId() + " => " + document.getData());
                                String designation = document.get(Constants.KEY_ENGINE_DESIGNATION).toString();
                                int cyclesPerDegree = Integer.parseInt(document.get(Constants.KEY_CYCLES_PER_DEGREE).toString());
                                int redLineTemperature = Integer.parseInt(document.get(Constants.KEY_RED_LINE_TEMRERATURE).toString());
                                int thrustInK = Integer.parseInt(document.get(Constants.KEY_THRUST_IN_K).toString());
                                int averageCyclesPerYear = Integer.parseInt(document.get(Constants.KEY_AVERAGE_CYCLES_PER_YEAR).toString());



                                int egtMargin = redLineTemperature - CurrentEGT;
                                int remainingCycles = egtMargin * cyclesPerDegree;
                                int shopVisitYear = Calendar.getInstance().get(Calendar.YEAR) + ((egtMargin * cyclesPerDegree) / averageCyclesPerYear);
                                EGTResult egtResult = new EGTResult(designation, thrustInK, egtMargin, remainingCycles, shopVisitYear);


                                mEGTResult.add(egtResult);
                            }
                            EGTMarginDetailActivity egtMarginDetailActivity = (EGTMarginDetailActivity) context;
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

    private void addEngineIfNotExists(final String ESN, final String EngineModel) {
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
                            if (found == false) {
                                // Add this ESN to the list of engines
                                Map<String, Object> mEngine = new HashMap<>();
                                mEngine.put(Constants.KEY_ESN, ESN);
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

    private void CreateDesignations() {
        addDesignation("CFM56-5B","3/P",32,940,1180,500);
        addDesignation("CFM56-5B","4/P",27,990,1407,500);
        addDesignation("CFM56-5B","7/P",27,990,1471,500);
        addDesignation("CFM56-5B","9/P",23,1030,2300,500);
        addDesignation("CFM56-5B","5/P",22,1040,1473,500);
        addDesignation("CFM56-5B","8/P",22,1040,2295,500);
        addDesignation("CFM56-7B","27/3",27,950,806,500);
        addDesignation("CFM56-7B","27/3B1",27,950,552,500);
        addDesignation("CFM56-7B","26/3",26,960,1276,500);
        addDesignation("CFM56-7B","26/3B1",26,960,1512,500);
        addDesignation("CFM56-7B","24/3",24,980,1619,500);
        addDesignation("CFM56-7B","24/3B1",24,980,600,500);
        addDesignation("CFM56-7B","24/B1",24,980,800,500);
        addDesignation("CFM56-7B","22/3",23,990,1349,500);
        addDesignation("CFM56-7B","20/3",21,1010,1500,500);
        addDesignation("LEAP-1A","35A",32,1023,1000,500);
        addDesignation("LEAP-1A","33B2",32,1023,1200,500);
        addDesignation("LEAP-1A","30",32,1023,1500,500);
        addDesignation("LEAP-1A","32",32,1023,1288,500);
        addDesignation("LEAP-1A","33",32,1023,818,500);
        addDesignation("LEAP-1A","26E1",27,1073,1300,500);
        addDesignation("LEAP-1A","26",27,1073,1574,500);
        addDesignation("LEAP-1A","24E1",24,1103,1100,500);
        addDesignation("LEAP-1A","24",24,1103,1500,500);
        addDesignation("LEAP-1A","23",24,1103,1400,500);
        addDesignation("LEAP-1B","28/B1",29,1032,1700,500);
        addDesignation("LEAP-1B","28/B2",29,1032,1800,500);
        addDesignation("LEAP-1B","28/B3",29,1032,1900,500);
        addDesignation("LEAP-1B","28",29,1032,928,500);
        addDesignation("LEAP-1B","27",28,1042,864,500);
        addDesignation("LEAP-1B","25",27,1052,535,500);
        addDesignation("LEAP-1B","23",24,1082,1000,500);
        addDesignation("LEAP-1B","21",23,1092,900,500);


    }

    private void addDesignation(
            String engine_model,
            String engine_designation,
            int thrust_in_k,
            int red_line_temperature,
            int average_cycles_per_year,
            int cycles_per_degree
    ) {
        // Create a new user with a first and last name
        Map<String, Object> mEngineDesignationRecord = new HashMap<>();
        mEngineDesignationRecord.put(Constants.KEY_ENGINE_MODEL, engine_model);
        mEngineDesignationRecord.put(Constants.KEY_ENGINE_DESIGNATION, engine_designation);
        mEngineDesignationRecord.put(Constants.KEY_THRUST_IN_K, thrust_in_k);
        mEngineDesignationRecord.put(Constants.KEY_AVERAGE_CYCLES_PER_YEAR, average_cycles_per_year);
        mEngineDesignationRecord.put(Constants.KEY_CYCLES_PER_DEGREE, cycles_per_degree);
        mEngineDesignationRecord.put(Constants.KEY_RED_LINE_TEMPERATURE, red_line_temperature);


        db.collection("engine_designation")
                .add(mEngineDesignationRecord)
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
