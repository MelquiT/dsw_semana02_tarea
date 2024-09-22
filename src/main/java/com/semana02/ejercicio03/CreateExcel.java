package com.semana02.ejercicio03;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateExcel {

    public static void main(String[] args) {
        Producto producto1 = new Producto(1, "Laptop", 2500.0, 10);
        Producto producto2 = new Producto(2, "Mouse", 50.0, 100);
        Producto producto3 = new Producto(3, "Teclado", 100.0, 50);

        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);

        System.out.println("Productos: " + productos);

        // Create un archivo excel
        String[] HEADERS = {"ID Producto", "Nombre", "Precio", "Stock"};
        String SHEET_NAME = "Productos";
        int[] COLUMN_WIDTHS = {3000, 7000, 3000, 3000};

        try {
            FileOutputStream fileOut = new FileOutputStream("D:/Cibertec/Ciclo_6/productos_tapia_G2.xlsx");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(SHEET_NAME);

             // Establecer el ancho de las columnas
             for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
                sheet.setColumnWidth(i, COLUMN_WIDTHS[i]);
            }

            // Crear la fila de encabezado
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }

            // Crear las filas de los productos
            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                sheet.createRow(i + 1).createCell(0).setCellValue(producto.getIdProducto());
                sheet.getRow(i + 1).createCell(1).setCellValue(producto.getNombre());
                sheet.getRow(i + 1).createCell(2).setCellValue(producto.getPrecio());
                sheet.getRow(i + 1).createCell(3).setCellValue(producto.getStock());
            }
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
