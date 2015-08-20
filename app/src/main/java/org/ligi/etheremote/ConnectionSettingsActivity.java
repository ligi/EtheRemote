package org.ligi.etheremote;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;


public class ConnectionSettingsActivity extends EtheremoteActivity {

    private Settings settings;

    @Bind(R.id.help)
    TextView textView;

    @Bind(R.id.host)
    TextView host;

    @Bind(R.id.port)
    TextView port;


    @OnTextChanged(R.id.port)
    void portChange() {
        try {
            settings.setPort(Integer.parseInt(port.getText().toString()));
        } catch (NumberFormatException ignored) {
        }
    }


    @OnTextChanged(R.id.host)
    void hostChange() {
        settings.setHost(host.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_settings);

        setTitle(R.string.title_settings);

        ButterKnife.bind(this);

        textView.setText(Html.fromHtml(getText(R.string.connection_help).toString()));
        textView.setMovementMethod(new LinkMovementMethod());

        settings = App.getSettings();

        host.setText(settings.getHost());
        port.setText(String.valueOf(settings.getPort()));
    }

}
