package org.attalaya.legacyhelper;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.attalaya.legacyhelper.LegacyHelperContract.Legacy;


public class EditLegacyFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private int genderLaw = 0,bloodlineLaw = 0,heirLaw = 0,exemplarTrait=-1;
    private EditText legacyName;
    private Spinner genderLawSpinner, bloodlineLawSpinner, heirLawSpinner, exemplarTraitSpinner;
    private Button createLegacyButton, cancelButton;

    private static final String ARG_ACTION = "action";

    private String mAction;

    public static EditLegacyFragment newInstance(String action) {
        EditLegacyFragment fragment = new EditLegacyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, action);
        fragment.setArguments(args);
        return fragment;
    }

    public EditLegacyFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_edit_legacy, container, false);
        legacyName = (EditText) rootView.findViewById(R.id.legacyNameText);
        genderLawSpinner = (Spinner) rootView.findViewById(R.id.genderLawSpinner);
        final ArrayAdapter<CharSequence> genderLawAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_law_array, android.R.layout.simple_spinner_item);
        genderLawAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderLawSpinner.setAdapter(genderLawAdapter);
        genderLawSpinner.setOnItemSelectedListener(this);
        bloodlineLawSpinner = (Spinner) rootView.findViewById(R.id.bloodlineLawSpinner);
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
            createLegacyButton.setText(R.string.editLegacy);
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
                    getActivity().getContentResolver().insert(LegacyHelperContract.Legacy.CONTENT_URI, newLegacyValues);
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
        });
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.genderLawSpinner: genderLaw = position; break;
            case R.id.bloodlineLawSpinner: bloodlineLaw = position; break;
            case R.id.heirLawSpinner:
                heirLaw = position;
                if (heirLaw == 7) {
                    ((View)exemplarTraitSpinner.getParent()).setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> exemplarTraitAdapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.m_trait_array, android.R.layout.simple_spinner_item);
                    exemplarTraitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    exemplarTraitSpinner.setAdapter(exemplarTraitAdapter);
                    exemplarTraitSpinner.setOnItemSelectedListener(this);
                } else {
                    ((View)exemplarTraitSpinner.getParent()).setVisibility(View.GONE);
                }
                break;
            case R.id.exemplarTraitSpinner: exemplarTrait = position; break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
