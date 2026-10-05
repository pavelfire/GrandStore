package com.vk.directop.grandstore

import android.R.attr.phoneNumber
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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

            if (phoneNumber.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }

                startActivity(callIntent)
            } else {
                editText.error = "Введите номер телефона"
            }
        }

        btnShare.setOnClickListener {
            val message = editText.text.toString()

            if (message.isNotEmpty()) {
                // Создаем базовый Intent для отправки данных
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    // Указываем MIME-тип данных (простой текст)
                    type = "text/plain"
                    // Добавляем сам текст
                    putExtra(Intent.EXTRA_TEXT, message)
                }

                // Оборачиваем в Chooser, чтобы принудительно показать системное меню выбора
                val chooserIntent = Intent.createChooser(sendIntent, "Поделиться через...")

                startActivity(chooserIntent)
            } else {
                editText.error = "Введите текст для отправки"
            }
        }
    }
}
