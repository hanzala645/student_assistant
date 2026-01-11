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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GrammarCheckerActivity extends AppCompatActivity {

    private EditText textToCheck;
    private TextView correctedText;
    private Button checkGrammarButton, saveButton, scanButton;
    private ImageButton copyButton;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private OkHttpClient client;

    private final ActivityResultLauncher<Intent> scanTextLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String scannedText = result.getData().getStringExtra("scannedText");
                    if (scannedText != null) {
                        textToCheck.setText(scannedText);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_checker);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        client = new OkHttpClient();

        textToCheck = findViewById(R.id.text_to_check);
        correctedText = findViewById(R.id.corrected_text);
        checkGrammarButton = findViewById(R.id.check_grammar_button);
        saveButton = findViewById(R.id.save_button);
        scanButton = findViewById(R.id.scan_button);
        copyButton = findViewById(R.id.copy_button);

        checkGrammarButton.setOnClickListener(v -> checkGrammar());

        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ScanTextActivity.class);
            scanTextLauncher.launch(intent);
        });

        copyButton.setOnClickListener(v -> {
            String textToCopy = correctedText.getText().toString();
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

    private void checkGrammar() {
        String text = textToCheck.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Please enter text to check.", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("text", text)
                .add("language", "en-US")
                .build();

        Request request = new Request.Builder()
                .url("https://api.languagetool.org/v2/check")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> correctedText.setText("Error: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resultText = parseCorrections(response.body().string(), text);
                    runOnUiThread(() -> correctedText.setText(resultText));
                } else {
                    runOnUiThread(() -> correctedText.setText("Error: " + response.message()));
                }
            }
        });
    }

    private String parseCorrections(String jsonResponse, String originalText) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray matches = json.getJSONArray("matches");

            StringBuilder corrected = new StringBuilder(originalText);

            for (int i = matches.length() - 1; i >= 0; i--) {
                JSONObject match = matches.getJSONObject(i);
                JSONArray replacements = match.getJSONArray("replacements");
                if (replacements.length() > 0) {
                    String replacement = replacements.getJSONObject(0).getString("value");
                    int from = match.getInt("offset");
                    int to = from + match.getInt("length");
                    corrected.replace(from, to, replacement);
                }
            }
            return corrected.toString();
        } catch (Exception e) {
            return "Error parsing corrections: " + e.getMessage();
        }
    }

    private void saveResultToFirestore() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String original = textToCheck.getText().toString();
        String corrected = correctedText.getText().toString();
        String userId = mAuth.getCurrentUser().getUid();

        if (original.isEmpty() || corrected.isEmpty()) {
            Toast.makeText(this, "Nothing to save.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> historyItem = new HashMap<>();
        historyItem.put("userId", userId);
        historyItem.put("originalText", original);
        historyItem.put("correctedText", corrected);
        historyItem.put("timestamp", System.currentTimeMillis());

        db.collection("history").add(historyItem)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(GrammarCheckerActivity.this, "Saved successfully.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(GrammarCheckerActivity.this, "Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}