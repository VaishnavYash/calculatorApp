package yash.com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private var tvInputHistory: TextView? = null
    private var firstnumeric: Boolean = true
    private var lastnumeric: Boolean = false
    private var lastdot: Boolean = false
    private var onEqualpress: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
        tvInputHistory = findViewById(R.id.tvInputHistory)
    }
    fun onDigital(view: View){
        if(firstnumeric){
            tvInput?.text=""
            firstnumeric = false
        }
        tvInput?.append((view as Button).text)
        lastnumeric = true
        lastdot = false
    }
    fun onD(view: View) {
        var tD = tvInput?.text.toString();
        if(onEqualpress){
            tvInput?.text=""
        }else {
            tD = tD.dropLast(1)
            tvInput?.text = tD.toString()
        }
        onEqualpress = false
    }

    fun onEqual(view: View){
        if(lastnumeric){
            var tvText = tvInput?.text.toString()
            var prefix = ""
            try {
                if(tvText.startsWith("-")){
                    prefix ="-"
                    tvText = tvText.substring(1)
                }
                if(tvText.contains("-")) {
                    var split = tvText.split("-")
                    var one = split[0]
                    var sec = split[1]
                    if (prefix == "-") {
                        one = prefix + one
                    }
                    tvInput?.text = removeDotZero((one.toDouble() - sec.toDouble()).toString())
                } else if(tvText.contains("+")) {
                    var split = tvText.split("+")
                    var one = split[0]
                    var sec = split[1]
                    if (prefix == "-") {
                        one = prefix + one
                    }
                    tvInput?.text = removeDotZero((one.toDouble() + sec.toDouble()).toString())
                } else if(tvText.contains("x")) {
                    var split = tvText.split("x")
                    var one = split[0]
                    var sec = split[1]
                    if (prefix == "-") {
                        one = prefix + one
                    }
                    tvInput?.text = removeDotZero((one.toDouble() * sec.toDouble()).toString())
                } else if(tvText.contains("/")) {
                    var split = tvText.split("/")
                    var one = split[0]
                    var sec = split[1]
                    if (prefix == "-") {
                        one = prefix + one
                    }
                    tvInput?.text = removeDotZero((one.toDouble() / sec.toDouble()).toString())
                }

                onEqualpress = true
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeDotZero(result: String): CharSequence? {
        var value = result
        if(value.contains(".0")){
            value = value.substring(0, value.length-2)
        }
        return value
    }
    fun onClear(view: View){
        tvInput?.text=""
    }
    fun onDeci(view: View){
        if(lastnumeric && !lastdot){
            tvInput?.append(".")
            lastnumeric = false
            lastdot = true
        }
    }
    private fun onOperatorAdded(value: String):Boolean {
        return if(value.startsWith("-")){
            false
        } else {
            value.contains("+") || value.contains("-") || value.contains("x") || value.contains("/") || value.contains("%")
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastnumeric && !onOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastnumeric=false
                lastdot = false
            }
        }
    }
}

