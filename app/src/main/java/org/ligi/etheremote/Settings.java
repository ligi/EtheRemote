package org.ligi.etheremote;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {

    public final static int ONBOARDING_NONE=0;
    public final static int ONBOARDING_SETTINGS=1;
    public final static int ONBOARDING_DRAWER=2;

    private final Context ctx;

    public Settings(Context ctx) {
        this.ctx=ctx;
    }

    public int getOnboardingState() {
        return getPrefs().getInt("ONBOARDING",0);
    }

    public void setOnboardingState(int newState) {
        getPrefs().edit().putInt("ONBOARDING",newState).apply();
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
}
