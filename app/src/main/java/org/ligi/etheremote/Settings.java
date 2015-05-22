package org.ligi.etheremote;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {

    public final static int ONBOARDING_NONE = 0;
    public final static int ONBOARDING_SETTINGS = 1;
    public final static int ONBOARDING_DRAWER = 2;


    private static final String KEY_ONBOARDING = "ONBOARDING";
    private static final String KEY_PORT = "PORT";
    private static final String KEY_HOST = "HOST";

    private final Context ctx;

    public Settings(Context ctx) {
        this.ctx = ctx;
    }

    public int getOnboardingState() {
        return getPrefs().getInt(KEY_ONBOARDING, 0);
    }

    public void setOnboardingState(int newState) {
        getPrefs().edit().putInt(KEY_ONBOARDING, newState).apply();
    }


    public int getPort() {
        return getPrefs().getInt(KEY_PORT, 8079);
    }

    public void setPort(int port) {
        getPrefs().edit().putInt(KEY_PORT, port).apply();
    }

    public String getHost() {
        return getPrefs().getString(KEY_HOST, "192.168.1.");
    }

    public void setHost(String host) {
        getPrefs().edit().putString(KEY_HOST, host).apply();
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
}
