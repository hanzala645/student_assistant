package com.example.student_assistant.models;

public class HistoryItem {
    private String originalText;
    private String correctedText;
    private String summarizedText;
    private long timestamp;

    public HistoryItem() {}

    public HistoryItem(String originalText, String correctedText, String summarizedText, long timestamp) {
        this.originalText = originalText;
        this.correctedText = correctedText;
        this.summarizedText = summarizedText;
        this.timestamp = timestamp;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getCorrectedText() {
        return correctedText;
    }

    public String getSummarizedText() {
        return summarizedText;
    }

    public long getTimestamp() {
        return timestamp;
    }
}