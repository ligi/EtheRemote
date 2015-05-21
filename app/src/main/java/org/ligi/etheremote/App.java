package org.ligi.etheremote;

import android.app.Application;

public class App extends Application {

    private static Settings settings;

    @Override
    public void onCreate() {
        super.onCreate();
        settings=new Settings(this);
    }

    public static Settings getSettings() {
        return settings;
    }

}
