package ejercicio2;

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

/**
 * ===================================================
 * EJERCICIO 2: Análisis de Empleados con DOM
 * Asignatura: Acceso a Datos
 * Alumno: Guillermo 
 * Fecha: Octubre de 2026
 * 
 * Descripción: Programa que lee un archivo XML de empleados, 
 * calcula salarios medios, filtra los sueldos altos y 
 * muestra la información formateada por consola.
 * ===================================================
 */

public class ejercicio02 {
    // Códigos ANSI para los colores
    public static final String RESET = "\u001B[0m";//Color por defecto
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        
        
        try {

            /**
             * Prepara la fabrica para el analizador xml
             * Ignora los comentarios
             * Ignora los espacios en blanco
             * Crea el analizador para leer el documento xmL
             * Lee el documento xml y lo guardamos en memoria
             * Obtener todos los empleados
             */
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
            factoria.setIgnoringComments(true);
            factoria.setIgnoringElementContentWhitespace(true);
            DocumentBuilder analizador = factoria.newDocumentBuilder();
            Document documento = analizador.parse("ejercicio2/empleados.xml");
            NodeList empleados = documento.getElementsByTagName("empleado");
            
            // 1. Contar el numero de empleados
            int numeroEmpleados = empleados.getLength();

            // 2. Calcular el salario promedio 
            NodeList salariosEmpleados = documento.getElementsByTagName("salario");
            double sumaSalarios = 0.00;
            double mediaSalarios = 0.00;
            
            // Recorre el NodeList y extrae los salarios parseando a Double
            for(int i = 0; i < salariosEmpleados.getLength(); i++){
                String textoSalario = salariosEmpleados.item(i).getTextContent().trim();
                sumaSalarios += Double.parseDouble(textoSalario);
            }

            // Calcula la media salarial
            if(numeroEmpleados > 0 ){
                mediaSalarios = sumaSalarios / numeroEmpleados;
            }

            // 3. Filtra empleados con salarios superiores a un limite (3500.00€)
            List<String> empleadosConSalarioAlto = new ArrayList<>();
            double limiteSalario = 3500.00;
            NodeList nombresEmpleados = documento.getElementsByTagName("nombre");
            for(int i = 0; i < nombresEmpleados.getLength(); i++){
                String textoSalario = salariosEmpleados.item(i).getTextContent().trim();
                double salario = Double.parseDouble(textoSalario);
                if(salario > limiteSalario){
                empleadosConSalarioAlto.add(nombresEmpleados.item(i).getTextContent().trim() + " (" + String.format("%.2f", salario) + ") EUR");
                }
            }

            // Mostrar resultados
            System.out.println("=== REPORTE DE EMPLEADOS ===");
            System.out.println();
            System.out.println("Número de empleados: "+ AZUL + numeroEmpleados + RESET);
            System.out.println();
            System.out.println("Salario promedio: " + VERDE + String.format("%.2f", mediaSalarios) + RESET + " EUR");
            System.out.println();
            System.out.println("Empleados con salario superior a " + AMARILLO + String.format("%.2f", limiteSalario) + " EUR:" + RESET);  
            
            // Recorre la lista de empleados con salario alto y los imprime
            for(String empleadoRico : empleadosConSalarioAlto){
                System.out.println("- " + empleadoRico);
            }
            System.out.println();
            System.out.println("Lista completa de empleados:");
            System.out.println("----------------------------");
            for(int i = 0; i < empleados.getLength(); i++){

                // Recorremos cada nodo <empleado> que encontramos en el XML
                Element empleado = (Element) empleados.item(i);

                /**
                * Extraemos todos los datos del empleado de golpe: 
                * buscamos cada etiqueta hija,
                * pillamos su valor de texto 
                * y limpiamos espacios.
                */
                String id = empleado.getElementsByTagName("id").item(0).getTextContent().trim();
                String nombre = empleado.getElementsByTagName("nombre").item(0).getTextContent().trim();
                String departamento = empleado.getElementsByTagName("departamento").item(0).getTextContent().trim();
                String salario = empleado.getElementsByTagName("salario").item(0).getTextContent().trim();

                // Construye la información del empleado en un StringBuilder para mejor legibilidad
                StringBuilder empleadoInfo = new StringBuilder();
                empleadoInfo.append("ID: ").append(id).append("\n");
                empleadoInfo.append("Nombre: ").append(nombre).append("\n");
                empleadoInfo.append("Departamento: ").append(departamento).append("\n");
                empleadoInfo.append("Salario: ").append(String.format("%.2f", Double.parseDouble(salario))).append(" EUR");
                System.out.println("\n" + empleadoInfo.toString());
            }

        //Bloque catch para manejar excepciones específicas relacionadas con el análisis del XML
        } catch (ParserConfigurationException e) {
            System.out.println("Error de configuración del parser: " + e.getMessage());
        }catch (SAXException e) {
            System.out.println("Error de análisis del XML: " + e.getMessage());
        }catch (java.io.IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}