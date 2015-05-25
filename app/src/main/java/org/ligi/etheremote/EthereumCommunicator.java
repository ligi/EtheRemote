package org.ligi.etheremote;

import com.github.arteam.simplejsonrpc.client.JsonRpcClient;
import com.github.arteam.simplejsonrpc.client.Transport;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EthereumCommunicator {

    private final JsonRpcClient rpcClient;

    public EthereumCommunicator() {
        final OkHttpClient client = new OkHttpClient();

        rpcClient = new JsonRpcClient(new Transport() {

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
    }



    @Nullable
    private Integer getIntegerFromMethod(final String eth_blockNumber) {
        try {
            String res=rpcClient.createRequest().method(eth_blockNumber).id(1).execute().toString();
            return Integer.parseInt(res.replace("0x", ""), 16);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public Integer getBlockNumber() {
        return getIntegerFromMethod("eth_blockNumber");
    }

    @Nullable
    public Integer getPeerCount() {
        return getIntegerFromMethod("net_peerCount");
    }
}
