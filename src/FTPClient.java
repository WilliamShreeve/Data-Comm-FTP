import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import javax.swing.*;

class FTPClient {

    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        boolean isOpen = true;
        int number = 1;
        boolean notEnd = true;
        String statusCode;
        boolean clientgo = true;


        BufferedReader inFromUser = new BufferedReader(
                new InputStreamReader(System.in));
        sentence = inFromUser.readLine();
        StringTokenizer tokens = new StringTokenizer(sentence);

        System.out.println("sentence: " + sentence);
        if (sentence.startsWith("connect")) {
            String serverName = tokens.nextToken(); // pass the connect command
            serverName = tokens.nextToken();
            int port = Integer.parseInt(tokens.nextToken());
            System.out.println("You are connected to " + serverName);

            Socket controlSocket = new Socket(serverName, port);
            System.out.println("Commands: ");
            System.out.println("list: lists files on server");
            System.out.println("retr: <filename> Downloads the specified file to your current directory");
            System.out.println("stor: <filename> Uploads the specified file to the server");
            System.out.println("quit terminates the connection with the server");
            while (isOpen && clientgo) {
                System.out.println("debug inside while");
                DataOutputStream outToServer = new DataOutputStream(
                        controlSocket.getOutputStream());

                DataInputStream inFromServer = new DataInputStream(
                        new BufferedInputStream(controlSocket.getInputStream()));

                sentence = inFromUser.readLine();

                if (sentence.equals("list:")) {

                    port = port + 2;
                    outToServer.writeBytes(port + " " + sentence + " " + '\n');

                    ServerSocket welcomeData = new ServerSocket(port);
                    Socket dataSocket = welcomeData.accept();
                    String[] list;

                    DataInputStream inData = new DataInputStream(
                            new BufferedInputStream(dataSocket.getInputStream()));
                    while (notEnd) {
                        modifiedSentence = inData.readUTF();
                        File files = new File(modifiedSentence);
                        list = files.list();
                        System.out.println("List of files on server: ");
                        for (String s: list){
                            System.out.println(s);
                        }
                    }


                    welcomeData.close();
                    dataSocket.close();
                    System.out.println("\nWhat would you like to do next: \n retr: file.txt ||stor: file.txt  || close");

                } else if (sentence.startsWith("retr: ")) {

                }
                else if (sentence.startsWith("stor: ")) {

                }
                else if (sentence.equals("quit")) {
                    System.out.println("Terminating connection...");
                    controlSocket.close();
                    isOpen = false;
                }
            }
        }
    }
}//....................................................