package com.example.student_assistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_assistant.R;
import com.example.student_assistant.models.HistoryItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryItem> historyItems;

    public HistoryAdapter(List<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem item = historyItems.get(position);
        holder.originalText.setText(item.getOriginalText());

        if (item.getCorrectedText() != null) {
            holder.processedText.setText(item.getCorrectedText());
        } else if (item.getSummarizedText() != null) {
            holder.processedText.setText(item.getSummarizedText());
        }

        // Format and display the timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(new Date(item.getTimestamp()));
        holder.timestampText.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView originalText, processedText, timestampText;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            originalText = itemView.findViewById(R.id.original_text);
            processedText = itemView.findViewById(R.id.processed_text);
            timestampText = itemView.findViewById(R.id.timestamp_text);
        }
    }
}