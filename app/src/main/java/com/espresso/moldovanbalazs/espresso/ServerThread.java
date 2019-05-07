package com.espresso.moldovanbalazs.espresso;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class ServerThread extends Thread {

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private static final int PORT = 8000;;
    ClientInterface clientPresent;
    private CommunicationThread communicationThread;

    private static ServerThread serverThread = null;


    private ServerThread() {
        super();
    }

    public static ServerThread getInstance(){
        if(serverThread == null) {
            serverThread = new ServerThread();
        }
        return serverThread;
    }

    public Socket getClientSocket(){
        return this.clientSocket;
    }

    public void setClientCallBack( ClientInterface clientCallBack) {
        this.clientPresent = clientCallBack;
    }

    public String getServerData() {
        return this.getIpAddress() + ":" + PORT;
    }

    public String getClient() {
        if(this.clientSocket == null) {
            return "RC car offline";
        } else {
            return "RC car online";
        }
    }

    public void closeConnection() {
        if (serverSocket != null) {
            try {
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String message;
        while(true) {
            try {
                if(clientSocket == null) {
                    Log.d("ServerThread", "No client on channel");
                    message = "No client";
                    //uiRunnable.setMessage(message);
                    //localHandler.post(new UIRunnable());
                    clientPresent.clientPresent(false);

                    communicationThread.join();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                clientSocket = serverSocket.accept();
                communicationThread = new CommunicationThread(serverSocket, clientSocket);


                if(clientSocket.isConnected()) {
                    clientPresent.clientPresent(true);
                    Log.d("ServerThread", "client is present");
                    //new CommunicationThread(serverSocket,clientSocket).run();
                    communicationThread.run();
                }

                /*if(communicationThread.isInterrupted()) {
                    clientPresent.clientPresent(false);
                }*/
                /*TODO close connection
                if received message from client CLOSE -> close connection from server :)
                 */

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}
