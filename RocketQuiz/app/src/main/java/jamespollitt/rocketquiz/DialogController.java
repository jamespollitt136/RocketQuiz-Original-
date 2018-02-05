package jamespollitt.rocketquiz;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogController {

    /**
     * The sole purpose of this method is to create and display an AlertDialog, whenever it is needed.
     * This method has been created to stop the reuse of code throughout. The AlertDialog follows the
     * simple convention below:
     * Title
     * Message
     * OK
     * @param title The title of the AlertDialog, displays the error type.
     * @param message The message of the AlertDialog, displays an explanation of the occurred error.
     * @param context The context of the calling activity.
     */
    public void okDialog(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * The sole purpose of this method is to create and display an AlertDialog, whenever it is needed.
     * This method has been created to stop the reuse of code throughout. The AlertDialog follows the
     * simple convention below:
     * Title
     * Message
     * OK
     * @param context The context of the calling activity.
     */
    public void missingFieldsDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Missing Fields");
        builder.setMessage("All fields must be completed.");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //TODO: Custom dialogs
}
