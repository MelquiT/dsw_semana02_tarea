package com.semana02.ejercicio05;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class ClienteZipPais {

    public static void main(String[] args) {
        new ClienteZipPais();
    }

    private final int PORT = 13;
    private final String HOST = "localhost";

    public ClienteZipPais() {
        //1 Generar todos los archivos
        System.out.println("Generando los archivos JSON, XML y ZIP");
        GenerateZipPais generateZipPais = new GenerateZipPais();
        generateZipPais.procesar();

        //2 Crear el Socket Cliente
        try {
            System.out.println("2 Client started");
            Socket socket = new Socket(HOST, PORT);
            System.out.println("3 Connected to server");

            //3 Enviar el archivo ZIP
            //Flujos para enviar y recibir archivos
            File file = new File("D:/Cibertec/Ciclo_6/cliente/comprimido_tapia_G2.zip");
            FileInputStream fis = new FileInputStream(file);
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            
            //enviar archivo
            int byteLeidos = 0;
            while ((byteLeidos = fis.read()) != -1) {
                salida.write(byteLeidos);
            }
            System.out.println("3 File sent");
            fis.close();
            salida.close();
            System.out.println("4 Client finished");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }  
        
    }

}
