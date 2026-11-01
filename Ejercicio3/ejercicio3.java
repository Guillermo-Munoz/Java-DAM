package Ejercicio3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ejercicio3 {

    /**
     * ========================================
     * Ejercicio 3: Control de inventario de una tienda
     * Asignatura: Acceso a Datos
     * Alumno: Guillermo
     * Fecha: agosto 2026
     * 
     * Descripcion: Programa que lee un archivo XML de inventario, 
     * calcula el valor total del inventario,
     * detecta productos que necesuitan reposicion,
     * identifica productos agotados
     * encuentra el producto mas caro 
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        
        try {
            /**
             * Preparar la fabrica el analizador XML
             * Ignorar los comentarios
             * Ingnorar los espacion en blancos
             * Crear el analizador para leer el documento XML
             * Lee el documento XML y lo guarda en memoria
             */
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            fabrica.setIgnoringComments(true);
            fabrica.setIgnoringElementContentWhitespace(true);
            DocumentBuilder analizador = fabrica.newDocumentBuilder();
            Document documento = analizador.parse("ejercicio3/inventario.xml");

            // Obtiene toso los nodos del domumento XML
            NodeList productos = documento.getElementsByTagName("producto");
            
            // Variables para calculos
            double valorInventario = 0.0;
            List<String> productosReponer = new ArrayList<>();
            List<String> productosAgotados = new ArrayList<>();
            String productoMasCaro = "";
            double precioProductoMasCaro = 0;
            String reglaFormato = "| %-2s | %-20.20s | %-18.18s | %5d | %11d | %8.2f |%n";
            String reglaFormatoCabecera = "| %-3s | %-20.20s | %-18.18s | %5.5s | %11.11s | %8.8s |%n";
            String reglaFormatoSeparador = "+ %-3.3s + %-20.20s + %-18.18s + %5.5s + %11.11s + %8.8s +%n";
            String cabeceraColumnas = String.format(reglaFormatoCabecera, "id",  "nombre", "categoria", "stock", "stockMinimo", "precio/Uni");
            int anchoLinea = 85;
            
            // Recorre la lista de productos una sola vez
            for(int i = 0 ; i < productos.getLength(); i++){

                // Obtene nodo por su posicion
                Element producto = (Element) productos.item(i);

                int id = Integer.parseInt(producto.getElementsByTagName("id").item(0).getTextContent().trim());
                String nombre = producto.getElementsByTagName("nombre").item(0).getTextContent().trim();
                String categoria = producto.getElementsByTagName("categoria").item(0).getTextContent().trim();
                double precio = Double.parseDouble(producto.getElementsByTagName("precio").item(0).getTextContent().trim());
                int stock = Integer.parseInt(producto.getElementsByTagName("stock").item(0).getTextContent().trim());
                int stockMinimo = Integer.parseInt(producto.getElementsByTagName("stockMinimo").item(0).getTextContent().trim());

                // Calcula valor del inventario
                valorInventario += precio * stock;

                // Calcula stock minimo
                if(stock < stockMinimo){
                    /**
                     * GUÍA DE FORMATO CON printf (Sintaxis general: % [alineación] [ancho] [.precisión] [tipo])
                     * 
                     * 1. PARA TEXTO (Strings) -> %-X.Ys
                     *    %  : Inicia la regla de formato para procesar la variable.
                     *    -  : Alinea el texto a la izquierda. Si se omite, se alinea a la derecha.
                     *    X  : El ancho total y fijo de la columna (espacio reservado en pantalla).
                     *    .Y : Límite máximo. Si el texto supera este número de caracteres, se trunca (se corta).
                     *    s  : Indica que el dato es un String (texto).
                     * 
                     * 2. PARA NÚMEROS ENTEROS -> %Xd
                     *    %  : Inicia la regla de formato.
                     *    X  : El ancho fijo de la columna (habitualmente alineado a la derecha para números).
                     *    d  : Indica que el dato es un número entero (d viene de "decimal integer").
                     * 
                     * 3. PARA NÚMEROS DECIMALES -> %X.Yf
                     *    %  : Inicia la regla de formato.
                     *    X  : El ancho total de la columna (incluyendo el punto y los decimales).
                     *    .Y : El número exacto de decimales que se mostrarán (aplica redondeo automático).
                     *    f  : Indica que el dato es un número de punto flotante/decimal (float o double).
                     */
                    // Guarda la estructura/regla en una variable
                    
                    productosReponer.add(String.format(reglaFormato, id,  nombre, categoria, stock, stockMinimo, precio));
                }
                // Guarda los productos agotados
                if(stock == 0){
                    
                    productosAgotados.add(String.format(reglaFormato, id,  nombre, categoria, stock, stockMinimo, precio));
                }
                // Guarda el producto mas caro que valla econtrando
                if(precio > precioProductoMasCaro){
                    
                    productoMasCaro = String.format(reglaFormato, id,  nombre, categoria, stock, stockMinimo, precio);
                }
            }

            // MOSTRAR RESULTADOS EN PANTALLA
            String titulo1 = "SYSTEMA DE INVENTARIO: INFORME ";
            String titulo2 = "PRODUCTOS PARA REPONER ";
            String titulo3 = "PRODUCTOS AGOTADOS ";
            String titulo4 = "PRODUCTO MAS CARO";
            System.out.printf("%s%n", "-".repeat(anchoLinea));

            // SYSTEMA DE INVENTARIO INFORME
            int gionesLado = (anchoLinea - titulo1.length() - 2) / 2;
            System.out.printf("%s %s %s%n", "-".repeat(gionesLado), titulo1, "-".repeat(gionesLado));
            System.out.printf("%n");
            System.out.printf("Valor total del inventario: " + valorInventario + "%n%n");

            // PRODUCTOS PARA REPONER
            gionesLado = (anchoLinea - titulo2.length() - 2) / 2;
            System.out.printf("%s %s %s%n", "-".repeat(gionesLado), titulo2, "-".repeat(gionesLado));
            System.out.printf(cabeceraColumnas);
            System.out.printf(reglaFormatoSeparador, "-".repeat(3), "-".repeat(20), "-".repeat(18), "-".repeat(5), "-".repeat(11), "-".repeat(8));
            for (String producto : productosReponer) {
                System.out.printf(producto);  
            }
            System.out.println("");

            // PRODUCTOS AGOTADOS
            gionesLado = (anchoLinea - titulo3.length() - 2) / 2;
            System.out.printf("%s %s %s%n", "-".repeat(gionesLado), titulo3, "-".repeat(gionesLado));
            System.out.printf(cabeceraColumnas);
            System.out.printf(reglaFormatoSeparador, "-".repeat(3), "-".repeat(20), "-".repeat(18), "-".repeat(5), "-".repeat(11), "-".repeat(8));
            for (String producto : productosAgotados) {
            System.out.printf(producto);
            }
            System.out.println("");

            // PRODUCTO MAS CARO
            gionesLado = (anchoLinea - titulo4.length() - 2) / 2;
            System.out.printf("%s %s %s%n", "-".repeat(gionesLado), titulo4, "-".repeat(gionesLado));
            System.out.printf(cabeceraColumnas);
            System.out.printf(reglaFormatoSeparador, "-".repeat(3), "-".repeat(20), "-".repeat(18), "-".repeat(5), "-".repeat(11), "-".repeat(8));
            System.out.printf(productoMasCaro);
            System.out.printf("%s%n", "-".repeat(anchoLinea));

        } catch (ParserConfigurationException e) {
            System.out.println("Error de configuracion: " + e.getMessage());
        } catch (SAXException e){
            System.out.println("Error de analisis XML: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de entrada/dalida: " + e.getMessage());
        }



    }
}
