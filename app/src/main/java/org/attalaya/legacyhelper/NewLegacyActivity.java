package org.attalaya.legacyhelper;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.attalaya.legacyhelper.LegacyHelperContract.Legacy;


public class NewLegacyActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private int genderLaw = 0,bloodlineLaw = 0,heirLaw = 0;
    private EditText legacyName;
    private Spinner genderLawSpinner, bloodlineLawSpinner, heirLawSpinner;
    private Button createLegacyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_legacy);
        legacyName = (EditText) findViewById(R.id.legacyNameText);
        genderLawSpinner = (Spinner) findViewById(R.id.genderLawSpinner);
        final ArrayAdapter<CharSequence> genderLawAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_law_array, android.R.layout.simple_spinner_item);
        genderLawAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderLawSpinner.setAdapter(genderLawAdapter);
        genderLawSpinner.setOnItemSelectedListener(this);
        bloodlineLawSpinner = (Spinner) findViewById(R.id.bloodlineLawSpinner);
        ArrayAdapter<CharSequence> bloodlineLawAdapter = ArrayAdapter.createFromResource(this,
                R.array.bloodline_law_array, android.R.layout.simple_spinner_item);
        bloodlineLawAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodlineLawSpinner.setAdapter(bloodlineLawAdapter);
        bloodlineLawSpinner.setOnItemSelectedListener(this);
        heirLawSpinner = (Spinner) findViewById(R.id.heirLawSpinner);
        ArrayAdapter<CharSequence> heirLawAdapter = ArrayAdapter.createFromResource(this,
                R.array.heir_law_array, android.R.layout.simple_spinner_item);
        heirLawAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heirLawSpinner.setAdapter(heirLawAdapter);
        heirLawSpinner.setOnItemSelectedListener(this);
        createLegacyButton = (Button) findViewById(R.id.newLegacyButton);
        createLegacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genderLaw==0||bloodlineLaw==0||heirLaw==0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Select all the three laws", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                ContentValues newLegacyValues = new ContentValues();
                newLegacyValues.put(Legacy.COLUMN_NAME_NAME,legacyName.getText().toString());
                newLegacyValues.put(Legacy.COLUMN_NAME_GENDER_LAW,genderLaw);
                newLegacyValues.put(Legacy.COLUMN_NAME_BLOODLINE_LAW,bloodlineLaw);
                newLegacyValues.put(Legacy.COLUMN_NAME_HEIR_LAW,heirLaw);

                getContentResolver().insert(Legacy.CONTENT_URI,newLegacyValues);

                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_legacy, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.genderLawSpinner: genderLaw = position; break;
            case R.id.bloodlineLawSpinner: bloodlineLaw = position; break;
            case R.id.heirLawSpinner: heirLaw = position; break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
