package org.attalaya.legacyhelper.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.attalaya.legacyhelper.LegacyActivity;
import org.attalaya.legacyhelper.R;
import org.attalaya.legacyhelper.controller.LegacyController;
import org.attalaya.legacyhelper.model.Legacy;
import org.attalaya.legacyhelper.util.RealmListAdapter;

public class LegacyListFragment extends Fragment implements ListView.OnItemClickListener, View.OnClickListener {

    private LegacyController controller;

    public LegacyListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = LegacyController.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_legacy_list, container, false);

        ListView legacys = (ListView)rootView.findViewById(R.id.legacys);
        legacys.setOnItemClickListener(this);
        RealmListAdapter<Legacy> mAdapter = new RealmListAdapter<>(getActivity(),controller.getAllLegacys(),R.layout.list_item_legacy);
        mAdapter.setOnClickListener(this);
        legacys.setAdapter(mAdapter);

        View addButton = rootView.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), LegacyActivity.class);
        intent.putExtra("legacyId", id);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editLegacyButton:
                View parentRow = (View) view.getParent();
                ListView listView = (ListView) parentRow.getParent();
                int position = listView.getPositionForView(parentRow);
                editLegacy((Legacy) listView.getAdapter().getItem(position));
                break;
            case R.id.add_button:
                newLegacy();
                break;
        }
    }

    private void editLegacy(Legacy legacy) {
        getFragmentManager().beginTransaction().replace(R.id.container, EditLegacyFragment.newInstance("edit", legacy.getId())).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

    public void newLegacy() {
        getFragmentManager().beginTransaction().replace(R.id.container, EditLegacyFragment.newInstance("new",-1)).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

}
