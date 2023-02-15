package psu.pjm6196.bigo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        // toolbar
        Toolbar headerToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(headerToolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_compose:
                Log.d("TOOLBAR", "onOptionsItemSelected: title is " + item.getTitle() );
                // if icon is pen, display result
                if ( item.getTitle() == getText(R.string.menu_compose) ) {
                    Log.d("TOOLBAR", "showing result");
                    showResult();

                    // set icon to envelop
                    item.setIcon(R.drawable.ic_action_send);
                    item.setTitle(R.string.menu_send);
                }

                // if icon is envelope, fire send email intent
                else {
                    Log.d("TOOLBAR", "composing email");
                    composeEmail();

                    // set icon to pen
                    item.setIcon(R.drawable.ic_action_edit);
                    item.setTitle(R.string.menu_compose);
                }

                return true;
            case R.id.menu_settings:
                // hidden, so do nothing
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void showResult() {
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

    public void composeEmail() {


        // ~~~~~~~~~~~~~~~~~~~~ get email text fields ~~~~~~~~~~~~~~~~~~~~
        EditText emailAddress = (EditText) findViewById( R.id.editTextEmailAddress );
        EditText emailSubject = (EditText) findViewById( R.id.editTextEmailSubject );
        TextView emailBody = (TextView) findViewById( R.id.textViewResultBigO);

        // modified from android docs: https://developer.android.com/guide/components/intents-common#ComposeEmail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // target email apps
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress.getText());
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject.getText());
        intent.putExtra(Intent.EXTRA_TEXT, emailBody.getText());

        // I assume this checks for an available email app
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d("MAIN:TOOLBAR", "composeEmail: email composed and handed to email client");
            startActivity(intent);
        } else {
            Log.e("MAIN:TOOLBAR", "composeEmail: email not composed. possibly no email client available");
        }

    }
}