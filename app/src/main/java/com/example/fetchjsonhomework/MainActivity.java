package com.example.fetchjsonhomework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import com.github.javafaker.Faker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    List<PersonInfo> list_of_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_of_person = new ArrayList<>();
        new GetJsonData().execute();
        Faker faker = new Faker();

        for (int i = 1; i < 20; i++){
            list_of_person.add(new PersonInfo(
                    i, faker.name().fullName(), faker.phoneNumber().phoneNumber(), faker.internet().emailAddress()
            ));
        }

        ContactAdapter adapter = new ContactAdapter(this,list_of_person);
        ListView listView = findViewById(R.id.list_items);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "CALL");
        menu.add(0, v.getId(), 0, "SMS");
        menu.add(0, v.getId(), 0, "EMAIL");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        String menuItemIndex = (String) item.getTitle();
        if (menuItemIndex.equals("CALL")){
            String phoneNumber = "tel:" + list_of_person.get((int) info.id).getPhoneNumber();
            Intent callAction = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
            startActivity(callAction);
        }else if(menuItemIndex.equals("SMS")){
            String phoneNumber = "sms:" + list_of_person.get((int) info.id).getPhoneNumber();
            Intent textAction = new Intent(Intent.ACTION_SENDTO, Uri.parse(phoneNumber));
            startActivity(textAction);
        }else {
            String email = list_of_person.get((int) info.id).getEmail();
            Intent emailAction = new Intent(Intent.ACTION_SENDTO);
            emailAction.setData(Uri.parse("mailto:"));
            emailAction.putExtra(Intent.EXTRA_EMAIL, email);
            startActivity(Intent.createChooser(emailAction, "Chooser Title"));
        }
        return true;
    }

    class GetJsonData extends AsyncTask<Void, Integer, Boolean>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Downloading");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("https://lebavui.github.io/jsons/users.json");
                HttpURLConnection con = (HttpURLConnection)  url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();

                Log.v("TAG", "Sending 'GET' request to URL : " + url.toString());
                Log.v("TAG", "Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
//print result
                Log.v("TAG", response.toString());

                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}


