package com.semana02.ejercicio01;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CreateJson {

    public static void main(String[] args) {
        
        Auto auto1 = new Auto(1, "Toyota");
        Auto auto2 = new Auto(2, "Nissan");
        Auto auto3 = new Auto(3, "Mazda");

        ArrayList<Auto> autos = new ArrayList<>();
        autos.add(auto1);
        autos.add(auto2);
        autos.add(auto3);

        System.out.println("Autos: " + autos);

        FileWriter fileWriter = null;
        try {
            //Leer un archivo
            File file = new File("D:/Cibertec/Ciclo_6/autos_tapia_G2.json");
            fileWriter = new FileWriter(file);

            // Convertir el objeto a JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(autos, fileWriter);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
