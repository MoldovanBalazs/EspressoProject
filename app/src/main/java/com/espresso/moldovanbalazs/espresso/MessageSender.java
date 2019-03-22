package com.espresso.moldovanbalazs.espresso;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageSender implements Runnable{

    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private String message;

    ServerThread serverThread = ServerThread.getInstance();

    public MessageSender() {

    }


    public MessageSender(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    public MessageSender(Socket clientSocket, String message) throws IOException {
        this.clientSocket = clientSocket;
        this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void run(){
        if(serverThread.isAlive()) {
            if(serverThread.getClientSocket() != null && serverThread.getClientSocket().isConnected()) {
                if(this.dataOutputStream != null) {
                    try {
                        dataOutputStream.writeUTF(this.message + '\n');
                        dataOutputStream.flush();
                        Log.d("MESSAGESENDER", "message sent!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else{
                    Log.d("MESSAGESENDER", "message not sent!");
                }
            }
        }

    }


}
