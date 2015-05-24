package org.ligi.etheremote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.arteam.simplejsonrpc.client.JsonRpcClient;
import com.github.arteam.simplejsonrpc.client.Transport;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;


public class MainActivity extends EtheremoteActivity {

    @InjectView(R.id.currentBlock)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);


        if (App.getSettings().getOnboardingState() == Settings.ONBOARDING_NONE) {
            startActivity(new Intent(this, ConnectionSettingsActivity.class));
            App.getSettings().setOnboardingState(Settings.ONBOARDING_SETTINGS);
            finish();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClient client = new OkHttpClient();

                final JsonRpcClient rpcClient = new JsonRpcClient(new Transport() {

                    @NotNull
                    @Override
                    public String pass(@NotNull String request2) throws IOException {
                        final Settings settings = App.getSettings();
                        Request request = new Request.Builder().url("http://" + settings.getHost() + ":" + settings.getPort())
                                                               .post(RequestBody.create(MediaType.parse("text"), request2))
                                                               .build();

                        Response response = client.newCall(request).execute();
                        return response.body().string();
                    }
                });

                try {
                    final String res = rpcClient.createRequest().method("eth_blockNumber").id(1).execute().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("" + Integer.parseInt(res.replace("0x", ""), 16));
                        }
                    });
                } catch (Exception e) {

                }
            }
        }).start();


    }
}
