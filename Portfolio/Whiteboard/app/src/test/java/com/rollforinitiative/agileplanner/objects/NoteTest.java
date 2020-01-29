package com.rollforinitiative.agileplanner.objects;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteTest {

    @Test
    public void setTitle() {
        String title = "New Note";
        Note note = new Note("");
        note.setTitle(title);

        assertEquals(note.getTitle(), title);
    }

    @Test
    public void setText() {
        String data = "new data to record via a note object";
        Note note = new Note("New Note");
        note.setText(data);

        assertEquals(note.getText(), data);
    }
}