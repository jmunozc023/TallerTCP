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
        var server= new Server();
        while (true)            
            server.aceptarConexion();
        
    }
    
}
