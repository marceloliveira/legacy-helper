package org.attalaya.legacyhelper.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import org.attalaya.legacyhelper.controller.LegacyController;
import org.attalaya.legacyhelper.view.LegacyHelperTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Marcel on 06/06/2015.
 */
public class RealmListAdapter<E extends RealmObject> extends RealmBaseAdapter<E> implements ListAdapter {

    private static class ViewHolder {
        HashMap<Integer,View> views;
    }

    private LegacyController controller;
    private int layout;
    private View.OnClickListener onClickListener;

    public RealmListAdapter(Context context,
                            RealmResults<E> realmResults, int layout) {
        super(context, realmResults, true);
        this.layout = layout;
        this.controller = LegacyController.getInstance(context);
    }

    private ArrayList<View> getAllChilds(View view) {
        ArrayList<View> views = new ArrayList<>();
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View v = ((ViewGroup) view).getChildAt(i);
                views.add(v);
                if (v instanceof ViewGroup) {
                    views.addAll(getAllChilds(v));
                }
            }
        }
        return views;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layout,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.views = new HashMap<>();
            for (View v: getAllChilds(convertView)) {
                viewHolder.views.put(v.getId(),v);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        E item = realmResults.get(position);
        for (Entry<Integer, View> e: viewHolder.views.entrySet()) {
            View v = e.getValue();
            if (v instanceof LegacyHelperTextView) {
                String realmField = ((LegacyHelperTextView) v).getRealmField();
                try {
                    String value = controller.getStringFieldByName(item, realmField);
                    ((LegacyHelperTextView) v).setText(value);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (onClickListener!=null) {
                if (v.isFocusable()) {
                    v.setOnClickListener(onClickListener);
                }
            }
        }
        return convertView;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public RealmResults<E> getRealmResults() {
        return realmResults;
    }
}
