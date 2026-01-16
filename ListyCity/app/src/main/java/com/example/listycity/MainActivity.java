package com.example.listycity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Declares variables that we use later
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button button_add, button_delete; // Initializes the buttons
    int cityPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initializes items
        cityList = findViewById(R.id.city_list);
        button_add = findViewById(R.id.buttonAdd);
        button_delete = findViewById(R.id.buttonDelete);

        // Define Array - Starting list
        String []cities = {"Edmonton", "Vancouver", "Calgary"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // newArrayAdapter - 1st arg is context (called using "this"). 2nd arg is
        // resource file. 3rd arg is dataList we want to pass.
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // When user selects a city, this remembers which one was picked.
        // Used for "Delete City".
        cityList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent,
                                    android.view.View view, int position, long id) {
                cityPos = position;
                String cityName = dataList.get(position);  // Gets city name at respective position
                // When a city is selected, small pop-up box confirms which one.
                Toast.makeText(MainActivity.this, "Selected: " + cityName,
                               Toast.LENGTH_SHORT).show();
            }
        });

        // Functionality of "Add City" button
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create popup input-box in MainActivity.
                android.app.AlertDialog.Builder builder = new
                        android.app.AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add City"); // Sets Title of Dialog

                // Create input field
                final android.widget.EditText input = new android.widget.EditText(MainActivity.this);
                input.setHint("Enter city name");
                builder.setView(input);

                builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        String cityName = input.getText().toString();
                        dataList.add(cityName);
                        cityAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

        // Functionality of "Delete City" button
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityPos != -1 && cityPos < dataList.size()) {
                    // Remove city at selected position
                    String deletedCity = dataList.remove(cityPos);
                    cityAdapter.notifyDataSetChanged();  // Refresh list
                    cityPos = -1; // Reset selection
                }
                else { // If "Delete city" button pressed, but none selected.
                    Toast.makeText(MainActivity.this, "Please select a city first",
                                   Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}