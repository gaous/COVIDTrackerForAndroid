package com.Gaous.covidtracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textViewTitle;
    EditText editTextInputCountries;
    TextView textViewCountriesList;

    Model model = new Model();
    ArrayList<String> countriesTextArray = new ArrayList<>();
    ArrayList<Model> countryArray = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url ="https://api.covid19api.com/summary";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                stringBuilder::append,
                error -> System.out.println("Fuck you"));
        requestQueue.add(stringRequest);
    }

    public void handleEnterButton(View view){
        textViewTitle = findViewById(R.id.textViewTitle);
        editTextInputCountries = findViewById(R.id.editTextInputCountries);
        textViewCountriesList = findViewById(R.id.textViewCountriesList);
        Context context = this;
        editTextInputCountries.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                if (editTextInputCountries.getText().toString().trim().toLowerCase().equals("")) {
                    new AlertDialog.Builder(context)
                            .setTitle("Warning")
                            .setMessage("Please write a country name in the text box to include")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                } else
                {
                    String tempCountryName = editTextInputCountries.getText().toString().trim();
                    try {
                        APIController apiController = new APIController();
                        apiController.setCollectedDataFromAPI(stringBuilder.toString());
                        model = apiController.getDataFromAPI("Countries", false, tempCountryName);
                        if(model.getCountryName().length() == 0)
                            throw new Exception();
                        else {
                            if (!countriesTextArray.toString().toLowerCase().contains(tempCountryName.toLowerCase())) {
                                countriesTextArray.add(model.getCountryName());
                                countryArray.add(model);
                            }
                            else{
                                new AlertDialog.Builder(context)
                                        .setTitle("Warning")
                                        .setMessage("The country name is already included")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .show();
                            }
                            if(countriesTextArray.size() < 2){
                                textViewCountriesList.setText(model.getCountryName());
                            }
                            else{
                                String temp = countriesTextArray.toString();
                                temp = temp.replaceAll("[^A-Za-z, ]", "");
                                textViewCountriesList.setText(temp);
                            }
                        }
                    }
                    catch ( NullPointerException n1){
                        new AlertDialog.Builder(context)
                                .setTitle("Warning")
                                .setMessage("Please input a valid country name")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    } catch (UnknownHostException ex){
                        new AlertDialog.Builder(context)
                                .setTitle("Warning")
                                .setMessage("Please connect to internet and try again")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    editTextInputCountries.setText("");
                    editTextInputCountries.requestFocus();
                }
                handled = true;
            }
            return handled;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void handleDeleteCountriesFromLabels(View view) {
        textViewTitle = findViewById(R.id.textViewTitle);
        editTextInputCountries = findViewById(R.id.editTextInputCountries);
        textViewCountriesList = findViewById(R.id.textViewCountriesList);

        if(editTextInputCountries.getText().toString().trim().toLowerCase().equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Please write a country name in the text box to remove")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
        else{
            String tempWord = editTextInputCountries.getText().toString().trim();
            if (!countriesTextArray.toString().toLowerCase().contains(tempWord.toLowerCase())){
                new AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage("You have not input the country name yet")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
            else{
                countryArray.removeIf(removeCountryModel -> removeCountryModel.getCountryName().toLowerCase().equals(tempWord.toLowerCase()));
                countriesTextArray.removeIf(removeCountryText -> removeCountryText.toLowerCase().equals(tempWord.toLowerCase()));
                String temp = countriesTextArray.toString();
                temp = temp.replaceAll("[^A-Za-z, ]", "");
                textViewCountriesList.setText(temp);
                editTextInputCountries.setText("");
            }
        }
        editTextInputCountries.requestFocus();
    }

    public void handleSearchButton(View view) {
        textViewTitle = findViewById(R.id.textViewTitle);
        editTextInputCountries = findViewById(R.id.editTextInputCountries);
        textViewCountriesList = findViewById(R.id.textViewCountriesList);
        if(isSearchEligibleNow()){
            ArrayList<String> a = (countriesTextArray);
            ArrayList<Model> b = (countryArray);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("key1", a);
            Intent intent = new Intent(this, SecondaryActivity.class);
            intent.putExtras(bundle);
            intent.putExtra("List", (Serializable) b);
            startActivity(intent);
        }
    }

    private boolean isSearchEligibleNow(){
        if(countriesTextArray.size()< 1){
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Please input at least 1 country name to search for the data")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            editTextInputCountries.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }

    public void handleRefreshButton(View view) {
        textViewTitle = findViewById(R.id.textViewTitle);
        try{
            APIController apiController = new APIController();
            apiController.setCollectedDataFromAPI(stringBuilder.toString());
            model = apiController.getDataFromAPI("Global", true, "");
            textViewTitle.setText(model.getDetails(false));
        } catch (JSONException ex){
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Please connect to internet and try again")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }
}