package com.example.mocktest

import android.util.Log
import com.github.theapache64.retrosheet.RetrosheetInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ReadData {
    private val mQuizList = ArrayList<QuizItem>(1)
    fun getQuizItems(): ArrayList<QuizItem> {
        // Building Retrosheet Interceptor
        val retrosheetInterceptor = RetrosheetInterceptor.Builder()
            .setLogging(false)
            // To Read
            .addSheet(
                "Koroad_Quiz", // sheet name
                "problem","examples1","examples2","examples3","examples4","examples5","explanation","answer", "video","image" // columns in same order
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
                for(i in 1..998) {
                    val problem = response.body()?.get(i)?.problem
                    val example1 = response.body()?.get(i)?.examples1
                    val example2 = response.body()?.get(i)?.examples2
                    val example3 = response.body()?.get(i)?.examples3
                    val example4 = response.body()?.get(i)?.examples4
                    val example5 = response.body()?.get(i)?.examples5
                    val explanation = response.body()?.get(i)?.explanation
                    val answer = response.body()?.get(i)?.answer
                    val video = response.body()?.get(i)?.video
                    val image = response.body()?.get(i)?.image
                    val data = QuizItem(
                        i, problem, example1, example2, example3, example4,
                        example5, explanation, answer, video, image
                    )
                    mQuizList.add(data)
                }
            }

            override fun onFailure(call: Call<List<Read>>, t: Throwable) {
                Log.d("API Fail", t.message.toString())
            }

        })
        return mQuizList
    }
}