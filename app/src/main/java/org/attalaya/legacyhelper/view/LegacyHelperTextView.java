package org.attalaya.legacyhelper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.TextView;

import org.attalaya.legacyhelper.R;

/**
 * Created by Marcel on 05/06/2015.
 */
public class LegacyHelperTextView extends TextView {

    private String realmField;

    public LegacyHelperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LegacyHelperTextView);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.LegacyHelperTextView_pack:
                    String pack = a.getString(attr);
                    if (!PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getBoolean(pack,false)) {
                        this.setVisibility(GONE);
                    }
                    break;
                case R.styleable.LegacyHelperTextView_realmField:
                    realmField = a.getString(attr);
                    break;
            }
        }
        a.recycle();
    }

    public String getRealmField() {
        return realmField;
    }
}
