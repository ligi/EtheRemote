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
    var syncingTv: TextView? = null

    var running: Boolean = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.title_stats)

        if (App.getSettings().onboardingState == Settings.ONBOARDING_NONE) {
            startActivity(Intent(this, ConnectionSettingsActivity().javaClass))
            App.getSettings().onboardingState = Settings.ONBOARDING_SETTINGS
            finish()
            return
        }

        verticalLayout() {
            blockNumberTV = textView("No Data yet")
            peerCountTV = textView()
            isMiningTV = textView()
            hashRateTV = textView()
            ethVersionTV = textView()
            syncingTv = textView()
        }.setPadding(dip(8), dip(8), dip(8), dip(8))

    }

    override fun onResume() {
        super.onResume()
        running = true
        Thread(Runnable {
            while (running) {
                try {
                    val blockNumber = App.getCommunicator().getBlockNumber()

                    if (blockNumber == null) {
                        runOnUiThread {
                            blockNumberTV!!.text = "cannot connect";
                        }
                    } else {
                        val peerCount = App.getCommunicator().getPeerCount()
                        val ethVersion = App.getCommunicator().getEthVersion()
                        val isMining = App.getCommunicator().isMining()
                        val hashRate = App.getCommunicator().getHashRate()
                        val sync = App.getCommunicator().getSyncState()

                        runOnUiThread {
                            //blockNumberTV!!.text = "Block #$blockNumber"
                            peerCountTV!!.text = "Peers: $peerCount"
                            ethVersionTV!!.text = "EthVersion: $ethVersion"
                            isMiningTV!!.text = "isMining: $isMining"
                            hashRateTV!!.text = "hashRate: $hashRate Hashes/s"
                            syncingTv!!.text = "startingBlock: ${sync.startingBlock}" +
                                    "\ncurrentBlock: ${sync.currentBlock}" +
                                    "\nhighestBlock: ${sync.highestBlock}"
                        }
                    }

                } catch (e: Exception) {
                }
                Thread.sleep(300);

            }
        }).start()


    }

    override fun onPause() {
        super.onPause()
        running = false
    }
}
