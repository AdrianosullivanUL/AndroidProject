package com.example.maryc.egt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class EGTMarginDetailActivity extends AppCompatActivity {
    private TextView mESNTextView;
    private TextView mModelDesigTextView;
    private TextView mDate;
    private TextView mEgt;
    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egtmargin_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent receivedIntent = getIntent();
        String docId = receivedIntent.getStringExtra(Constants.EXTRA_DOCUMENT_ID);
       mESNTextView  = findViewById(R.id.ESN_detail_textview);
       mModelDesigTextView = findViewById(R.id.Type_detail_textview);
       mEgt = findViewById(R.id.Current_egt_detail_textview);
       populateTable(docId);

      // Temp code only
       // mModelDesigTextView.setText(docId);
        mDocRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_ENGINE_RECORD).document(docId);
        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(Constants.TAG, "Listen failed");
                    return;
                }
                if (documentSnapshot.exists()) {
                    mDocSnapshot = documentSnapshot;
                    mESNTextView.setText((String) documentSnapshot.get(Constants.KEY_ESN));
                    mModelDesigTextView.setText((String) documentSnapshot.get(Constants.KEY_ENGINE_MODEL));
                    mEgt.setText((String) documentSnapshot.get(Constants.KEY_CURRENT_EGT).toString());
                }

            }
        });

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
            //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
              //          .setAction("Action", null).show();
          //  }
       // });
    }
    @Override
   public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_egt_detail, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                // TODO delete EGT Entry and close activity
                //mDocRef.delete();
                //finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateTable(String engineRecordId) {


        // TODO: Populate EngineRecordId

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout_EGT_detail);

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DBHelper dbHelper = new DBHelper(this,db);

        EngineRecord engineRecord = dbHelper.getEngineRecord(engineRecordId);

        List<EGTResult> egtResults = new ArrayList<EGTResult>();
        egtResults = dbHelper.generateResults(engineRecordId);
        for (EGTResult egtResult : egtResults) {

            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView textThrusts = new TextView(this);
            textThrusts.setText(String.format("%2d",egtResult.getThrust()));
            textThrusts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView textEGTMargin = new TextView(this);
            textEGTMargin.setText(String.format("%2d",egtResult.getEGTMargin()));
            textEGTMargin.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView textRemainingCycles = new TextView(this);
            textRemainingCycles.setText(String.format("%2d",egtResult.getRemainingCycles()));
            textRemainingCycles.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView textShopVisitYear = new TextView(this);
            textShopVisitYear.setText(String.format("%2d",egtResult.getShopVisitYear()));
            textShopVisitYear.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            tableRow.addView(textThrusts);
            tableRow.addView(textEGTMargin);
            tableRow.addView(textRemainingCycles);
            tableRow.addView(textShopVisitYear);

            tableLayout.addView(tableRow);

        }

    }



}
