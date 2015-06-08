package org.attalaya.legacyhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.attalaya.legacyhelper.view.CreateSimFragment;

public class SimListFragment extends Fragment {

    protected ActionMode mActionMode;
    protected SimpleCursorAdapter mAdapter;
    protected ListView sims;
    protected long legacyId;

    public SimListFragment() {
    }

    public static SimListFragment newInstance(long legacyId) {
        SimListFragment f = new SimListFragment();
        f.legacyId = legacyId;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sim_list, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.legacy_list, menu);
        MenuItem newLegacy = menu.findItem(R.id.action_new_legacy);
        if (newLegacy!=null) {
            newLegacy.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_legacy_list_item, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit_legacy:
                    createSim();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            sims.setItemChecked(-1, true);
            mActionMode = null;
        }
    };

    private void createSim() {
        getFragmentManager().beginTransaction().replace(R.id.container, CreateSimFragment.newInstance("",0)).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

}
