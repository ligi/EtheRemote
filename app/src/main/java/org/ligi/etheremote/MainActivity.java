package org.ligi.etheremote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends EtheremoteActivity {

    @InjectView(R.id.currentBlock)
    TextView textView;

    @InjectView(R.id.peerCount)
    TextView peerCountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.title_stats);

        if (App.getSettings().getOnboardingState() == Settings.ONBOARDING_NONE) {
            startActivity(new Intent(this, ConnectionSettingsActivity.class));
            App.getSettings().setOnboardingState(Settings.ONBOARDING_SETTINGS);
            finish();
            return;
        }

        ButterKnife.inject(this);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        final int blockNumber = App.getCommunicator().getBlockNumber();
                        final Integer peerCount = App.getCommunicator().getPeerCount();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("#" + blockNumber);
                                peerCountTV.setText("" + peerCount);
                            }
                        });
                        Thread.sleep(300);
                    } catch (Exception e) {

                    }
                }
            }
        }).start();


    }
}
