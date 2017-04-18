package ku.eecscap.team8.auricle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;

import java.util.zip.Inflater;

/**
 * Created by Austin Kurtti on 4/3/2017.
 * Last Edited by Austin Kurtti on 4/12/2017
 */

public class PostRecord {

    Auricle mApp;
    private Activity mContext;

    public PostRecord(Auricle app, Activity context) {
        mApp = app;
        mContext = context;
    }

    public void show() {
        // Inflate layout into view for reference in the positive button handler
        LayoutInflater inflater = mContext.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.post_record, null);

        // Configure filename edit to enable Save button when valid
        final EditText clipFilename = (EditText) dialogView.findViewById(R.id.post_record_filename);

        // Configure seek bar
        final RangeBar rangeBar = (RangeBar) dialogView.findViewById(R.id.post_record_rangebar);
        rangeBar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String value) {
                return value + "%";
            }
        });

        // Disable orientation changes to prevent parent activity reinitialization
        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(R.string.post_record_dialog_title)
                .setView(dialogView)
                .setPositiveButton(R.string.save, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Save the clip with the entered filename
//                        EditText clipFilename = (EditText) dialogView.findViewById(R.id.post_record_filename);
                        String filename = clipFilename.getText().toString();
                        mApp.saveRecordingAs(filename);

                        // Close dialog
                        dialogInterface.dismiss();

                        // Restore device orientation detection
                        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Disable Save button initially; allow valid filename to enable it
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        clipFilename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
                else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });
    }
}
