package com.Gaous.covidtracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecondaryActivity extends AppCompatActivity{

     ListView listView;
     TextView textViewDetailsArea;
    ArrayList<String> countryNameList = new ArrayList<>();
    Set<String> list = new HashSet<>();
    public List<Model> arraysOfModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        Bundle b=this.getIntent().getExtras();
        ArrayList<String> array=b.getStringArrayList("key1");
        listView = findViewById(R.id.listView);
//        listView.setRotation(-90);

        textViewDetailsArea = findViewById(R.id.textViewDetailsArea);
        textViewDetailsArea.setMovementMethod(new ScrollingMovementMethod());
        list.addAll(array);
        Intent i = getIntent();
        arraysOfModel.addAll((List<Model>) i.getSerializableExtra("List"));
        for(Model a : arraysOfModel){
            countryNameList.add(a.getCountryName());
        }
        ListAdapter arrayAdapter = new ArrayAdapter<>(
                this, R.layout.activity_row, array
        );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String string =  listView.getItemAtPosition(position).toString();
            for(Model model: arraysOfModel){
                if (model.getCountryName().equals(string)) {
                    textViewDetailsArea.setText(model.getDetails(true));
                    break;
                }
            }
        });
    }
}
