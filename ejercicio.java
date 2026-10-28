package Ejercicio9;
import java.xml.parsers.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.nio.file.Paths;

public class ejercicio {
    public static void main(String[] args) {

        try {
            //Crea la fabrica para preparar el analizador xml
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //Creamos el analizador que nos permitirá leer el documento xml
            DocumentrBuilder analizador = factory.newDocumentBuilder();

            //Leemos el documento xml y lo guardamos en memoria
            Document documento = analizador.parse("libros.xml");

            //Obtenemos el elemento raiz Biblioteca
            Element raiz = documento.getDocumentElement();

            System.out.println("Elemento raiz: " + raiz.getTagName());

            //Obtenemos los nodos libros
            NodeList listaLibros = raiz.getElementsByTagName("libro");

            System.out.println("Número de libros: " + listaLibros.getLength());

            //Recorremos la lista de libros
            for (int i = 0; i < listaLibros.getLength(); i++){

                Node nodo = listaLibros.item(i);
                if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                    //convertir el nodo en elemento para poder acceder a sus atributos y elementos hijos
                    Element libro = (Element) nodo;

                    //Obtener atributo
                    String id = libro.getAttribute("id");

                    String titulo = libro.getElementsByTagName("titulo").item(0).getTextContent();
                    String autor = libro.getElementsByTagName("autor").item(0).getTextContent();
                    String precio = libro.getElementsByTagName("precio").item(0).getTextContent();


                    System.out.println("_____________________________");
                    System.out.println("ID: " + id);
                    System.err.println("Autot: " + autor);
                    System.out.println("Precioi: " + precio);
                }
            }

        } catch (ParserConfigurationException e) {
            System.out.println("Error de configuración del parser: " + e.getMessage());
        }catch (SAXException e) {
            System.out.println("Error de análisis del XML: " + e.getMessage());
        }catch (java.io.IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}