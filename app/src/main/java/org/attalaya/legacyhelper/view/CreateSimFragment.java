package org.attalaya.legacyhelper.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.attalaya.legacyhelper.R;
import org.attalaya.legacyhelper.controller.DataController;
import org.attalaya.legacyhelper.controller.LegacyController;


public class CreateSimFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private int simType, simSex, simAge;
    private EditText simName, simSurname;
    private Spinner simAgeSpinner;

    private static final String ARG_ACTION = "action";
    private static final String ARG_LEGACY_ID = "mLegacyId";
    private LegacyController controller;
    private DataController dataController;
    private String mAction;
    private int mLegacyId;


    public static CreateSimFragment newInstance(String action, int legacyId) {
        CreateSimFragment fragment = new CreateSimFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, action);
        args.putInt(ARG_LEGACY_ID, legacyId);
        fragment.setArguments(args);
        return fragment;
    }

    public CreateSimFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAction = getArguments().getString(ARG_ACTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_sim, container, false);

        Spinner simTypeSpinner = (Spinner) rootView.findViewById(R.id.simTypeSpinner);
        final ArrayAdapter<CharSequence> simTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sim_type_array, android.R.layout.simple_spinner_item);
        simTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        simTypeSpinner.setAdapter(simTypeAdapter);
        simTypeSpinner.setOnItemSelectedListener(this);

        Spinner simSexSpinner = (Spinner) rootView.findViewById(R.id.sexSpinner);
        final ArrayAdapter<CharSequence> simSexAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sim_sex_array, android.R.layout.simple_spinner_item);
        simSexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        simSexSpinner.setAdapter(simSexAdapter);
        simSexSpinner.setOnItemSelectedListener(this);

        Spinner simAgeSpinner = (Spinner) rootView.findViewById(R.id.ageSpinner);
        final ArrayAdapter<CharSequence> simAgeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sim_age_array, android.R.layout.simple_spinner_item);
        simAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        simAgeSpinner.setAdapter(simAgeAdapter);
        simAgeSpinner.setOnItemSelectedListener(this);

        /*bloodlineLawSpinner = (Spinner) rootView.findViewById(R.id.bloodlineLawSpinner);
        ArrayAdapter<CharSequence> bloodlineLawAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.bloodline_law_array, android.R.layout.simple_spinner_item);
        bloodlineLawAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodlineLawSpinner.setAdapter(bloodlineLawAdapter);
        bloodlineLawSpinner.setOnItemSelectedListener(this);
        exemplarTraitSpinner = (Spinner) rootView.findViewById(R.id.exemplarTraitSpinner);
        heirLawSpinner = (Spinner) rootView.findViewById(R.id.heirLawSpinner);
        ArrayAdapter<CharSequence> heirLawAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.heir_law_array, android.R.layout.simple_spinner_item);
        heirLawAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heirLawSpinner.setAdapter(heirLawAdapter);
        heirLawSpinner.setOnItemSelectedListener(this);
        createLegacyButton = (Button) rootView.findViewById(R.id.newLegacyButton);
        if (mAction.equals("edit")) {
            createLegacyButton.setText(R.string.edit_legacy);
        }
        createLegacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genderLaw==0||bloodlineLaw==0||heirLaw==0) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.select_all_laws, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                ContentValues newLegacyValues = new ContentValues();
                newLegacyValues.put(Legacy.COLUMN_NAME_NAME,legacyName.getText().toString());
                newLegacyValues.put(Legacy.COLUMN_NAME_GENDER_LAW,genderLaw);
                newLegacyValues.put(Legacy.COLUMN_NAME_BLOODLINE_LAW,bloodlineLaw);
                newLegacyValues.put(Legacy.COLUMN_NAME_HEIR_LAW,heirLaw);
                newLegacyValues.put(Legacy.COLUMN_NAME_EXEMPLAR_TRAIT,exemplarTrait);

                if (mAction.equals("new")) {
                    getActivity().getContentResolver().insert(Legacy.CONTENT_URI, newLegacyValues);
                }

                getFragmentManager().popBackStack();
            }
        });
        cancelButton = (Button) rootView.findViewById(R.id.cancelNewLegacyButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });*/
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.simTypeSpinner: simType = position; break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
