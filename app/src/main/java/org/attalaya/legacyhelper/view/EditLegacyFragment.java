package org.attalaya.legacyhelper.view;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.attalaya.legacyhelper.R;
import org.attalaya.legacyhelper.controller.DataController;
import org.attalaya.legacyhelper.controller.LegacyController;
import org.attalaya.legacyhelper.model.Law;
import org.attalaya.legacyhelper.model.Legacy;
import org.attalaya.legacyhelper.model.Trait;
import org.attalaya.legacyhelper.util.DataRealmSpinnerAdapter;


public class EditLegacyFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String ARG_ACTION = "action";
    private static final String ARG_LEGACY_ID = "mLegacyId";
    private LegacyController controller;
    private DataController dataController;
    private String mAction;
    private int mLegacyId;

    public EditLegacyFragment() {
    }

    public static EditLegacyFragment newInstance(String action, int legacyId) {
        EditLegacyFragment fragment = new EditLegacyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, action);
        args.putInt(ARG_LEGACY_ID, legacyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = LegacyController.getInstance(getActivity().getApplicationContext());
        dataController = DataController.getInstance(getActivity().getApplicationContext());
        if (getArguments() != null) {
            mAction = getArguments().getString(ARG_ACTION);
            mLegacyId = getArguments().getInt(ARG_LEGACY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_legacy, container, false);

        EditText legacyName = (EditText) rootView.findViewById(R.id.legacyNameText);

        Spinner genderLawSpinner = (Spinner) rootView.findViewById(R.id.genderLawSpinner);
        DataRealmSpinnerAdapter<Law> genderLawSpinnerAdapter = new DataRealmSpinnerAdapter<>(getActivity(), dataController.getGenderLaws());
        genderLawSpinner.setAdapter(genderLawSpinnerAdapter);
        genderLawSpinner.setOnItemSelectedListener(this);

        Spinner bloodlineLawSpinner = (Spinner) rootView.findViewById(R.id.bloodlineLawSpinner);
        DataRealmSpinnerAdapter<Law> bloodlineLawSpinnerAdapter = new DataRealmSpinnerAdapter<>(getActivity(), dataController.getBloodlineLaws());
        bloodlineLawSpinner.setAdapter(bloodlineLawSpinnerAdapter);
        bloodlineLawSpinner.setOnItemSelectedListener(this);

        Spinner heirLawSpinner = (Spinner) rootView.findViewById(R.id.heirLawSpinner);
        DataRealmSpinnerAdapter<Law> heirLawSpinnerAdapter = new DataRealmSpinnerAdapter<>(getActivity(), dataController.getHeirLaws());
        heirLawSpinner.setAdapter(heirLawSpinnerAdapter);
        heirLawSpinner.setOnItemSelectedListener(this);

        Spinner exemplarTraitSpinner = (Spinner) rootView.findViewById(R.id.exemplarTraitSpinner);
        DataRealmSpinnerAdapter<Trait> exemplarTraitAdapter = new DataRealmSpinnerAdapter<>(getActivity(), dataController.getAllTraits());
        exemplarTraitSpinner.setAdapter(exemplarTraitAdapter);
        exemplarTraitSpinner.setOnItemSelectedListener(this);

        Spinner speciesLawSpinner = (Spinner) rootView.findViewById(R.id.speciesLawSpinner);
        DataRealmSpinnerAdapter<Law> speciesLawSpinnerAdapter = new DataRealmSpinnerAdapter<>(getActivity(), dataController.getSpeciesLaws());
        speciesLawSpinner.setAdapter(speciesLawSpinnerAdapter);
        speciesLawSpinner.setOnItemSelectedListener(this);
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("ep01", false)) {
            rootView.findViewById(R.id.speciesLawField).setVisibility(View.VISIBLE);
        }

        Button createLegacyButton = (Button) rootView.findViewById(R.id.newLegacyButton);
        createLegacyButton.setOnClickListener(this);
        if (mAction.equals("edit")) {
            createLegacyButton.setText(R.string.edit_legacy);
        }

        Button cancelButton = (Button) rootView.findViewById(R.id.cancelNewLegacyButton);
        cancelButton.setOnClickListener(this);

        Legacy legacy = controller.getLegacyById(mLegacyId);
        if (legacy != null){
            legacyName.setText(legacy.getName());
            genderLawSpinner.setSelection(genderLawSpinnerAdapter.getPosition(legacy.getGenderLaw()));
            bloodlineLawSpinner.setSelection(bloodlineLawSpinnerAdapter.getPosition(legacy.getBloodlineLaw()));
            heirLawSpinner.setSelection(heirLawSpinnerAdapter.getPosition(legacy.getHeirLaw()));
            if (legacy.getHeirLaw().getName().equals(getActivity().getResources().getString(R.string.exemplar_heir_law))) {
                exemplarTraitSpinner.setSelection(exemplarTraitAdapter.getPosition(legacy.getExemplarTrait()));
            }
            if ((PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("ep01", false))&&(legacy.getSpeciesLaw()!=null)) {
                speciesLawSpinner.setSelection(speciesLawSpinnerAdapter.getPosition(legacy.getSpeciesLaw()));
            }
        }

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.heirLawSpinner:
                if (getView()!=null) {
                    Law heirLaw = (Law)parent.getItemAtPosition(position);
                    if (heirLaw != null && heirLaw.getName().equals(getActivity().getResources().getString(R.string.exemplar_heir_law))) {
                        getView().findViewById(R.id.exemplarTraitField).setVisibility(View.VISIBLE);
                    } else {
                        getView().findViewById(R.id.exemplarTraitField).setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newLegacyButton:
                View rootView = getView();
                EditText legacyNameText = null;
                String legacyName = "";
                Law genderLaw = null,bloodlineLaw = null,heirLaw = null,speciesLaw = null;
                Trait exemplarTrait = null;
                if (rootView!=null) {
                    legacyNameText = ((EditText) getView().findViewById(R.id.legacyNameText));
                    legacyName = legacyNameText.getText().toString();
                    genderLaw = (Law)((Spinner) rootView.findViewById(R.id.genderLawSpinner)).getSelectedItem();
                    bloodlineLaw = (Law)((Spinner) rootView.findViewById(R.id.bloodlineLawSpinner)).getSelectedItem();
                    heirLaw = (Law)((Spinner) rootView.findViewById(R.id.heirLawSpinner)).getSelectedItem();
                    exemplarTrait = (Trait)((Spinner) rootView.findViewById(R.id.exemplarTraitSpinner)).getSelectedItem();
                    speciesLaw = (Law)((Spinner) rootView.findViewById(R.id.speciesLawSpinner)).getSelectedItem();
                }
                if (legacyName.equals("")) {
                    if (legacyNameText!=null)
                        legacyNameText.setError(getResources().getString(R.string.legacy_name_empty));
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.legacy_name_empty, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (genderLaw == null || bloodlineLaw == null || heirLaw == null) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.select_all_laws, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (heirLaw.getName().equals(getActivity().getResources().getString(R.string.exemplar_heir_law))) {
                    if (exemplarTrait == null) {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.select_exemplar_trait, Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                } else {
                    exemplarTrait = null;
                }
                controller.createOrUpdateLegacy(mLegacyId, legacyName, genderLaw, bloodlineLaw, heirLaw, exemplarTrait, speciesLaw);
                getFragmentManager().popBackStack();
                break;
            case R.id.cancelNewLegacyButton: getFragmentManager().popBackStack(); break;
        }
    }

}
