package org.attalaya.legacyhelper;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.attalaya.legacyhelper.settings.SettingsFragment;
import org.attalaya.legacyhelper.util.Database;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                SharedPreferences pref = getSharedPreferences("database",0);
                pref.edit().putString("dbVersion",Database.populateDataRealm(getApplicationContext(),pref.getString("dbVersion","0")));
                return "";
            }
        }.execute();
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.container, new LegacyListFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_new_legacy: openNewLegacy(); return true;
            case R.id.action_settings: openSettings(); return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openNewLegacy() {
        getFragmentManager().beginTransaction().replace(R.id.container, EditLegacyFragment.newInstance("new", -1)).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

    public void openSettings() {
        getFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment()).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
