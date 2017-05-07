package com.myfridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.myfridge.barcodereader.BarcodeCaptureActivity;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "ItemDetail";

    private EditText barcodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        barcodeValue = (EditText) findViewById(R.id.barcode_value);

        findViewById(R.id.scan_button).setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scan_button) {

            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            startActivityForResult(intent, RC_BARCODE_CAPTURE);

            // launch barcode activity.
//            scanBarcode();
        }

    }
    protected void scanBarcode(){
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }


    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
//                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.barcode_failure, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {

                Toast.makeText(getApplicationContext(), R.string.barcode_error, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), CommonStatusCodes.getStatusCodeString(resultCode), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
