package psu.pjm6196.bigo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // arrays of time complexities
    //                    Data Structures:    2-3 Tree       BinSearch     Hash     Linked  Min Heap
    private final String[] GET_MIN_WORST = { "O(log(n))",   "O(n)",       "O(n)",   "O(n)", "O(1)"      };
    private final String[] INSERT_WORST  = { "O(log(n))",   "O(n)",       "O(n)",   "O(1)", "O(log(n))" };
    private final String[] SEARCH_WORST  = { "O(log(n))",   "O(n)",       "O(n)",   "O(n)", "O(n)"      };
    private final String[] GET_MIN_AVG   = { "O(log(n))",   "O(log(n))",  "O(1)",   "O(n)", "O(1)"      };
    private final String[] INSERT_AVG    = { "O(log(n))",   "O(log(n))",  "O(1)",   "O(1)", "O(1)"      };
    private final String[] SEARCH_AVG    = { "O(log(n))",   "O(log(n))",  "O(1)",   "O(n)", "O(n)"      };

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

    public void showResult(View view) {


        // ~~~~~~~~~~~~~~~~~~~~ set results: email address ~~~~~~~~~~~~~~~~~~~~
        EditText emailAddress = (EditText) findViewById( R.id.editTextEmailAddress );
        TextView resultTo = (TextView) findViewById( R.id.textViewResultTo );
        resultTo.setText( R.string.to_label_text );
        resultTo.append( " " + emailAddress.getText() );

        // ~~~~~~~~~~~~~~~~~~~~ set results: email subject ~~~~~~~~~~~~~~~~~~~~
        EditText emailSubject = (EditText) findViewById( R.id.editTextEmailSubject );
        TextView resultSubject = (TextView) findViewById( R.id.textViewResultSubject );
        resultSubject.setText( R.string.subject_label_text );
        resultSubject.append( " " + emailSubject.getText() );

        // ~~~~~~~~~~~~~~~~~~~~ set results: Big O ~~~~~~~~~~~~~~~~~~~~

        // get worst/average case option
        RadioGroup complexityCaseGroup = (RadioGroup) findViewById( R.id.radioGroup );
        int complexityCaseId = complexityCaseGroup.getCheckedRadioButtonId();
        RadioButton complexityCaseOption = (RadioButton) findViewById( complexityCaseId );

        // get data structure
        Spinner dataStructure = (Spinner) findViewById( R.id.spinnerDataStructure );
        int dataStructureIndex = (int) dataStructure.getSelectedItemId();

        // get complexities to show
        CheckBox operationGetMin = (CheckBox) findViewById( R.id.checkBoxOpsMin );
        CheckBox operationInsert = (CheckBox) findViewById( R.id.checkBoxOpsInsert );
        CheckBox operationSearch = (CheckBox) findViewById( R.id.checkBoxOpsSearch );

        TextView resultBigO = (TextView) findViewById( R.id.textViewResultBigO);
        resultBigO.setText( complexityCaseOption.getText() );
        resultBigO.append( " " + getString(R.string.time_complexity_text) );
        resultBigO.append( " " + dataStructure.getSelectedItem().toString() );

        if ( operationGetMin.isChecked() ) {
            resultBigO.append("\n\tGet Minimum: ");

            if ( complexityCaseId == R.id.radioButtonWorstCase )
                resultBigO.append( GET_MIN_WORST[dataStructureIndex] );
            else
                resultBigO.append( GET_MIN_AVG[dataStructureIndex] );

        }

        if ( operationInsert.isChecked() ) {
            resultBigO.append( "\n\tInsert: " );

            if ( complexityCaseId == R.id.radioButtonWorstCase )
                resultBigO.append( INSERT_WORST[dataStructureIndex] );
            else
                resultBigO.append( INSERT_AVG[dataStructureIndex] );
        }

        if ( operationSearch.isChecked() ) {
            resultBigO.append( "\n\tSearch: " );

            if ( complexityCaseId == R.id.radioButtonWorstCase )
                resultBigO.append( SEARCH_WORST[dataStructureIndex] );
            else
                resultBigO.append( SEARCH_AVG[dataStructureIndex] );
        }
    }
}