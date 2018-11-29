package com.example.artemmakarcev.sqlex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.artemmakarcev.sqlex.POJO.GetAllEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public List<GetAllEx> getAllItems = new ArrayList<>();
    private Adapter adapter;
    public static String LOG_TAG = "my_log";
    private TextView nameUser;
    private SharedPreferences.Editor loginPrefsEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        String token = intent.getStringExtra("token");

        TextView mTextJson = findViewById(R.id.nameEx);
        nameUser =findViewById(R.id.textView);

//        nameUser.setText(login);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        ListView listView = findViewById(R.id.list);

        adapter = new Adapter(this, new ArrayList<GetAllEx>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
//                intent.putExtra("id", adapter.getItem(position));
//                intent.putExtra("urlImage", adapter.getUrlImage(position));
//                intent.putExtra("nameTitle", adapter.getNameTitle(position));
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        TextView mLoginUser = findViewById(R.id.nameUser);
//        mLoginUser.setText("Вы вошди как ");
        new Exercises().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = getIntent();
            String login = intent.getStringExtra("login");
            String token = intent.getStringExtra("token");
            Intent intent1 = new Intent(this, UserActivity.class);
            intent1.putExtra("login", login);
            intent1.putExtra("token", token);
            startActivity(intent1);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, LoginActivity.class);
            SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();
            loginPrefsEditor.apply();
            loginPrefsEditor.putString("password", "");
            loginPrefsEditor.commit();
            MainActivity.this.finish();
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("StaticFieldLeak")
    public class Exercises extends AsyncTask<Void, Void, String> {

        HttpsURLConnection conn = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                Intent intent = getIntent();
                String token = intent.getStringExtra("token");
                Log.d("my_log", "token " + token);

                URL url = new URL("https://sql.ma-dev.cloud/api/v1/exercises");
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                StringBuilder builder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                resultJson = builder.toString();
                Log.d("my_log", "Полученные данные " + resultJson);
                conn.disconnect();

                try {
                    JSONArray arr = new JSONArray(builder.toString());
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject object = arr.getJSONObject(i);
                        getAllItems.add(new GetAllEx(object.getString("id"), object.getString("name"), object.getString("description")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
            adapter.data = getAllItems;
            adapter.notifyDataSetChanged();
//            mTextJson.setText(strJson);
//            try {
//                JSONObject jsonObject = new JSONObject(strJson);
//                String login = jsonObject.getString("login");
//                String token = jsonObject.getString("access_token");

//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }

    }

}
