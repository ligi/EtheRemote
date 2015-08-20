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
        ctx.startActivity(new Intent(ctx, StatsActivity.class));
    }

    @OnClick(R.id.drawer_wallet)
    void walletClick() {
        ctx.startActivity(new Intent(ctx, WalletActivity.class));
    }

    @OnClick(R.id.drawer_settings)
    void settingsClick() {
        ctx.startActivity(new Intent(ctx, ConnectionSettingsActivity.class));
    }

    @OnClick(R.id.drawer_blocks)
    void blocksClick() {
        ctx.startActivity(new Intent(ctx, BlockListActivity.class));
    }

    public DrawerActions(ViewGroup root) {
        ctx = root.getContext();
        rootView = root;
    }

    public void bind() {
        ButterKnife.bind(this, rootView);
    }
}
