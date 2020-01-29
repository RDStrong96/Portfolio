package com.rollforinitiative.agileplanner.objects;

public class Note implements IMagnet {
    private String title;
    private String text;

    public Note(String title) {
        this.title = title;
        text = "";
    }

    public String displayMagnet() {
        return "note_icon120-120";
    }

    public void showDetailedInfo() {
        // launch new activity?
    }

    public void setTitle(String title) { this.title = title; }
    public void setText(String text) { this.text = text; }

    public String getTitle() { return this.title; }
    public String getText() { return this.text; }
}