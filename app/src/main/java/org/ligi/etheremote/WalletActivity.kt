package org.ligi.etheremote

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.jetbrains.anko.dip
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import java.math.BigInteger
import java.util.HashMap

public class WalletActivity : EtheremoteActivity() {

    var accountsTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.title_wallet)


        verticalLayout() {
            accountsTV = textView()
        }.setPadding(dip(8), dip(8), dip(8), dip(8))

        Thread(object : Runnable {
            override fun run() {

                while (true) {
                    try {
                        val accounts = App.getCommunicator().getAccounts()


                        val balances = HashMap<String, BigInteger>()

                        accounts?.forEach { account ->
                            balances.put(account, App.getCommunicator().getBalance(account))
                        }

                        runOnUiThread(object : Runnable {
                            override fun run() {
                                if (accounts == null) {
                                    accountsTV!!.setText("cannot get Accounts")
                                } else {
                                    var res = "";
                                    accounts.forEach { account ->
                                        res += account + "\n-> " + UnitConverter.humanize(balances.get(account))+"\n";
                                    }
                                    accountsTV!!.setText(res)
                                }


                            }
                        })
                        Thread.sleep(300)
                    } catch (e: Exception) {
                        Log.e("","",e);
                    }

                }
            }
        }).start()


    }
}