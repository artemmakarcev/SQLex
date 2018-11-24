package com.example.artemmakarcev.sqlex;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class UserActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";
    private TextView mTextJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mTextJson = (TextView) findViewById(R.id.json);
//        Intent intent = getIntent();
//        String token = intent.getStringExtra("token");
//        Log.d("my_log", token);

        new UserInfo().execute();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class UserInfo extends AsyncTask<Void, Void, String> {

        HttpsURLConnection conn = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                /**
                 * Настройка подключения
                 */
                Intent intent = getIntent();
                String token = intent.getStringExtra("token");
                Log.d("my_log", "token " + token);

                URL url = new URL("https://sql.ma-dev.cloud/api/v1/user");
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", token);
//                conn.setRequestProperty("Accept","Authorization");
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
                conn.connect();
                /**
                 * Запись полученных данных в строку
                 */
                InputStream inputStream = conn.getInputStream();
                StringBuilder builder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                resultJson = builder.toString();

                Log.d("my_log", String.valueOf(conn.getResponseCode()));
                Log.d("my_log" , conn.getResponseMessage());
                Log.d("my_log", "Полученные данные " + resultJson);

                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
            // TODO: register the new account here.
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
//            mAuthTask = null;
//            showProgress(false);
            Log.d("my_log", strJson);
            mTextJson.setText(strJson);
            try {
                JSONObject jsonObject = new JSONObject(strJson);
//                String login = jsonObject.getString("login");
//                String token = jsonObject.getString("access_token");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //            if (success) {
////                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
        }

    }
}
