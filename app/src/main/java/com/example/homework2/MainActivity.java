package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // set data structures spinner values
        // adapted from the android docs: https://developer.android.com/develop/ui/views/components/spinner#java

        // get spinner by id
        Spinner ds_spinner = (Spinner) findViewById( R.id.spinnerDataStructure );

        // create ArrayAdapter from string-array resources
        ArrayAdapter<CharSequence> ds_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.data_structures,
                android.R.layout.simple_spinner_item
        );

        // set adapter layout
        ds_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        // apply adapter to spinner
        ds_spinner.setAdapter( ds_adapter );
    }
}