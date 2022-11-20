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
class Client implements Runnable { //Clase para crear las funciones de los hilos Cliente

    private final Socket socket; //Variable del socket para los clientes
    private ObjectInputStream in; //Variables para recibir objetos de conexion
    private ObjectOutputStream out; //Variable para enviar objetos de conexion
    private String flag; //Bandera para determinar el jugador
    private String[][] parejas; //Variable para el campo de juego
    private List<Client> jugadorList; //Variable para inicializar la lista de jugadores
    private int puntaje1, puntaje2; //Variables para almacenar los puntajes

    public Client(final Socket socket, final String flag, final String[][] parejas, List<Client> jugadorList) { //Constructor de la clase
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

    public void leer() { //Metodo utilizado para leer la informacion recibida del cliente
        try {
            var data = in.readUTF();
            System.out.println(data);
            readString(data);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readString(String data) { //Metodo que nos permite indicarle que hacer al hilo del cliente
        var splitString = data.split(",");
        switch (splitString[0]) {
            case "jugar": //Case para determinar el turno de juego, divide los datos recibidos en coordenadas (x,y,x1,y1)
                var x = Integer.parseInt(splitString[1]);
                var y = Integer.parseInt(splitString[2]);
                var x1 = Integer.parseInt(splitString[3]);
                var y1 = Integer.parseInt(splitString[4]);
                try {
                    if (parejas[x][y].equals(parejas[x1][y1])) { //If que nos permite cambiar los valores de la matriz cada vez que un jugador obtiene una pareja
                        parejas[x][y] = null;
                        parejas[x1][y1] = null;
                        System.out.println(flag + " Ha encontrado una pareja.");
                        if (flag.equals("jugador1")) { //If para subir el puntaje de cada jugador cada vez que se encuentra una pareja
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

                    if (puntaje1 > puntaje2) {
                        jugadorList.get(0).escribir("Ganaste! " + flag + " Puntaje final: " + puntaje1);
                        jugadorList.get(1).escribir("Gano " + flag);
                    } else if (puntaje2 > puntaje1) {
                        jugadorList.get(1).escribir("Ganaste! " + flag + " Puntaje final: " + puntaje2);
                        jugadorList.get(0).escribir("Gano " + flag);
                    } else {
                        jugadorList.get(0).escribir("Empate " + flag + " Puntaje final: " + puntaje1);
                        jugadorList.get(1).escribir("Empate " + flag + " Puntaje final: " + puntaje2);
                    }
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

    private boolean gano() { //Metodo para determinar cuando se acaban las parejas en el campo de juego (matriz)

        for (int i = 0; i < parejas.length; i++) {
            for (int j = 0; j < parejas[i].length; j++) {
                if (parejas[i][j] != null) {
                    return false;
                }
            }

        }
        return true;
    }

    public void escribir(String data) { //Metodo para escribir los datos recibidos del cliente
        try {
            out.writeUTF(data);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void imprimir() { //Metodo para imprimir la matriz del servidor en el cliente

        for (var i : parejas) {
            for (var j : i) {
                System.out.print(j + "|");

            }
            System.out.println("");

        }
    }

    @Override
    public void run() { //Override de la clase Run para manteer los hilos corriendo
        while (true) {
            leer();
        }
    }

}
