package com.tippy_kotlin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView


private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENTT = 15
class MainActivity : AppCompatActivity() {

    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tipdescription: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip= findViewById(R.id.seekBarTip)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTipAmount=findViewById(R.id.tvTipAmount)
        tvTotalAmount= findViewById(R.id.tvTotalAmount)
        tipdescription= findViewById(R.id.tipdescription)

        seekBarTip.progress=INITIAL_TIP_PERCENTT
        tvTipPercentLabel.text="$INITIAL_TIP_PERCENTT%"
        updatetipdescription(INITIAL_TIP_PERCENTT)
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG,"onProgressChanged $progress")
                tvTipPercentLabel.text="$progress%"

                computetipAndtotal()
                updatetipdescription(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        etBaseAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"aftertextchanged $p0")
                computetipAndtotal()
            }

        })
    }

    private fun updatetipdescription(tipprecent: Int) {
        val tipdescpr= when (tipprecent){
            in 0..9 ->"Poor"
            in 10..14 ->"Acceptable"
            in 15..19 ->"Good"
            in 20..25 ->"Great"
            else -> "Amazing"
        }
        tipdescription.text=tipdescpr
    }

    private fun computetipAndtotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text=""
            tvTotalAmount.text=""
            return
        }
        // get the value to amount & tip
        val baseAmount =  etBaseAmount.text.toString().toDouble()
        Log.i(TAG,"base amount $baseAmount")
        val tipercent = seekBarTip.progress
        Log.i(TAG,"tip percent $tipercent")
        //compute the tip and total
        val tipamount = baseAmount * tipercent / 100
        Log.i(TAG,"tip amount $tipamount")
        val totalamount = baseAmount + tipamount
        Log.i(TAG,"total amount $totalamount")
        //update ui
        tvTipAmount.text = "%.2f".format(tipamount)
        tvTotalAmount.text = "%.2f".format(totalamount)

    }
}