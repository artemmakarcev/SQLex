package com.example.artemmakarcev.sqlex;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int REQUEST_READ_CONTACTS = 0;
    //    private static final String[] DUMMY_CREDENTIALS = new String[]{
    private EditText mLoginView;
    // UI references.
    private EditText mPasswordView;
    //    private UserLoginTask mAuthTask = null;
    private View mProgressView;
    //    };
    private View mLoginFormView;
    //            "foo@example.com:hello", "bar@example.com:world"
    public static String LOG_TAG = "my_log";
    private CheckBox mSaveLogin;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginView =  findViewById(R.id.login);
//        populateAutoComplete();
        mPasswordView =  findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSaveLogin = findViewById(R.id.autoLogin);
        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        loginPrefsEditor.apply();
        if (loginPreferences.getString("username", "") != ""){
            mLoginView.setText(loginPreferences.getString("username", ""));
//            mSaveLogin.isChecked();
            mSaveLogin.setChecked(true);
        }

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void Enter(View view) {
        Log.d(LOG_TAG, "Click Enter");
        mLoginView.setError(null);
        mPasswordView.setError(null);

        String mLogin = mLoginView.getText().toString();
        String mPass = mPasswordView.getText().toString();

        Boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(mPass)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(mLogin)) {
            mLoginView.setError(getString(R.string.error_invalid_login));
            focusView = mLoginView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            new UserLoginTask().execute();
        }

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mLoginView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
//        mLoginView.setError(null);
//        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
//        String login = mLoginView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(login, password);
//            mAuthTask.execute((Void) null);
//        mAuthTask.execute();
//        }
    }
    //        return password.length() > 4;
    //        //TODO: Replace this with your own logic
    //    private boolean isPasswordValid(String password) {
    //
    //    }
    //        return email.contains("@");
    //        //TODO: Replace this with your own logic
//    private boolean isEmailValid(String email) {

//    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

//        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public void NextActivity(String login,String token) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("login", login);
        intent.putExtra("token", token);
        LoginActivity.this.finish();
        startActivity(intent);
    }

//    public Dialog onCreateDialog(String error) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//        builder.setTitle("Важное сообщение!")
//                .setMessage(error)
//                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                       dialog.cancel();
//                    }
//                });
//        return builder.create();
//    }

    /**
     * Авторизация пользователя через логин и пароль
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        HttpsURLConnection conn = null;
        BufferedReader reader = null;
        String resultJson = "";

        String userLogin = mLoginView.getText().toString();
        String userPassword = mPasswordView.getText().toString();

        @Override
        protected String doInBackground(Void... params) {
            try {
                /**
                 * Настройка подключения
                 */
                URL url = new URL("https://sql.ma-dev.cloud/api/v1/login");
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
//                conn.connect();
                /**
                 * Формирование JSON для отправки
                 */
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("login", userLogin);
                jsonParam.put("password", userPassword);
//                jsonParam.put("latitude", 0D);
//                jsonParam.put("longitude", 0D);

                Log.i("my_log", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();
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

//                Log.d("my_log", String.valueOf(conn.getResponseCode()));
//                Log.d("my_log" , conn.getResponseMessage());
                Log.d("my_log","получено " + resultJson);

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
//            Log.d("my_log", strJson);
            try {
                JSONObject jsonObject = new JSONObject(strJson);

                 if (jsonObject.has("massage")){
                        Log.d(LOG_TAG, "Ошибка");
//                        onCreateDialog("Ошибка");
//                        JSONArray loginError = jsonObject.getJSONArray("login");
//                        JSONObject error = loginError.getJSONObject(1);
//                        String first = error.getString("name");
//                     JSONObject loginError =
//                        Log.d("my_log", String.valueOf(error));

                 } else {
                          String login = jsonObject.getString("login");
                          String token = jsonObject.getString("access_token");
                          Log.d(LOG_TAG, "Логин " + login + " Токин " + token);
                          if(mSaveLogin.isEnabled()){
                              loginPrefsEditor.putBoolean("saveLogin", true);
                              loginPrefsEditor.putString("username", userLogin);
                              loginPrefsEditor.putString("password", userPassword);
                              loginPrefsEditor.commit();
                          }
                        NextActivity(login, token);
                    }

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

