/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tallertcpclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author josep
 */
public class Client extends Thread { //Clase de cliente que extiende los Hilos

    private Socket socket; //Variable de socket de conexion de Cliente
    private ObjectOutputStream out; //Variable para enviar objetos de conexion
    private ObjectInputStream in; //Variable para recibir Objetos de conexion
    private String flag; //Bandera para determinar el nombre del jugardor
    private boolean jugar = false; //Variable Boolean para determinar el turno 

    public Client() { //Genera la conexion entre el Servidor y el cliente
        try {
            socket = new Socket(InetAddress.getLocalHost(), 7800);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leer() { //Metodo para leer los datos a enviar
        try {
            var data = in.readUTF();
            System.out.println(data);
            readString(data);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void escribir() { //Metodo para enviar los datos al servidor, ademas de usar una ventana de dialogo para recibir los datos del usuario
        var data = JOptionPane.showInputDialog("Escoja una pareja usando las cordenadas (x1,y1,x2,y2)" + flag);
        var text = "jugar," + data;
        try {
            out.writeUTF(text);
            out.flush();
            jugar = false;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readString(final String data) { //Metodo para determinar quien juega y si fue ganador
        var splitString = data.split(",");
        if (splitString[0].equals("flag")) {
            flag = splitString[1];
            if (flag.equals("jugador1")) {
                jugar = true;
            }
        } else if (splitString[0].contains("jugar")) {
            jugar = true;
        } else if (splitString[0].contains("Ganaste! ")) {
            System.out.println(splitString[0] + " : " + splitString[1]);
            System.exit(0);

        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                if (jugar) {
                    escribir();
                }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
