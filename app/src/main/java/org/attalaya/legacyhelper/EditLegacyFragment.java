package org.attalaya.legacyhelper;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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

import org.attalaya.legacyhelper.controller.LegacyController;
import org.attalaya.legacyhelper.model.Law;
import org.attalaya.legacyhelper.model.Legacy;
import org.attalaya.legacyhelper.model.Trait;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;


public class EditLegacyFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Law genderLaw,bloodlineLaw,heirLaw;
    private Trait exemplarTrait;
    private EditText legacyName;
    private Spinner exemplarTraitSpinner;
    private TraitSpinnerAdapter exemplarTraitAdapter;
    private Realm realm,dataRealm;
    private LegacyController controller;

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
        if (getArguments() != null) {
            mAction = getArguments().getString(ARG_ACTION);
            mLegacyId = getArguments().getInt(ARG_LEGACY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getInstance(getActivity());
        Toast.makeText(getActivity().getApplicationContext(), String.valueOf(realm.allObjects(Legacy.class).size()), Toast.LENGTH_SHORT).show();
        dataRealm = Realm.getInstance(getActivity(), "data.realm");
        View rootView = inflater.inflate(R.layout.fragment_edit_legacy, container, false);
        legacyName = (EditText) rootView.findViewById(R.id.legacyNameText);
        Spinner genderLawSpinner = (Spinner) rootView.findViewById(R.id.genderLawSpinner);
        LawSpinnerAdapter genderLawSpinnerAdapter = new LawSpinnerAdapter(getActivity(),dataRealm.where(Law.class).equalTo("type", 0).findAll());
        genderLawSpinner.setAdapter(genderLawSpinnerAdapter);
        genderLawSpinner.setOnItemSelectedListener(this);
        Spinner bloodlineLawSpinner = (Spinner) rootView.findViewById(R.id.bloodlineLawSpinner);
        LawSpinnerAdapter bloodlineLawSpinnerAdapter = new LawSpinnerAdapter(getActivity(),dataRealm.where(Law.class).equalTo("type", 1).findAll());
        bloodlineLawSpinner.setAdapter(bloodlineLawSpinnerAdapter);
        bloodlineLawSpinner.setOnItemSelectedListener(this);
        exemplarTraitSpinner = (Spinner) rootView.findViewById(R.id.exemplarTraitSpinner);
        exemplarTraitAdapter = new TraitSpinnerAdapter(getActivity(),dataRealm.allObjects(Trait.class));
        exemplarTraitSpinner.setAdapter(exemplarTraitAdapter);
        exemplarTraitSpinner.setOnItemSelectedListener(this);
        Spinner heirLawSpinner = (Spinner) rootView.findViewById(R.id.heirLawSpinner);
        LawSpinnerAdapter heirLawSpinnerAdapter = new LawSpinnerAdapter(getActivity(),dataRealm.where(Law.class).equalTo("type", 2).findAll());
        heirLawSpinner.setAdapter(heirLawSpinnerAdapter);
        heirLawSpinner.setOnItemSelectedListener(this);
        Button createLegacyButton = (Button) rootView.findViewById(R.id.newLegacyButton);
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
        if (mAction.equals("edit")) {
            createLegacyButton.setText(R.string.edit_legacy);
        }
        createLegacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genderLaw == null || bloodlineLaw == null || heirLaw == null) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.select_all_laws, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (heirLaw.getName().equals(getActivity().getResources().getString(R.string.exemplar_heir_law)) && exemplarTrait == null) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.select_exemplar_trait, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                realm.beginTransaction();

                Legacy legacy = new Legacy();
                if (mAction.equals("new")) {
                    legacy.setId(realm.allObjects(Legacy.class).size() + 1);
                } else if (mAction.equals("edit")) {
                    legacy.setId(mLegacyId);
                }
                legacy.setName(legacyName.getText().toString());
                legacy.setGenderLaw(genderLaw);
                legacy.setBloodlineLaw(bloodlineLaw);
                legacy.setHeirLaw(heirLaw);
                legacy.setExemplarTrait(exemplarTrait);

                realm.copyToRealmOrUpdate(legacy);

                realm.commitTransaction();

                getFragmentManager().popBackStack();
            }
        });
        Button cancelButton = (Button) rootView.findViewById(R.id.cancelNewLegacyButton);
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
        if (parent.getItemAtPosition(position) instanceof Law && position!=0)
            Toast.makeText(getActivity(),position+" "+String.valueOf(((Law)parent.getItemAtPosition(position)).getId()), Toast.LENGTH_SHORT).show();
        switch (parent.getId()) {
            case R.id.genderLawSpinner: genderLaw = (Law)parent.getItemAtPosition(position); break;
            case R.id.bloodlineLawSpinner: bloodlineLaw = (Law)parent.getItemAtPosition(position); break;
            case R.id.heirLawSpinner:
                heirLaw = (Law)parent.getItemAtPosition(position);
                if (heirLaw!=null && heirLaw.getName().equals(this.getActivity().getResources().getString(R.string.exemplar_heir_law))) {
                    ((View)exemplarTraitSpinner.getParent()).setVisibility(View.VISIBLE);
                } else {
                    ((View)exemplarTraitSpinner.getParent()).setVisibility(View.GONE);
                }
                break;
            case R.id.exemplarTraitSpinner: exemplarTrait = (Trait)parent.getItemAtPosition(position); break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected class LawSpinnerAdapter extends RealmBaseAdapter<Law> implements SpinnerAdapter {

        public LawSpinnerAdapter(Context context,
                                 RealmResults<Law> realmResults) {
            super(context, realmResults, true);
        }

        @Override
        public int getCount() {
            return super.getCount()+1;
        }

        @Override
        public Law getItem(int i) {
            if (i==0)
                return null;
            return realmResults.get(i-1);
        }

        public int getPosition(Law law) {
            int position = -1;
            for (int i = 0; i < realmResults.size(); i++) {
                if (realmResults.get(i).getId()==law.getId()) {
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
                Law item = realmResults.get(position-1);
                ((TextView) convertView).setText(item.getName());
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
                Law item = realmResults.get(position-1);
                ((TextView) convertView).setText(item.getName());
                return convertView;
            }
        }
    }

    protected class TraitSpinnerAdapter extends RealmBaseAdapter<Trait> implements SpinnerAdapter {

        public TraitSpinnerAdapter(Context context,
                                   RealmResults<Trait> realmResults) {
            super(context, realmResults, true);
        }

        @Override
        public int getCount() {
            return super.getCount()+1;
        }

        @Override
        public Trait getItem(int i) {
            if (i==0)
                return null;
            return realmResults.get(i-1);
        }

        public int getPosition(Trait trait) {
            int position = -1;
            for (int i = 0; i < realmResults.size(); i++) {
                if (realmResults.get(i).getId()==trait.getId()) {
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
                Trait item = realmResults.get(position-1);
                ((TextView) convertView).setText(item.getmName());
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
                Trait item = realmResults.get(position-1);
                ((TextView) convertView).setText(item.getmName());
                return convertView;
            }
        }
    }

}
