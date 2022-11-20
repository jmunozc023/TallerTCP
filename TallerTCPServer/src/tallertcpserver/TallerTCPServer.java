/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tallertcpserver;

/**
 *
 * @author josep
 */
public class TallerTCPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        var server= new Server(); // Constructor de la clase Server
        while (true) // Ciclo While que busca aceptar conexiones al servidor
            server.aceptarConexion();
        
    }
    
}
