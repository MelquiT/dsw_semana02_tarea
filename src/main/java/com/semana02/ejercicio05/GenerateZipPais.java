package com.semana02.ejercicio05;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GenerateZipPais {

    public static void main(String[] args) {
        
        GenerateZipPais generateZipPais = new GenerateZipPais();
        generateZipPais.procesar();
    }

    public void procesar() {
        ArrayList<Pais> paises = new ArrayList<>();
        //1 Generar el arrayList de paises de la bd
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySqlDBConexion.getConexion();
            String query = "SELECT * FROM pais";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idPais = resultSet.getInt("idPais");
                String iso = resultSet.getString("iso");
                String nombre = resultSet.getString("nombre");
                Pais pais = new Pais(idPais, iso, nombre);
                paises.add(pais);
            }    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(paises);

        //2 Generar el archivo json de los paises
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("D:/Cibertec/Ciclo_6/cliente/paises_tapia_G2.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(paises, fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //3 Generar el archivo XML de los paises
        FileWriter fileWriterXML = null;
        try {
            fileWriterXML = new FileWriter("D:/Cibertec/Ciclo_6/cliente/paises_tapia_G2.xml");
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.writeValue(fileWriterXML, paises);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriterXML.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //4 Generar el archivo zip con los archivos json y xml
        String[] files = {"D:/Cibertec/Ciclo_6/cliente/paises_tapia_G2.json", "D:/Cibertec/Ciclo_6/cliente/paises_tapia_G2.xml"};
        try {
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("D:/Cibertec/Ciclo_6/cliente/comprimido_tapia_G2.zip"));

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
