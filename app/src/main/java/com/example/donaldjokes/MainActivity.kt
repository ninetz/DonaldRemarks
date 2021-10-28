package com.example.donaldjokes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.json.JSONTokener

class MainActivity : AppCompatActivity() {
    lateinit var queue : RequestQueue
    lateinit var quoteDisplayText : TextView
    lateinit var quotePersonalizeText : TextView
    lateinit var buttonDisplayText : Button
    lateinit var buttonPersonalizeText : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        queue = Volley.newRequestQueue(this)
        quoteDisplayText = findViewById<TextView>(R.id.textView)
        quotePersonalizeText  = findViewById<TextView>(R.id.idEditText)
        buttonDisplayText = findViewById<Button>(R.id.IdRandomQuoteButton)
        buttonPersonalizeText  = findViewById<Button>(R.id.idPersonalizeButton)

    buttonDisplayText.setOnClickListener() {
        simpleQuote()
    }
    buttonPersonalizeText.setOnClickListener() {
    personalizedQuote(quotePersonalizeText.text.toString())
    }



    simpleQuote()

    }
    fun personalizedQuote(pText: String) {

        val url = "https://api.whatdoestrumpthink.com/api/v1/quotes/personalized?q=$pText"
        val stringRequest = JsonObjectRequest( Request.Method.GET, url, null,
            Response.Listener { response ->
                quoteDisplayText.text = parseJsonResponse(response)
            },
            Response.ErrorListener { quoteDisplayText.text = "An error occured! Check if you are connected to the internet!"
            }
        )
        queue.add(stringRequest)

    }
    fun simpleQuote() {
        val url = "https://api.whatdoestrumpthink.com/api/v1/quotes/random"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                quoteDisplayText.text = parseJsonResponse(response)
            },
            Response.ErrorListener { error ->
                quoteDisplayText.text = "An error occured! Check if you are connected to the internet!"
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun parseJsonResponse(pResponse: JSONObject): String {

        return String(pResponse.getString("message").toByteArray(), charset("UTF-8"))
    }
}
