package org.ligi.etheremote;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawerActions {

    private final Context ctx;
    private final ViewGroup rootView;

    @OnClick(R.id.drawer_stats)
    void statsClick() {
        ctx.startActivity(new Intent(ctx,MainActivity.class));
    }

    @OnClick(R.id.drawer_settings)
    void settingsClick() {
        ctx.startActivity(new Intent(ctx,ConnectionSettingsActivity.class));
    }


    public DrawerActions(ViewGroup root) {
        ctx=root.getContext();
        rootView=root;
    }

    public void bind() {
        ButterKnife.inject(this,rootView);
    }
}
