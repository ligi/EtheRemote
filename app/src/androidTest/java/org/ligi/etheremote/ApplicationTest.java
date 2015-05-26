package org.ligi.etheremote;

import android.app.Application;
import android.preference.PreferenceManager;
import android.test.ApplicationTestCase;

public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().clear().commit();
        super.setUp();
    }

    public void testThatDefaultPortWorks() {
        assertEquals(App.getSettings().getPort(), 8079);
    }

    public void testThatDefaultHostWorks() {
        assertEquals(App.getSettings().getHost(), "192.168.1.");
    }

    public void testThatPortSettingsWorks() {
        App.getSettings().setPort(42);
        assertEquals(App.getSettings().getPort(), 42);
    }

    public void testThatHostSettingWorks() {
        App.getSettings().setHost("foo");
        assertEquals(App.getSettings().getHost(), "foo");
    }
}