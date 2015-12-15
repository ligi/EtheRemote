package org.ligi.etheremote

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import org.jetbrains.anko.*
import java.math.BigInteger
import java.util.HashMap

public class WalletActivity : EtheremoteActivity() {

    var accountsTV: TextView? = null
    var amountTV: TextView? = null

    var fromSpinner: Spinner? = null
    var toSpinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.title_wallet)


        verticalLayout() {
            accountsTV = textView()

            linearLayout() {
                textView("from:")
                fromSpinner = spinner()
            }

            linearLayout() {
                textView("to:")
                toSpinner = spinner()
            }

            amountTV = editText("0") {
                inputType = InputType.TYPE_CLASS_NUMBER
            }

            button("Transfer") {
                onClick {
                    Thread(Runnable {
                        App.getCommunicator().sendTransaction(fromSpinner!!.selectedItem as String, toSpinner!!.selectedItem as String,
                                amountTV!!.text.toString(),
                                onSuccess = { result ->
                                    runOnUiThread {
                                        AlertDialog.Builder(ctx).setMessage("success" + result).show()
                                    }
                                },
                                onError = { msg ->
                                    runOnUiThread {
                                        AlertDialog.Builder(ctx).setMessage("error" + msg).show()
                                    }
                                }
                        )
                    }).start()
                }
            }

        }.setPadding(dip(8), dip(8), dip(8), dip(8))

        Thread(Runnable {
            var hasData = false;
            while (!hasData) {
                try {
                    val accounts = App.getCommunicator().getAccounts()
                    val balances = HashMap<String, BigInteger>()

                    accounts?.forEach { account ->
                        balances.put(account, App.getCommunicator().getBalance(account))
                    }

                    runOnUiThread {
                        if (accounts == null) {
                            accountsTV!!.text = "cannot get Accounts"
                        } else {

                            fromSpinner!!.adapter = AccountSpinner(accounts, balances)
                            toSpinner!!.adapter = AccountSpinner(accounts, balances)
                            hasData = true
                        }
                    }
                    Thread.sleep(300)
                } catch (e: Exception) {
                    Log.e("", "", e);
                }

            }
        }).start()


    }


    inner class AccountSpinner(accounts: List<String>?, balances: HashMap<String, BigInteger>) : BaseAdapter(), SpinnerAdapter {

        val accounts = accounts!!
        val balances = balances

        override fun getCount(): Int {
            return accounts.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            return getTV(position, 23)
        }

        private fun getTV(position: Int, i: Int): TextView {
            val res = TextView(ctx)
            res.textSize = i.toFloat()
            val account = accounts[position]
            res.text = account.substring(2, 10) + "(${UnitConverter.humanize(balances[account])})"
            return res
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            return getTV(position, 26)
        }

        override fun getItem(position: Int): Any? {
            return accounts[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

    }
}
