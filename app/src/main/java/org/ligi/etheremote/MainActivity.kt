package org.ligi.etheremote

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import butterknife.ButterKnife
import org.jetbrains.anko.dip
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout


public class MainActivity : EtheremoteActivity() {

    var blockNumberTV: TextView? = null
    var peerCountTV: TextView? = null
    var ethVersionTV: TextView? = null
    var isMiningTV: TextView? = null
    var hashRateTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.title_stats)

        if (App.getSettings().getOnboardingState() == Settings.ONBOARDING_NONE) {
            startActivity(Intent(this, javaClass<ConnectionSettingsActivity>()))
            App.getSettings().setOnboardingState(Settings.ONBOARDING_SETTINGS)
            finish()
            return
        }

        verticalLayout() {
            peerCountTV = textView()
            blockNumberTV = textView()
            isMiningTV = textView()
            hashRateTV = textView()
            ethVersionTV = textView()
        }.setPadding(dip(8),dip(8),dip(8),dip(8))

        Thread(object : Runnable {
            override fun run() {

                while (true) {
                    try {
                        val blockNumber = App.getCommunicator().getBlockNumber()!!
                        val peerCount = App.getCommunicator().getPeerCount()
                        val ethVersion = App.getCommunicator().getEthVersion()
                        val isMining = App.getCommunicator().isMining()
                        val hashRate = App.getCommunicator().getHashRate()

                        runOnUiThread(object : Runnable {
                            override fun run() {
                                blockNumberTV!!.setText("Block #${blockNumber}")
                                peerCountTV!!.setText("Peers: ${peerCount}")
                                ethVersionTV!!.setText("EthVersion: ${ethVersion}")
                                isMiningTV!!.setText("isMining: ${isMining}")
                                hashRateTV!!.setText("hashRate: ${hashRate} Hashes/s")
                            }
                        })
                        Thread.sleep(300)
                    } catch (e: Exception) {
                    }

                }
            }
        }).start()


    }
}
