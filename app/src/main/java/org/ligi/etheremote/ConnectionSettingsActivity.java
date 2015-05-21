package org.ligi.etheremote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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


public class ConnectionSettingsActivity extends EtheremoteActivity {

    @InjectView(R.id.help)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_settings);

        ButterKnife.inject(this);

        textView.setText(Html.fromHtml(getText(R.string.connection_help).toString()));
        textView.setMovementMethod(new LinkMovementMethod());
    }
}
