package com.rollforinitiative.agileplanner.presentation;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.rollforinitiative.agileplanner.R;
import com.rollforinitiative.agileplanner.business.MagnetLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class MagnetAdderFragment extends DialogFragment {
    MagnetLoader loader;
    String[] magnetIcons;
    int index = 0;
    Context fragmentContext;

    //lifecycle hook that happens before fragment is shown
    // use this to load assets from the apk
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loader = new MagnetLoader(getActivity());
        magnetIcons = loader.getPrettyNames();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText textBox = new EditText(getContext());

        //add title
        builder.setTitle("Select Magnet Icon and Name");

        //add a text field
        textBox.setHint("Name");
        textBox.setSingleLine();
        textBox.setId(R.id.magnet_name_edit);
        builder.setView(textBox);

        // add  buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(this.getClass().getSimpleName(), "value of the text box: " + textBox.getText());
                ListView lw = ((AlertDialog)dialog).getListView();
                int selectedIndex = lw.getCheckedItemPosition();
                ((ProjectDetailActivity) getActivity()).addMagnet(textBox.getText().toString(), loader.getNameAtIndex(selectedIndex));
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setSingleChoiceItems(magnetIcons, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(this.getClass().getSimpleName(), "clicked on " + which);
            }
        });


        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        //disable the okay button if text is not entered
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        //set listener for changes to the text box so that the button can be re-enabled when
        // text is entered
        EditText magnetNameText = getDialog().findViewById(R.id.magnet_name_edit);
        magnetNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //if text has been entered then enable the button, if the text is "" again, disable
                // the button again
                if (TextUtils.isEmpty(s)) {
                    // Disable ok button
                    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                } else {
                    // Something into edit text. Enable the button.
                    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }

            }
        });
    }
}
