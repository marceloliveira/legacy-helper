package org.attalaya.legacyhelper.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.attalaya.legacyhelper.controller.DataController;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Marcel on 03/06/2015.
 */
public class DataRealmSpinnerAdapter<E extends RealmObject> extends RealmBaseAdapter<E> implements SpinnerAdapter {

    private DataController dataController;

    public DataRealmSpinnerAdapter(Context context,
                                   RealmResults<E> realmResults) {
        super(context, realmResults, true);
        dataController = DataController.getInstance(context);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public E getItem(int i) {
        if (i == 0)
            return null;
        return realmResults.get(i - 1);
    }

    public int getPosition(E item) {
        int position = -1;
        for (int i = 0; i < realmResults.size(); i++) {
            if (dataController.equalsObjects(realmResults.get(i), item)) {
                position = i;
            }
        }
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            ((TextView) convertView).setText("");
            return convertView;
        } else {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            E item = realmResults.get(position - 1);
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
            E item = realmResults.get(position - 1);
            ((TextView) convertView).setText(dataController.getDefaultName(item));
            return convertView;
        }
    }
}
