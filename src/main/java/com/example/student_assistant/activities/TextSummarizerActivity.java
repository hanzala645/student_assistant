package com.example.student_assistant.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_assistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextSummarizerActivity extends AppCompatActivity {

    private EditText textToSummarize;
    private TextView summarizedText;
    private Button summarizeButton, saveButton, scanButton;
    private ImageButton copyButton;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private final ActivityResultLauncher<Intent> scanTextLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String scannedText = result.getData().getStringExtra("scannedText");
                    if (scannedText != null) {
                        textToSummarize.setText(scannedText);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_summarizer);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        textToSummarize = findViewById(R.id.text_to_summarize);
        summarizedText = findViewById(R.id.summarized_text);
        summarizeButton = findViewById(R.id.summarize_button);
        saveButton = findViewById(R.id.save_button);
        scanButton = findViewById(R.id.scan_button);
        copyButton = findViewById(R.id.copy_button);

        summarizeButton.setOnClickListener(v -> {
            String text = textToSummarize.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(TextSummarizerActivity.this, "Please enter some text.", Toast.LENGTH_SHORT).show();
                return;
            }
            String summary = summarize(text, 3);
            summarizedText.setText(summary);
        });

        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ScanTextActivity.class);
            scanTextLauncher.launch(intent);
        });

        copyButton.setOnClickListener(v -> {
            String textToCopy = summarizedText.getText().toString();
            if (!textToCopy.isEmpty()) {
                copyToClipboard(textToCopy);
            }
        });

        saveButton.setOnClickListener(v -> saveResultToFirestore());
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void saveResultToFirestore() {
        String original = textToSummarize.getText().toString();
        String summarized = summarizedText.getText().toString();
        String userId = mAuth.getCurrentUser().getUid();

        if (original.isEmpty() || summarized.isEmpty()) {
            Toast.makeText(this, "Nothing to save.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> historyItem = new HashMap<>();
        historyItem.put("userId", userId);
        historyItem.put("originalText", original);
        historyItem.put("summarizedText", summarized);
        historyItem.put("timestamp", System.currentTimeMillis());

        db.collection("history").add(historyItem)
                .addOnSuccessListener(documentReference -> Toast.makeText(TextSummarizerActivity.this, "Saved successfully.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(TextSummarizerActivity.this, "Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public static String summarize(String text, int maxSentences) {
        List<String> sentences = splitSentences(text);
        if (sentences.size() <= maxSentences) {
            return text;
        }
        Map<String, Integer> wordFrequency = buildWordFrequency(text);
        Map<String, Double> sentenceScores = scoreSentences(sentences, wordFrequency);
        sentences.sort((a, b) -> Double.compare(sentenceScores.get(b), sentenceScores.get(a)));
        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < maxSentences; i++) {
            summary.append(sentences.get(i)).append(" ");
        }
        return summary.toString().trim();
    }

    private static List<String> splitSentences(String text) {
        return Arrays.asList(text.split("(?<=[.!?])\\s+"));
    }

    private static Map<String, Integer> buildWordFrequency(String text) {
        Map<String, Integer> freq = new HashMap<>();
        for (String word : text.toLowerCase().split("\\W+")) {
            if (word.length() > 3) {
                freq.put(word, freq.getOrDefault(word, 0) + 1);
            }
        }
        return freq;
    }

    private static Map<String, Double> scoreSentences(List<String> sentences, Map<String, Integer> wordFreq) {
        Map<String, Double> scores = new HashMap<>();
        for (String sentence : sentences) {
            double score = 0;
            for (String word : sentence.toLowerCase().split("\\W+")) {
                score += wordFreq.getOrDefault(word, 0);
            }
            scores.put(sentence, score);
        }
        return scores;
    }
}