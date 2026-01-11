package com.example.student_assistant.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_assistant.R;
import com.example.student_assistant.adapters.HistoryAdapter;
import com.example.student_assistant.models.HistoryItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SavedHistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private List<HistoryItem> historyItems;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_history);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        historyRecyclerView = findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyItems = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyItems);
        historyRecyclerView.setAdapter(historyAdapter);

        loadHistoryData();
    }

    private void loadHistoryData() {
        String userId = mAuth.getCurrentUser().getUid();

        // The orderBy clause has been removed to avoid the composite index requirement.
        // Note: The history will not be sorted by date.
        db.collection("history")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        historyItems.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HistoryItem item = document.toObject(HistoryItem.class);
                            historyItems.add(item);
                        }
                        historyAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(SavedHistoryActivity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}