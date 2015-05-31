package org.ligi.etheremote

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import org.jetbrains.anko.dip
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout


public class StatsActivity : EtheremoteActivity() {

    var blockNumberTV: TextView? = null
    var peerCountTV: TextView? = null
    var ethVersionTV: TextView? = null
    var isMiningTV: TextView? = null
    var hashRateTV: TextView? = null

    var running: Boolean = true;

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
            blockNumberTV = textView("No Data yet")
            peerCountTV = textView()
            isMiningTV = textView()
            hashRateTV = textView()
            ethVersionTV = textView()
        }.setPadding(dip(8), dip(8), dip(8), dip(8))

    }

    override fun onResume() {
        super.onResume()
        running = true
        Thread(object : Runnable {
            override fun run() {

                while (running) {
                    try {
                        val blockNumber = App.getCommunicator().getBlockNumber()

                        if (blockNumber == null) {
                            runOnUiThread {
                                blockNumberTV!!.setText("cannot connect");
                            }
                        } else {
                            val peerCount = App.getCommunicator().getPeerCount()
                            val ethVersion = App.getCommunicator().getEthVersion()
                            val isMining = App.getCommunicator().isMining()
                            val hashRate = App.getCommunicator().getHashRate()

                            runOnUiThread {
                                blockNumberTV!!.setText("Block #${blockNumber}")
                                peerCountTV!!.setText("Peers: ${peerCount}")
                                ethVersionTV!!.setText("EthVersion: ${ethVersion}")
                                isMiningTV!!.setText("isMining: ${isMining}")
                                hashRateTV!!.setText("hashRate: ${hashRate} Hashes/s")
                            }
                        }

                    } catch (e: Exception) {
                    }
                    Thread.sleep(300);

                }
            }
        }).start()


    }

    override fun onPause() {
        super.onPause()
        running = false
    }
}
