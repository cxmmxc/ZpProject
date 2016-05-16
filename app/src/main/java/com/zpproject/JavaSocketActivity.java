package com.zpproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class JavaSocketActivity extends AppCompatActivity {

    EditText zp_edit;
    Button btn, volley_btn;
    TextView zp_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewAndData();
        initListener();
    }

    private void initListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        volley_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JavaSocketActivity.this, VolleyActivity.class));
            }
        });
    }

    private void sendMessage() {
        String msg = zp_edit.getEditableText().toString();
        new NetAsyTask().execute(msg);
    }

    private void initViewAndData() {
        zp_edit = (EditText) findViewById(R.id.zp_edit);
        btn = (Button) findViewById(R.id.btn);
        zp_txt = (TextView) findViewById(R.id.zp_txt);
        volley_btn = (Button) findViewById(R.id.volley_btn);
    }

    class NetAsyTask extends AsyncTask<String, Void, String> {

        Socket socket = null;
        String readLine = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(JavaSocketActivity.this);
            dialog.setMessage("loading");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(String[] params) {
            try {
                socket = new Socket("192.168.0.26", 18888);
                PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                writer.println(params[0]);
//                InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                BufferedReader bfReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "gb2312"));
                writer.close();
                bfReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return readLine;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (s != null) {
                Log.e("cxm", "s=" + s);
                zp_txt.setText(s);
            } else {
                Toast.makeText(getApplicationContext(), "errer", Toast.LENGTH_SHORT);
            }
        }
    }

}
