package com.rollforinitiative.agileplanner.presentation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class WhiteboardAdderFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText textBox = new EditText(getContext());

        //add title
        builder.setTitle("Add Whiteboard");

        //add a text field
        textBox.setHint("Name");
        textBox.setSingleLine();
        builder.setView(textBox);

        // add  buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(this.getClass().getSimpleName(), "value of the text box: " + textBox.getText());
                ((ProjectDetailActivity) getActivity()).addWhiteboard(textBox.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });


        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
