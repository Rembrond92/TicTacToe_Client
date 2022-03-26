package Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
				
    private  static final int    serverPort = 6666;
    private  static final String localhost  = "127.0.0.1";

    public static void main(String[] ar) {
       
        Socket socket = null;
        try {
            try {
                System.out.println("Добро пожаловать в клиентскую версию Крестики-Нолики!\n" +
                             "Соединяемся с сервером\n\t" +
                             "(IP address " + localhost + 
                             ", port " + serverPort + ")");
                InetAddress ipAddress;
                ipAddress = InetAddress.getByName(localhost);
                socket = new Socket(ipAddress, serverPort);
                System.out.println(
                            "Соединение установлено.");

                // Получаем входной и выходной потоки 
                // сокета для обмена сообщениями с сервером
                InputStream  sin  = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();

                DataInputStream  in = new DataInputStream (sin);
                DataOutputStream out = new DataOutputStream(sout);

                // Создаем поток для чтения с клавиатуры.
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader keyboard = new BufferedReader(isr);
                
																 	 String line = null;
                
                while(true) {
                    Thread.sleep(100);
                    while(in.available() > 0) {
                        Thread.sleep(100);
                        line = in.readUTF();
                        System.out.println(line);
                        if(line.equals("Выход из игры."))
                            throw new Exception();
                    }
                    
                    Thread.sleep(100);
                    line = keyboard.readLine();
                    out.writeUTF(line);
                    out.flush();
                    Thread.sleep(100);
                }

            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Соединение разорвано!");
            }
        }
								
								 finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Критическая ошибка! Программа будет закрыта.");
            }
        }
    }
}