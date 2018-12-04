package com.example.maryc.egt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper mDBHelper;
    List<EngineModel> mEngineModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDBHelper = new DBHelper();
        mDBHelper.populateEngineModels();
        mDBHelper.populateEngineDesignations();

        RecyclerView recycleView = findViewById(R.id.recycler_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setHasFixedSize(true);
        EngineRecordAdapator engineRecordAdapator = new EngineRecordAdapator();
        recycleView.setAdapter(engineRecordAdapator);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEngineRecord();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
    }

    private void addNewEngineRecord() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Engine Record");
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        builder.setView(view);

        final EditText esn_edittext = view.findViewById(R.id.esn_edittext);
        final Spinner engine_model_spinner = view.findViewById(R.id.engine_model_spinner);
        final Spinner engine_designation_spinner = view.findViewById(R.id.engine_designation_spinner);
        final EditText current_egt_edittext = view.findViewById(R.id.current_egt_edittext);
        final EditText time_since_last_sv_edittext = view.findViewById(R.id.time_since_last_sv_edittext);
        final EditText time_since_new_edittext = view.findViewById(R.id.time_since_new_edittext);
        final EditText cycles_since_new_edittext = view.findViewById(R.id.cycles_since_new_edittext);


        // Add Engine Models to the Array List
        final List<String> mEngineModels = mDBHelper.getEngineModels();
        ArrayAdapter<String> engineModelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mEngineModels);
        engineModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        engine_model_spinner.setAdapter(engineModelAdapter);

        ArrayAdapter<String> EngineDesignationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mEngineModels);
        EngineDesignationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        engine_model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                List<String> mEngineConfigurations = mDBHelper.getEngineConfigurationsForEngineModel(engine_model_spinner.getSelectedItem().toString());
                ArrayAdapter<String> EngineDesignationAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, mEngineConfigurations);
                EngineDesignationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                engine_designation_spinner.setAdapter(EngineDesignationAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // temp code to test validation cycle
        builder.setCancelable(false);

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });

        //builder.create().show();
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EngineDesignation engineDesignation = null;
                boolean errorFound = false;
                // Process eSN
                String mESN = esn_edittext.getText().toString();
                if (mESN.length() == 0) {
                    esn_edittext.requestFocus();
                    esn_edittext.setError("ESN cannot be empty");
                    errorFound = true;
                }

                // Process Engine Model
                String mEngineModel = engine_model_spinner.getSelectedItem().toString();
                if (mEngineModel.length() == 0) {
                    esn_edittext.requestFocus();
                    esn_edittext.setError("Engine Model cannot be empty");
                    errorFound = true;
                }

                // Process Engine Designation
                String mEngineDesignation = engine_designation_spinner.getSelectedItem().toString();
                if (mEngineDesignation.length() == 0) {
                    esn_edittext.requestFocus();
                    esn_edittext.setError("Engine Designation cannot be empty");
                    errorFound = true;
                } else {
                    engineDesignation = mDBHelper.getEngineDesignation(mEngineModel, mEngineDesignation);
                }

                // Process Current EGT
                int mCurrentEGT = 0;
                boolean mCurrentEGTError = false;
                try {
                    mCurrentEGT = Integer.parseInt(current_egt_edittext.getText().toString());
                } catch (Exception ex) {
                    current_egt_edittext.requestFocus();
                    current_egt_edittext.setError("Current EGT cannot be empty");
                    mCurrentEGTError = true;
                    errorFound = true;
                }
                if (mCurrentEGT <= 0 && mCurrentEGTError == false) {
                    current_egt_edittext.requestFocus();
                    current_egt_edittext.setError("Current EGT must be a positive number");
                    errorFound = true;
                }

                // Process Time Since Last SV
                int mTimeSinceLastSV = 0;
                boolean mTimeSinceLastSVError = false;
                try {
                    mTimeSinceLastSV = Integer.parseInt(time_since_last_sv_edittext.getText().toString());
                } catch (Exception ex) {
                    time_since_last_sv_edittext.requestFocus();
                    time_since_last_sv_edittext.setError("Time Since Last SV must be a positive number");
                    mTimeSinceLastSVError = true;
                    errorFound = true;
                }
                if (mTimeSinceLastSV <= 0 && mTimeSinceLastSVError == false) {
                    time_since_last_sv_edittext.requestFocus();
                    time_since_last_sv_edittext.setError("Time Since Last SV must be a positive number");
                    errorFound = true;
                }

                // Porcess Time Since New
                int mTimeSinceNew = 0;
                boolean mTimeSinceNewError = false;
                try {
                    mTimeSinceNew = Integer.parseInt(time_since_new_edittext.getText().toString());
                } catch (Exception ex) {
                    time_since_new_edittext.requestFocus();
                    time_since_new_edittext.setError("Time Since New must be a positive number");
                    mTimeSinceNewError = true;
                    errorFound = true;
                }
                if (mTimeSinceNew <= 0 && mTimeSinceNewError == false) {
                    time_since_new_edittext.requestFocus();
                    time_since_new_edittext.setError("Time Since New must be a positive number");
                    errorFound = true;
                }

                // Process Cycles Since New
                int mCyclesSinceNew = 0;
                boolean mCyclesSinceNewError = false;
                try {
                    mCyclesSinceNew = Integer.parseInt(cycles_since_new_edittext.getText().toString());
                } catch (Exception ex) {
                    cycles_since_new_edittext.requestFocus();
                    cycles_since_new_edittext.setError("Cycles Since New must be a positive number");
                    mCyclesSinceNewError = true;
                    errorFound = true;
                }
                if (mCyclesSinceNew <= 0 && mCyclesSinceNewError == false) {
                    cycles_since_new_edittext.requestFocus();
                    cycles_since_new_edittext.setError("Cycles Since New must be a positive number");
                    errorFound = true;
                }
                if (errorFound == false) {
                    Date mRecordDate = new Date();
                    String mOperator = ""; //operator_edittext.getText().toString();
                    EngineRecord mEngineRecord;
                    mEngineRecord = new EngineRecord();

                    mEngineRecord = new EngineRecord(mESN,
                            mRecordDate,
                            mEngineModel,
                            mOperator,
                            engineDesignation.getConfiguration(),
                            engineDesignation.getThrustInK(),
                            mCurrentEGT,
                            mTimeSinceLastSV,
                            mTimeSinceNew,
                            mCyclesSinceNew, 0);

                    mDBHelper.addEngineRecord( mEngineRecord);
                    alertDialog.dismiss();
                    // MC TODO Launch result dialog from here
                }


            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

