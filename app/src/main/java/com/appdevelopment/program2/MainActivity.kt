package com.appdevelopment.program2

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var mEditAmount: EditText? = null
    private var mEditPercentage: EditText? = null
    private var mEditSplit: EditText? = null
    private lateinit var showMethod: Button

    private lateinit var tipAmountOutput: TextView
    private lateinit var amountDueOutput: TextView
    private lateinit var numberOfSplitPerPerson: TextView
    private lateinit var RR_for_output: RelativeLayout

    var numberOfSplits: Int = 1

    private var choiceBoolean: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMethod = findViewById(R.id.btn_show_method)
        mEditAmount = findViewById(R.id.total_amount);
        mEditPercentage = findViewById(R.id.tip_percentage)
        mEditSplit = findViewById(R.id.et_People)


        tipAmountOutput = findViewById(R.id.tipAmountOutput);
        amountDueOutput = findViewById(R.id.amountDueOutput);
        numberOfSplitPerPerson = findViewById(R.id.LblPeople);
        RR_for_output = findViewById(R.id.RR_for_output);

        showMethod.setOnClickListener {

            showDialog()
        }

    }

    private fun showDialog() {

        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
        val viewGroup: ViewGroup = this.findViewById(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(this)
                .inflate(R.layout.layout_dialog, viewGroup, false)
        val btnTotalBillToNear = dialogView.findViewById<Button>(R.id.btn_total_bill_to_near)
        val btnTipToNearest = dialogView.findViewById<Button>(R.id.btn_tip_to_nearest)
        val btnNoRounding = dialogView.findViewById<Button>(R.id.btn_no_rounding)
        builder.setView(dialogView)
        val alertDialog = builder.create()

        btnTotalBillToNear.setOnClickListener {

            choiceBoolean = 1
            alertDialog.cancel()
            calculateBill()
        }
        btnTipToNearest.setOnClickListener {
            choiceBoolean = 0
            if (mEditPercentage!!.text.toString().trim() == "") {
                mEditPercentage!!.error = "This field can not be blank"
                alertDialog.cancel()
            } else {
                alertDialog.cancel()
                calculateBill()
            }
        }
        btnNoRounding.setOnClickListener {
            choiceBoolean = 2
            alertDialog.cancel()
            calculateBill()

        }

        alertDialog.show()


    }

    private fun calculateBill() {
        RR_for_output.visibility = View.VISIBLE

        numberOfSplits = if (mEditSplit!!.text.isNullOrEmpty()) {
            1
        } else {
            val getString = mEditSplit!!.text.toString()
            getString.toInt()
        }
        when (choiceBoolean) {
            0 -> {
                val amount: Double = mEditAmount!!.getText().toString().toDouble()

                val tip: Double = mEditPercentage!!.text.toString().toDouble() * 0.01
                val tipAmount = tip * amount
                val tipAmountString = "$" + String.format("%.0f", tipAmount)
                tipAmountOutput.text = tipAmountString
                val amountDue = amount + tipAmount
                val amountDueString = "$" + String.format("%.2f", amountDue)
                amountDueOutput.text = amountDueString
                val numberOfSplitPerPersonInt = amountDue / numberOfSplits
                val numberOfSplitPerPersonString =
                    "$" + String.format("%.2f", numberOfSplitPerPersonInt)
                numberOfSplitPerPerson.text = numberOfSplitPerPersonString


            }
            1 -> {
                val amount: Double = mEditAmount!!.text.toString().toDouble()
                var tip: Double = mEditPercentage!!.text.toString().toDouble() * 0.01
                val tipAmount = tip * amount

                val tipAmountString = "$" + String.format("%.2f", tipAmount)
                tipAmountOutput.text = tipAmountString
                val amountDue = amount + tipAmount
                val amountDueString = "$" + String.format("%.0f", amountDue)
                amountDueOutput.text = amountDueString

                val numberOfSplitPerPersonInt = amountDue / numberOfSplits
                val numberOfSplitPerPersonString =
                    "$" + String.format("%.2f", numberOfSplitPerPersonInt)
                numberOfSplitPerPerson.text = numberOfSplitPerPersonString
            }
            2 -> {
                val amount: Double = mEditAmount!!.text.toString().toDouble()
                var amountDue = amount
                val amountDueString = "$" + String.format("%.2f", amountDue)
                amountDueOutput.text = amountDueString

                val numberOfSplitPerPersonInt = amountDue / numberOfSplits
                val numberOfSplitPerPersonString =
                    "$" + String.format("%.2f", numberOfSplitPerPersonInt)
                numberOfSplitPerPerson.text = numberOfSplitPerPersonString
            }
        }

    }

}