/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tallertcpclient;

/**
 *
 * @author josep
 */
public class TallerTCPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { //Proyecto para Clientes de el juego de memoria
        var client=new Client(); //Constructor de la clase Cliente
        client.start(); //Inicializador de los hilos
        while(true) //Ciclo while para mantener los hilos coriendo
            client.leer();
            
    }
    
}
