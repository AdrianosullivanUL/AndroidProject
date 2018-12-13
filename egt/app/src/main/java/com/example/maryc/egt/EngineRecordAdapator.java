package com.example.maryc.egt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EngineRecordAdapator extends RecyclerView.Adapter<EngineRecordAdapator.EngineRecordViewHolder> {
    private List<DocumentSnapshot> mEngineRecords = new ArrayList<>();

    public EngineRecordAdapator(String ESN)
    {
        CollectionReference engineRecordCollectionRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_ENGINE_RECORD);
        engineRecordCollectionRef
                .whereEqualTo("ESN", ESN)
                .orderBy(Constants.KEY_RECORD_DATE
                        ,Query.Direction.DESCENDING)
                .limit(50)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(Constants.TAG, "Listening Failed");
                            return;
                        }
                        mEngineRecords = queryDocumentSnapshots.getDocuments();
                        notifyDataSetChanged();

                    }
                });
    }
    @NonNull
    @Override
    public EngineRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.engine_record_itemview, parent, false);
        return new EngineRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EngineRecordViewHolder engineRecordViewHolder, int i) {
        DocumentSnapshot ds = mEngineRecords.get(i);
        String ESN = (String) ds.get(Constants.KEY_ESN);
        String ModelAndDesig = (String) ds.get(Constants.KEY_ENGINE_MODEL) + (String) ds.get(Constants.KEY_ENGINE_DESIGNATION);
        Date date = (Date)ds.get(Constants.KEY_RECORD_DATE);String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateFmt = simpleDateFormat.format(date);
        String egt = (String) ds.get(Constants.KEY_CURRENT_EGT).toString();
        engineRecordViewHolder.mESNTextView.setText(ESN);
        engineRecordViewHolder.mModelDesigTextView.setText(ModelAndDesig);
        engineRecordViewHolder.mDate.setText(dateFmt);
        engineRecordViewHolder.mEgt.setText(egt);

        //engineRecordViewHolder.mDate.setText();
    }

    @Override
    public int getItemCount() {
        return mEngineRecords.size();
    }

    class EngineRecordViewHolder extends RecyclerView.ViewHolder {
        private TextView mESNTextView;
        private TextView mModelDesigTextView;
        private TextView mDate;
        private TextView mEgt;


        public EngineRecordViewHolder(@NonNull final View itemView) {
            super(itemView);
            mESNTextView = itemView.findViewById(R.id.itemview_ESN);
            mModelDesigTextView = itemView.findViewById(R.id.itemview_engine_model_and_desig);

            mDate = itemView.findViewById(R.id.itemview_date);
            mEgt =  itemView.findViewById(R.id.itemview_current_egt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentSnapshot ds = mEngineRecords.get(getAdapterPosition());
                    //mESNTextView.setText(ds.get(Constants.KEY_ESN));
                    Context c = itemView.getContext();
                    Intent intent = new Intent(c,EGTMarginDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_DOCUMENT_ID, ds.getId());
                    c.startActivity(intent);
                }
            });

        }
    }
}
