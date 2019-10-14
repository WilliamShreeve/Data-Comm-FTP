import java.io.*;
import java.net.*;
import java.util.*;

public class FTPServer {

    public static void main(String argv[]) throws IOException {

        Thread thread;
        WorkerThread workerThread;

        ServerSocket welcomeSocket = new ServerSocket(12000);

        while (true) {
            workerThread = new WorkerThread(welcomeSocket.accept());
            thread = new Thread(workerThread);
            thread.start();
        }
    }
}
