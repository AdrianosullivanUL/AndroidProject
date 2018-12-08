package com.example.maryc.egt;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TempTableRowInsert {

    public void doWork() {
        String engineRecordId = "";
        // TODO: Populate EngineRecordId

        // final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);

        DBHelper dbHelper = new DBHelper();

        EngineRecord engineRecord = dbHelper.getEngineRecord(engineRecordId);

        List<EGTResult> egtResults = new ArrayList<EGTResult>();
        egtResults = dbHelper.generateResults(engineRecordId);
        for (EGTResult egtResult : egtResults) {

            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView textThrusts = new TextView(this);
            textThrusts.setText(egtResult.getmThrust());
            textThrusts.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView textEGTMargin = new TextView(this);
            textEGTMargin.setText(egtResult.getmEGTMargin());
            textEGTMargin.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView textRemainingCycles = new TextView(this);
            textRemainingCycles.setText(egtResult.getmRemainingCycles());
            textRemainingCycles.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView textShopVisitYear = new TextView(this);
            textShopVisitYear.setText(egtResult.getmShopVisitYear());
            textShopVisitYear.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


            tableRow.addView(textThrusts);
            tableRow.addView(textEGTMargin);
            tableRow.addView(textRemainingCycles);
            tableRow.addView(textShopVisitYear);

            tableLayout.addView(tableRow);
        }

    }
}
