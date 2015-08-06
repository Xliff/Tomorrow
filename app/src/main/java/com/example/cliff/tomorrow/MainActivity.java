package com.example.cliff.tomorrow;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.lang.Runtime.*;

/**
 * Created by Cliff on 8/5/2015.
 */
public class MainActivity extends FragmentActivity {
    private String TAG = "com.example.cliff.tomorrow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.sample_main);

        Calendar c = Calendar.getInstance();

        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());

            /*
            Log.d(TAG, "BYear: " + c.get(Calendar.YEAR));
            Log.d(TAG, "BMonth: " + c.get(Calendar.MONTH) + 1);
            Log.d(TAG, "BDay: " + c.get(Calendar.DAY_OF_MONTH));
            Log.d(TAG, "BHour: " + c.get(Calendar.HOUR));
            Log.d(TAG, "BMinute: " + c.get(Calendar.MINUTE));
            Log.d(TAG, "BSecond: " + c.get(Calendar.SECOND));
            */

            c.add(Calendar.DATE, 1);

            /*
            Log.d(TAG, "AYear: " + c.get(Calendar.YEAR));
            Log.d(TAG, "AMonth: " + c.get(Calendar.MONTH) + 1);
            Log.d(TAG, "ADay: " + c.get(Calendar.DAY_OF_MONTH));
            Log.d(TAG, "AHour: " + c.get(Calendar.HOUR));
            Log.d(TAG, "AMinute: " + c.get(Calendar.MINUTE));
            Log.d(TAG, "ASecond: " + c.get(Calendar.SECOND));
            */

            String command = "date -s " +
                c.get(Calendar.YEAR) +
                String.format("%02d", c.get(Calendar.MONTH) + 1) +
                String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "." +
                String.format("%02d", c.get(Calendar.HOUR)) +
                String.format("%02d", c.get(Calendar.MINUTE)) +
                String.format("%02d", c.get(Calendar.SECOND)) + "\n";

            os.writeBytes(command);
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            p.waitFor();

            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
            Toast.makeText(
                    getApplicationContext(),
                    "Date set to: " + f.format(c.getTime()),
                    Toast.LENGTH_LONG
            ).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
    }

}
