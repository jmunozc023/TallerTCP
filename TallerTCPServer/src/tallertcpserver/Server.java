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
    private ServerSocket socket;
    private List<Client> jugadorList;
    private ExecutorService service;
    private boolean first=true;
    private String[][] parejas= new String [4][4];
    
    public Server(){
        for (var i = 0; i < parejas.length; i++) {
            var random= String.valueOf(Math.floor(Math.random() * 1000 + 1));
                    parejas[i][0]= random;
            
            for (var j = 0; j < parejas.length; j++) {
                parejas[i][j]= random;
            } 
        }
        jugadorList= new ArrayList<>();
        service= Executors.newCachedThreadPool();
        try {
            socket= new ServerSocket(7800);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void aceptarConexion(){
        try {
            var client=socket.accept();
            var flag= "jugador2";
            if (first) {
                flag="jugador1";
                first=false;
            }
            var jugadorThread= new Client(client, flag, parejas, jugadorList);
            jugadorList.add(jugadorThread);
            service.submit(jugadorThread);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
