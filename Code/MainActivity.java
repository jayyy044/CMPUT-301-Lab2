package com.example.listcity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
     * cityList will hold a reference to the ListView in activity_main.xml
     * We get this reference using findViewById()
     * This is why we make it type ListView
     */
    ListView cityList;

    /*
     * ArrayAdapter acts as a bridge between data and UI
     * We pass it:
     *   - The data (dataList)
     *   - The UI template for styling (content.xml)
     *   - The destination (ListView via setAdapter())
     */
    ArrayAdapter<String> cityAdapter;

    /*
     * ArrayList that stores the actual data (city names)
     */
    ArrayList<String> dataList;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Rendering the main layout when the app starts
        setContentView(R.layout.activity_main);

        //This is creating the reference to the list view we have in the main layout
        cityList = findViewById(R.id.city_list);
        Button addButton = findViewById(R.id.addCityBtn);
        LinearLayout inputContainer = findViewById(R.id.input_container);
        EditText cityInput = findViewById(R.id.city_input);
        Button confirmButton = findViewById(R.id.confirm_button);
        Button deleteButton = findViewById(R.id.deleteBtn);

        //Initializing arraylist for the city names
        dataList = new ArrayList<>();
        dataList.add("Edmonton");
        dataList.add("Toronto");

        /*
            * We passed it this to tell the adapter we are running inside main activity
            * Then we passed it the styling for each element in our data which for us is the
              content.xml file
            * then we passed in the actual data
        * */
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        //We are sending the UI component the adapter with the data
        cityList.setAdapter(cityAdapter);

        // Show/hide input container
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputContainer.getVisibility() == View.GONE) {
                    inputContainer.setVisibility(View.VISIBLE);
                } else {
                    inputContainer.setVisibility(View.GONE);
                }
            }
        });

        // Add city when confirm button is clicked
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityInput.getText().toString();
                if (!cityName.isEmpty()) {
                    dataList.add(cityName);
                    cityAdapter.notifyDataSetChanged();
                    cityInput.setText("");  // Clear input
                    inputContainer.setVisibility(View.GONE);
                }
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // If same item clicked, deselect
                if (selectedPosition == position) {
                    selectedPosition = -1;
                    view.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    // Clear previous selection
                    if (selectedPosition != -1) {
                        View previousView = cityList.getChildAt(selectedPosition);
                        if (previousView != null) {
                            previousView.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                    // Set new selection
                    selectedPosition = position;
                    view.setBackgroundColor(Color.LTGRAY);  // Highlight selected
                }
            }
        });

        // Delete selected city
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1 && selectedPosition < dataList.size()) {
                    dataList.remove(selectedPosition);
                    cityAdapter.notifyDataSetChanged();
                    selectedPosition = -1;  // Reset selection
                }
            }
        });
    }
}