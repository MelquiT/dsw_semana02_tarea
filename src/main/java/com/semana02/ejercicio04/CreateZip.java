package com.semana02.ejercicio04;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZip {

    public static void main(String[] args) {
        
        String[] files = {"D:/Cibertec/Ciclo_6/autos_tapia_G2.json", "D:/Cibertec/Ciclo_6/clientes_tapia_G2.xml"};

        try {
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("D:/Cibertec/Ciclo_6/comprimido_tapia_G2.zip"));

            for(String ruta : files) {
                System.out.println("Agregando al archivo comprimido: " + ruta);

                //crear un archivo
                File file = new File(ruta);
                FileInputStream fis = new FileInputStream(file);

                //Crear un archivo el el zip
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);

                //Escribir el contenido del archivo
                int byteLeidos = 0;
                while((byteLeidos = fis.read()) != -1) {
                    zipOut.write(byteLeidos);
                }
                fis.close();
                zipOut.closeEntry();
            }
            zipOut.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }    
    }
}
