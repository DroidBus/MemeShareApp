package com.example.memeshareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var memeUrl:String
    lateinit var memeView:ImageView
    lateinit var nextButton: Button
    lateinit var shareButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        memeView=findViewById(R.id.imageView)
        loadMeme()
    }

    private fun loadMeme(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                run {
                    val jsonObject = JSONObject(response)
                    memeUrl=jsonObject.getString("url")
                    Log.i("msg", memeUrl)
                    Glide.with(this)
                        .load(memeUrl)
                        .into(memeView)
                }
            },
            Response.ErrorListener {

            })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun nextMeme(view: View) {
      loadMeme()
    }
    fun shareMeme(view: View) {
        val intent=Intent()
        intent.setAction(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"Share using")
        intent.setType("text/plain")
        startActivity(intent)
    }
}