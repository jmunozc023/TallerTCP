/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tallertcpserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josep
 */
class Client implements Runnable {

    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String flag;
    private String[][] parejas;
    private List<Client> jugadorList;
    private int puntaje1, puntaje2;

    public Client(final Socket socket, final String flag, final String[][] parejas, List<Client> jugadorList) {
        this.socket = socket;
        this.flag = flag;
        this.parejas = parejas;
        this.jugadorList = jugadorList;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            escribir("flag," + flag);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leer() {
        try {
            var data = in.readUTF();
            System.out.println(data);
            readString(data);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readString(String data) {
        var splitString = data.split(",");
        switch (splitString[0]) {
            case "jugar":
                var x = Integer.parseInt(splitString[1]);
                var y = Integer.parseInt(splitString[2]);
                var x1 = Integer.parseInt(splitString[3]);
                var y1 = Integer.parseInt(splitString[4]);
                try {
                    if (parejas[x][y].equals(parejas[x1][y1])) {
                        parejas[x][y] = null;
                        parejas[x1][y1] = null;
                        System.out.println(flag + " Ha encontrado una pareja.");
                        if (flag.equals("jugador1")) {
                            puntaje1++;
                        } else {
                            puntaje2++;
                        }
                    } else {
                        System.out.println("No son pareja");

                    }
                } catch (Exception nullException) { //Se adiciona un try catch en caso de que el cliente seleccione celdas vacias de la matriz
                    System.out.println("Pareja no disponible, intente de nuevo");
                }
                if (gano()) { //if dedidado a verificar si tenemos o no ganador
                    jugadorList.get(0).escribir("Ganaste! "+flag);
                    jugadorList.get(1).escribir("Ganaste! "+flag);
                } else {
                    if (flag.equalsIgnoreCase("jugador1")) {
                        jugadorList.get(1).escribir("jugar");
                    } else {
                        jugadorList.get(0).escribir("jugar");
                    }
                }
                imprimir();
                break;
            default:
                throw new AssertionError();
        }
    }
    private boolean gano(){
        if (parejas == null) {
            
        } else {
        }
        /*if (puntaje1 == 15) {
        return true;
        } else {
        if (puntaje2 == 15) {
        return true;
        }
        }
        return false;*/
        return false;
    }

    public void escribir(String data) {
        try {
            out.writeUTF(data);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void imprimir(){

        for (var i : parejas) {
            for (var j : i) {
                System.out.print(j+"|");
                
            }
            System.out.println("");
            
        }
    }

    @Override
    public void run() {
        while (true) {            
            leer();
        }
    }

}
