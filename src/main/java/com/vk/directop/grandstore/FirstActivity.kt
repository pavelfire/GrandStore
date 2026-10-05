package com.vk.directop.grandstore

import android.R.attr.phoneNumber
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText = findViewById<EditText>(R.id.et)
        val btnOpenSecond = findViewById<Button>(R.id.btnSecondActivity)
        val btnDial = findViewById<Button>(R.id.buttonDial)
        val btnShare = findViewById<Button>(R.id.buttonShare)

        btnOpenSecond.setOnClickListener {
            val textToSend = editText.text.toString()

            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("EXTRA_TEXT", textToSend)
            }
            startActivity(intent)
        }

        btnDial.setOnClickListener {
            val phoneNumber = editText.text.toString().trim()

            val digitsOnlyRegex = "^[0-9]+$".toRegex()

            if (phoneNumber.isNotEmpty() && phoneNumber.matches(digitsOnlyRegex)) {
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }

                try {
                    startActivity(callIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        this,
                        R.string.error_no_dialer_app,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                editText.error = getText(R.string.error_invalid_phone)
            }
        }

        btnShare.setOnClickListener {
            val message = editText.text.toString()

            if (message.isNotEmpty()) {
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                }

                val chooserIntent = Intent.createChooser(sendIntent, getText(R.string.share_by))

                try {
                    startActivity(chooserIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        this,
                        R.string.error_no_share_app,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                editText.error = getText(R.string.error_empty_field)
            }
        }
    }
}
