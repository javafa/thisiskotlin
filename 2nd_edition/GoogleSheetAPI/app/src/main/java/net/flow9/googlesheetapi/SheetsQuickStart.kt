package net.flow9.googlesheetapi

import android.util.Log
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*


object SheetsQuickstart {
    private const val APPLICATION_NAME = "Google Sheets API Java Quickstart"
    private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    private const val TOKENS_DIRECTORY_PATH = "tokens"

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private val SCOPES: List<String> = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
    private const val CREDENTIALS_FILE_PATH = "/credentials.json"

    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        // Load client secrets.
        val `in` = SheetsQuickstart::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH)
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
        )
            .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    /**
     * range : ex) "Class Data!A2:E"
     */
    fun getData(spreadSheetId: String, range: String) {
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        val service = Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build()
        val response: ValueRange = service.spreadsheets().values()[spreadSheetId, range].execute()
        val values: List<List<Any>> = response.getValues()
        if (values == null || values.isEmpty()) {
            Log.e("SheetsQuickstart", "No data found.")
        } else {
            for (row in values) {
                // 데이터 확인
               Log.d("SheetsQuickstart", "${row[0]}, ${row[1]}")
            }
        }
    }
}