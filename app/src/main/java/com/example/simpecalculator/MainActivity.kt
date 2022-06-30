package com.example.simpecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDecimal: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun append(view: View) {

        when ((view as Button).text) {
            "CLR" -> tvInput?.text = ""
            "." -> {
                if (lastNumeric && !lastDecimal) {
                    tvInput?.append((view).text)
                    lastDecimal = true
                    lastNumeric = false
                }
            }
            "+", "-", "/", "*" -> onOperator(view)
            "=" -> onEqual()
            else -> {
                tvInput?.append((view).text)
                lastDecimal = false
                lastNumeric = true
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+") ||
                    value.contains("/") ||
                    value.contains("*") ||
                    value.contains("-")
        }
    }

    private fun onOperator(view: View) {
        tvInput?.text?.let {

            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDecimal = false
            }


        }
    }

    private fun onEqual() {
        if (lastNumeric) {

            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    val result = firstValue.toDouble() - secondValue.toDouble()

                    tvInput?.text = removeZeroAfterDot(result.toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    val result = firstValue.toDouble() + secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    val result = firstValue.toDouble() * secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    val result = firstValue.toDouble() / secondValue.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())

                } else if (tvValue.contains("%")) {
                    val splitValue = tvValue.split("%")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        firstValue = prefix + firstValue
                    }
                    val result = (firstValue.toDouble() * secondValue.toDouble()) / 100.0
                    tvInput?.text = removeZeroAfterDot(result.toString())

                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result

        if (value.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value

    }

}