package org.attalaya.legacyhelper;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.attalaya.legacyhelper.controller.DataController;
import org.attalaya.legacyhelper.controller.LegacyController;
import org.attalaya.legacyhelper.model.Law;
import org.attalaya.legacyhelper.model.Legacy;
import org.attalaya.legacyhelper.model.Trait;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class EditLegacyFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private LegacyController controller;
    private DataController dataController;

    private static final String ARG_ACTION = "action";
    private static final String ARG_LEGACY_ID = "mLegacyId";

    private String mAction;
    private int mLegacyId;

    public static EditLegacyFragment newInstance(String action, int legacyId) {
        EditLegacyFragment fragment = new EditLegacyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTION, action);
        args.putInt(ARG_LEGACY_ID, legacyId);
        fragment.setArguments(args);
        return fragment;
    }

    public EditLegacyFragment() {
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
        RealmSpinnerAdapter<Law> genderLawSpinnerAdapter = new RealmSpinnerAdapter<>(getActivity(), dataController.getGenderLaws());
        genderLawSpinner.setAdapter(genderLawSpinnerAdapter);
        genderLawSpinner.setOnItemSelectedListener(this);

        Spinner bloodlineLawSpinner = (Spinner) rootView.findViewById(R.id.bloodlineLawSpinner);
        RealmSpinnerAdapter<Law> bloodlineLawSpinnerAdapter = new RealmSpinnerAdapter<>(getActivity(), dataController.getBloodlineLaws());
        bloodlineLawSpinner.setAdapter(bloodlineLawSpinnerAdapter);
        bloodlineLawSpinner.setOnItemSelectedListener(this);

        Spinner heirLawSpinner = (Spinner) rootView.findViewById(R.id.heirLawSpinner);
        RealmSpinnerAdapter<Law> heirLawSpinnerAdapter = new RealmSpinnerAdapter<>(getActivity(), dataController.getHeirLaws());
        heirLawSpinner.setAdapter(heirLawSpinnerAdapter);
        heirLawSpinner.setOnItemSelectedListener(this);

        Spinner exemplarTraitSpinner = (Spinner) rootView.findViewById(R.id.exemplarTraitSpinner);
        RealmSpinnerAdapter<Trait> exemplarTraitAdapter = new RealmSpinnerAdapter<>(getActivity(), dataController.getTraits());
        exemplarTraitSpinner.setAdapter(exemplarTraitAdapter);
        exemplarTraitSpinner.setOnItemSelectedListener(this);

        Spinner speciesLawSpinner = (Spinner) rootView.findViewById(R.id.speciesLawSpinner);
        RealmSpinnerAdapter<Law> speciesLawSpinnerAdapter = new RealmSpinnerAdapter<>(getActivity(), dataController.getSpeciesLaws());
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
                Law genderLaw = null,bloodlineLaw = null,heirLaw = null;
                Trait exemplarTrait = null;
                if (rootView!=null) {
                    legacyNameText = ((EditText) getView().findViewById(R.id.legacyNameText));
                    legacyName = legacyNameText.getText().toString();
                    genderLaw = (Law)((Spinner) rootView.findViewById(R.id.genderLawSpinner)).getSelectedItem();
                    bloodlineLaw = (Law)((Spinner) rootView.findViewById(R.id.bloodlineLawSpinner)).getSelectedItem();
                    heirLaw = (Law)((Spinner) rootView.findViewById(R.id.heirLawSpinner)).getSelectedItem();
                    exemplarTrait = (Trait)((Spinner) rootView.findViewById(R.id.exemplarTraitSpinner)).getSelectedItem();
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
                controller.createOrUpdateLegacy(mLegacyId, legacyName, genderLaw, bloodlineLaw, heirLaw, exemplarTrait);
                getFragmentManager().popBackStack();
                break;
            case R.id.cancelNewLegacyButton: getFragmentManager().popBackStack(); break;
        }
    }

    protected class RealmSpinnerAdapter<E extends RealmObject> extends RealmBaseAdapter<E> implements SpinnerAdapter {

        private DataController dataController;

        public RealmSpinnerAdapter(Context context,
                                   RealmResults<E> realmResults) {
            super(context, realmResults, true);
            dataController = DataController.getInstance(context);
        }

        @Override
        public int getCount() {
            return super.getCount()+1;
        }

        @Override
        public E getItem(int i) {
            if (i==0)
                return null;
            return realmResults.get(i-1);
        }

        public int getPosition(E item) {
            int position = -1;
            for (int i = 0; i < realmResults.size(); i++) {
                if (dataController.equalsObjects(realmResults.get(i), item)) {
                    position = i;
                }
            }
            return position+1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == 0) {
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
                ((TextView) convertView).setText("");
                return convertView;
            } else {
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
                E item = realmResults.get(position-1);
                ((TextView) convertView).setText(dataController.getDefaultName(item));
                return convertView;
            }
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (position == 0) {
                convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                ((TextView) convertView).setText("");
                return convertView;
            } else {
                convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                E item = realmResults.get(position-1);
                ((TextView) convertView).setText(dataController.getDefaultName(item));
                return convertView;
            }
        }
    }

}
