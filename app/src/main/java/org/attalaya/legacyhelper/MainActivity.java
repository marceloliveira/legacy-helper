package org.attalaya.legacyhelper;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.attalaya.legacyhelper.model.Law;
import org.attalaya.legacyhelper.model.Trait;

import io.realm.Realm;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                populateDataRealm();
                return "";
            }
        }.execute();
        Realm realm = Realm.getInstance(this, "data.realm");
        showStatus(String.valueOf(realm.allObjects(Law.class).size()));
        showStatus(String.valueOf(realm.allObjects(Trait.class).size()));
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
            case R.id.action_settings: return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openNewLegacy() {
        getFragmentManager().beginTransaction().replace(R.id.container, EditLegacyFragment.newInstance("new",-1)).addToBackStack(null).commit();
        getFragmentManager().executePendingTransactions();
    }

    private void showStatus(String txt) {
        Log.i(TAG, txt);
    }

    public void populateDataRealm() {
        Realm realm = Realm.getInstance(this, "data.realm");
        if (realm.allObjects(Law.class).size()>0) {
            return;
        }
        Resources res = this.getApplicationContext().getResources();

        realm.beginTransaction();
        int i = 1;
        String[] laws = res.getStringArray(R.array.gender_law_array);
        for (String s: laws) {
            Law law = new Law();
            law.setId(i);
            law.setName(s);
            law.setType(0);
            realm.copyToRealm(law);
            i++;
        }
        laws = res.getStringArray(R.array.bloodline_law_array);
        for (String s: laws) {
            Law law = new Law();
            law.setId(i);
            law.setName(s);
            law.setType(1);
            realm.copyToRealm(law);
            i++;
        }
        laws = res.getStringArray(R.array.heir_law_array);
        for (String s: laws) {
            Law law = new Law();
            law.setId(i);
            law.setName(s);
            law.setType(2);
            realm.copyToRealm(law);
            i++;
        }
        i=1;
        String[] mTraits = res.getStringArray(R.array.m_trait_array);
        String[] fTraits = res.getStringArray(R.array.f_trait_array);
        for (int j=0;j<mTraits.length;j++) {
            Trait trait = new Trait();
            trait.setId(i);
            trait.setmName(mTraits[j]);
            trait.setfName(fTraits[j]);
            trait.setChild(false);
            trait.setTeen(false);
            realm.copyToRealm(trait);
            i++;
        }
        int[] age = res.getIntArray(R.array.child_trait_array);
        for (int c: age) {
            Trait trait = realm.where(Trait.class).equalTo("id", c+1).findFirst();
            trait.setChild(true);
        }
        age = res.getIntArray(R.array.teen_trait_array);
        for (int c: age) {
            Trait trait = realm.where(Trait.class).equalTo("id", c+1).findFirst();
            trait.setTeen(true);
        }
        String[] pair = res.getStringArray(R.array.incompatible_trait_array);
        for (String p: pair) {
            String[] traits = p.split(",");
            Trait trait1 = realm.where(Trait.class).equalTo("id", Integer.parseInt(traits[0])+1).findFirst();
            Trait trait2 = realm.where(Trait.class).equalTo("id", Integer.parseInt(traits[0])+1).findFirst();
            trait1.getIncompatibleTraits().add(trait2);
            trait2.getIncompatibleTraits().add(trait1);
        }
        realm.commitTransaction();
    }

}
