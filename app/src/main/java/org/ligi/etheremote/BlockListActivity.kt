package org.ligi.etheremote

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.*
import java.util.ArrayList

public class BlockListActivity : EtheremoteActivity() {

    var currentBlockNum: Int = 0
    var currentBlockTV: EditText? = null
    var dataTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout() {
            linearLayout() {
                button("<") {
                    onClick {
                        if (currentBlockNum > 0) {
                            currentBlockNum--
                            refresh()
                        }
                    }
                }
                currentBlockTV = editText {
                    inputType = InputType.TYPE_CLASS_NUMBER

                    textChangedListener {
                        onTextChanged { text, start, before, count ->
                            try {
                                val newBlock = Integer.parseInt(currentBlockTV!!.text.toString())
                                if (currentBlockNum != newBlock) {
                                    currentBlockNum = newBlock
                                    refresh()
                                }

                            } catch (ignored: Exception) {}
                        }
                    }
                }
                button(">") {
                    onClick {
                        currentBlockNum++
                        refresh()
                    }
                }

            }
            scrollView() {
                dataTV = textView("no data yet")
            }
        }
        refresh()
    }


    fun refresh() {

        val selStart = currentBlockTV!!.selectionStart
        currentBlockTV!!.setText("$currentBlockNum")
        currentBlockTV!!.setSelection(selStart)

        async {
            var infoMap = App.getCommunicator().getBlockByNumber(currentBlockNum)
            val keys = infoMap.keySet()

            var stringToDisplay = ""

            keys.forEach { key ->
                val value = infoMap[key]
                if (value is String)
                    stringToDisplay += "$key=$value\n"
                else if (value is ArrayList<*>)
                    stringToDisplay +=  "$key=${value.size()}\n"

            }

            runOnUiThread {
                dataTV!!.text = stringToDisplay
            }
        }
    }

}