package com.example.maryc.egt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
    }

    private void addResult() {
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



        engine_model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                final List<String> mEngineConfigurations = mDBHelper.getEngineConfigurationsForEngineModel(null);
                ArrayAdapter<String> EngineDesignationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mEngineConfigurations);
                EngineDesignationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                engine_designation_spinner.setAdapter(EngineDesignationAdapter);




                engine_model_spinner.setAdapter(mEngineDesignationAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mESN = esn_edittext.getText().toString();
                String mEngineModel = engine_model_spinner.getSelectedItem().toString();
                String mEngineDesignation = engine_designation_spinner.getSelectedItem().toString();

                int mCurrentEGT_edittext = Integer.parseInt(current_egt_edittext.getText().toString());
                int mTimeSinceLastSV = Integer.parseInt(time_since_last_sv_edittext.getText().toString());
                int mTimeSinceNew = Integer.parseInt(time_since_new_edittext.getText().toString());
                int mCyclesSinceNew = Integer.parseInt(cycles_since_new_edittext.getText().toString());


                // todo add method

            }

        });

        builder.create().show();
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
