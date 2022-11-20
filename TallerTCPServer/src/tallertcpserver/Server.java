/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tallertcpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josep
 */
public class Server {
    private ServerSocket socket; //Variable para inicializar el Socket TCP del server
    private List<Client> jugadorList; //Variable para la lista de los jugadores
    private ExecutorService service; // Variable para inicializar y manejar los hilos
    private boolean first=true; //Variable booleana para determinar quien es el primer jugador
    private String[][] parejas= new String [10][10]; // Matriz para el juego de parejas
    
    public Server(){ //Metodo para rellenar la matriz del juego con numeros aleatorios repetidos
        for (var i = 0; i < parejas.length; i++) {
            var random= String.valueOf(Math.floor(Math.random() * 50 + 1));
                    parejas[i][0]= random;
            
            for (var j = 0; j < parejas.length; j++) {
                parejas[i][j]= random;
            } 
        }
        jugadorList= new ArrayList<>(); //Constructor para la lista de jugadores
        service= Executors.newCachedThreadPool(); //Constructor del ThreadPool
        try {
            socket= new ServerSocket(7800); //Constructor del socket
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void aceptarConexion(){ // Metodo para buscar requests de clientes y aceptar conexiones
        try {
            var client=socket.accept();
            var flag= "jugador2"; //Asigna la bandera en el jugador numero 2
            if (first) {
                flag="jugador1"; //Asigna la bandera en el jugador 1
                first=false;
            }
            var jugadorThread= new Client(client, flag, parejas, jugadorList); //Constructor de los hilos del cliente
            jugadorList.add(jugadorThread); //Adiciona el jugador a los hilos
            service.submit(jugadorThread); //Ejecuta los hilos de los clientes.
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
