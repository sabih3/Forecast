package com.sahmed.forecaster.framework.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.*


object FileUtils {

    private fun readFromFile(context: Context, fileName: String): String? {
        var ret = ""
        try {
            val inputStream: InputStream = context.getAssets().open(fileName)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also({ receiveString = it }) != null) {
                    stringBuilder.append(receiveString)
                }
                inputStream.close()
                ret = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: " + e.toString())
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: " + e.toString())
        }
        return ret
    }

    fun getCityList(context:Context): CityLookup? {
        val countriesJson = readFromFile(context, "city.list.min.json")
//        val countriesJson = readFromFile(context, "city.test.json")

        val gson = Gson()

        return gson.fromJson(
            countriesJson,
            CityLookup::class.java)
    }
}