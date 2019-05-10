package com.espresso.moldovanbalazs.espresso;

import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.Random;


public class CommunicationThread extends Thread implements CommunicationCallback {

    //private final DataRetreivalActivity activity = null;
    private ServerSocket serverSocket;

    public boolean clientSocketClosed() {
        return clientSocket.isClosed();
    }

    public void closeClientConnection() throws IOException {
        this.clientSocket.close();
    }

    private Socket clientSocket;
    private String message;
    private DataModel data;
    private DataCallback dataCallback;
    private DataRetreivalActivity activity;

    public CommunicationThread(ServerSocket serverSocket, Socket clientSocket) {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }


    public CommunicationThread(ServerSocket serverSocket, Socket clientSocket, DataRetreivalActivity activity) {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
        this.activity = activity;
    }


    /*public CommunicationThread(ServerSocket serverSocket, Socket clientSocket, DataRetreivalActivity dataRetreivalActivity) {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
        this.activity = dataRetreivalActivity;
    }*/

    public void setDataCallback(DataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    @Override
    public void run() {
        message = "";
        BufferedReader input = null;
        Gson gson = new Gson();
        InputStream inputStream = null;
        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        byte[] byteData = new byte[14];

        while (true) {
            try {
                int count = inputStream.read(byteData);
                //message = input.readLine();
                Log.d("CLIENT MESSAGE", this.message);
                /*activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.commentField.setText(message);
                    }
                });*/

//                if(message == null) {
//                    //this.clientSocket.close();
//                } else
                if (count >= byteData.length) {

                    //this.data = gson.fromJson(message, DataModel.class);
                    this.data = DiagnosisDataFetcher.decodeData(byteData);
                    if (this.data != null) {
                        /*activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.sonarLeftField.sett
                            }
                        });*/

                        new Thread(new UpdateDataRunnable(this.data)).start();
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public DataModel getClientData() {
        return this.data;
    }

    @Override
    public String getClientMessage() {
        return message;
    }

    public interface DataCallback {
        public void updateDataModel(DataModel dataModel);
    }

}

