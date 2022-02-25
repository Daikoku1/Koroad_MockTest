package com.example.mocktest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mocktest.ui.theme.MockTestTheme
import com.github.theapache64.retrosheet.RetrosheetInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            var data = ReadData.getQuizItems()
//            Log.d("API Main", data[0].problem.toString())
            MockTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
    private fun readData() {
        // Building Retrosheet Interceptor
        val retrosheetInterceptor = RetrosheetInterceptor.Builder()
            .setLogging(false)
            // To Read
            .addSheet(
                "Koroad_Quiz", // sheet name
                "problem",
                "examples1",
                "examples2",
                "examples3",
                "examples4",
                "examples5",
                "explanation",
                "answer",
                "video",
                "image" // columns in same order
            )
            .build()

        // Building OkHttpClient
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(retrosheetInterceptor) // and attaching interceptor
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory()).build()

        // Building retrofit client
        val retrofit = Retrofit.Builder()
            // with baseUrl as sheet's public URL
            .baseUrl("https://docs.google.com/spreadsheets/d/1PU4g_0kcMWkQQLyD-ABxJ-eNWvFJB7XPiQDey6WRSoc/") // Sheet's public URL
            // and attach previously created OkHttpClient
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        // Now create the API interface
        val notesApi = retrofit.create(ApiInterface::class.java)
        val call = notesApi.getNotes()
        call.enqueue(object : Callback<List<Read>> {
            override fun onResponse(call: Call<List<Read>>, response: Response<List<Read>>) {
                Log.d("API response", response.body().toString())
                Log.d("API response2", response.body()?.get(3)?.problem.toString())
            }

            override fun onFailure(call: Call<List<Read>>, t: Throwable) {
                Log.d("API Fail", t.message.toString())
            }

        })
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MockTestTheme {
        Greeting("Android")
    }
}