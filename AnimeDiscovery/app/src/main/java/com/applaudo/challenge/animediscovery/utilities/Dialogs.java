package com.applaudo.challenge.animediscovery.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import com.applaudo.challenge.animediscovery.R;

public class Dialogs {

    //Global Variables
    private Context mContext;
    private AlertDialog mAlertDialog;

    //Constructor, getting context
    public Dialogs(Context mContext)
    {
        this.mContext = mContext;
    }

    //Show alert dialog
    public void showAlertDialog(String title, String message, Boolean status, String buttonText){
        try {
            mAlertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom).create();

            mAlertDialog.setTitle(title);

            mAlertDialog.setMessage(message);

            mAlertDialog.setIcon((status) ? R.drawable.ic_success : R.drawable.ic_fail);

            mAlertDialog.setCanceledOnTouchOutside(false);

            mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, buttonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            mAlertDialog.show();
        }catch (Exception e){
            Log.e("Exception",e.getMessage());
        }
    }
}
