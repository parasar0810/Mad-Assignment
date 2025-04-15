package com.example.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // The rest of your code remains the same
    private static final String TAG = "UnitConverter";

    private EditText valueEditText;
    private Spinner fromUnitSpinner;
    private Spinner toUnitSpinner;
    private TextView resultTextView;

    private final String[] units = {"Feet", "Inches", "Centimeters", "Meters", "Yards"};

    // Conversion rates to meters (base unit)
    private final Map<String, Double> conversionRates = new HashMap<String, Double>() {{
        put("Feet", 0.3048);
        put("Inches", 0.0254);
        put("Centimeters", 0.01);
        put("Meters", 1.0);
        put("Yards", 0.9144);
    }};

    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0.0000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Log.d(TAG, "Starting initialization");

            // Initialize UI components
            valueEditText = findViewById(R.id.valueEditText);
            fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
            toUnitSpinner = findViewById(R.id.toUnitSpinner);
            resultTextView = findViewById(R.id.resultTextView);

            if (valueEditText == null || fromUnitSpinner == null ||
                    toUnitSpinner == null || resultTextView == null) {
                Toast.makeText(this, "Error: UI components not found", Toast.LENGTH_LONG).show();
                Log.e(TAG, "One or more UI components could not be found");
                return;
            }

            Log.d(TAG, "UI components initialized successfully");

            // Setup spinners
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            fromUnitSpinner.setAdapter(adapter);
            toUnitSpinner.setAdapter(adapter);

            // Set default selections
            fromUnitSpinner.setSelection(0); // Feet
            toUnitSpinner.setSelection(2);   // Centimeters

            Log.d(TAG, "Spinners setup complete");

            // Setup listeners
            setupListeners();

            // Initial conversion
            performConversion();

            Log.d(TAG, "Initialization complete");
        } catch (Exception e) {
            Log.e(TAG, "Error during onCreate: ", e);
            Toast.makeText(this, "Error initializing app: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupListeners() {
        try {
            // Input value change listener
            valueEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    performConversion();
                }
            });

            // Spinners selection listeners
            fromUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    performConversion();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            toUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    performConversion();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        } catch (Exception e) {
            Log.e(TAG, "Error setting up listeners: ", e);
        }
    }

    private void performConversion() {
        try {
            String valueStr = valueEditText.getText().toString();
            if (valueStr.isEmpty()) {
                resultTextView.setText("0");
                return;
            }

            double value = Double.parseDouble(valueStr);
            String fromUnit = fromUnitSpinner.getSelectedItem().toString();
            String toUnit = toUnitSpinner.getSelectedItem().toString();

            // Convert to base unit (meters)
            double valueInMeters = value * conversionRates.get(fromUnit);

            // Convert from base unit to target unit
            double result = valueInMeters / conversionRates.get(toUnit);

            resultTextView.setText(decimalFormat.format(result));
        } catch (NumberFormatException e) {
            resultTextView.setText("Invalid input");
            Log.e(TAG, "Number format error: ", e);
        } catch (Exception e) {
            resultTextView.setText("Error");
            Log.e(TAG, "Conversion error: ", e);
        }
    }
}