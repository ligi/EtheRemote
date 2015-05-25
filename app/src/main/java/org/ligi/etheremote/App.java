package org.ligi.etheremote;

import android.app.Application;

public class App extends Application {

    private static Settings settings;
    private static EthereumCommunicator communicator;

    @Override
    public void onCreate() {
        super.onCreate();
        settings=new Settings(this);
        communicator = new EthereumCommunicator();
    }

    public static Settings getSettings() {
        return settings;
    }

    public static EthereumCommunicator getCommunicator() {
        return communicator;
    }

}
