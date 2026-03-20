package org.example;

import java.util.Scanner;
import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class OperacionesEmpleado {
    public static void main(String[] args) {
        boolean seguir = true;
        Scanner sc = new Scanner(System.in);
        try(Connection conn = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword());
            Statement stmt = conn.createStatement()) {
            String sqlDelete ="DROP TABLE EMPLEADO CASCADE CONSTRAINTS";
            String sql = "CREATE TABLE empleado (" + "id NUMBER PRIMARY KEY, " + "nombre VARCHAR(100), " + "salario NUMBER (10, 2))";

            String insertar = "INSERT INTO empleado VALUES (1, 'Jose', 2000)";
            String insertar1 = "INSERT INTO empleado VALUES (2, 'Juanma', 1000)";
            String insertar2 = "INSERT INTO empleado VALUES (3, 'Pepe', 1342)";
            String insertar3 = "INSERT INTO empleado VALUES (4, 'Rosa', 234.32)";
            String insertar4 = "INSERT INTO empleado VALUES (5, 'Martina', 5000)";
            stmt.executeUpdate(sqlDelete);
            stmt.executeUpdate(sql);
            System.out.println("Tabla empleado creada");
            int identificador = 6;

            stmt.executeUpdate(insertar);
            stmt.executeUpdate(insertar1);
            stmt.executeUpdate(insertar2);
            stmt.executeUpdate(insertar3);
            stmt.executeUpdate(insertar4);
            System.out.println("Valores insertados");

            mostrar(stmt);
            while (seguir){
                System.out.println("Escoja la opcion (0=salir, 1=Insertar, 2=mostrar, 3=actualizar, 4=eliminar):");
                int opcion = sc.nextInt();
                switch (opcion){
                    case 0:
                        seguir = false;
                        System.out.println("Programa cerrado");
                        break;
                    case 1:
                        insertar(conn, identificador);
                        identificador++;
                        break;
                    case 2:
                        mostrar(stmt);
                        break;
                    case 3:
                        actualizar(conn);
                        break;
                    case 4:
                        eliminar(conn);
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void insertar(Connection conn, int id) throws SQLException {
        String insertar = "INSERT INTO empleado VALUES (?, ?, ?)";
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el nombre del empleado:");
        String nombre = sc.nextLine();
        System.out.println("Ingrese el salario del empleado:");
        int salario = sc.nextInt();

        PreparedStatement ps = conn.prepareStatement(insertar);
        ps.setDouble(1, id);
        ps.setString(2, nombre);
        ps.setInt(3, salario);
        ps.executeUpdate();
        System.out.println("Valor insertado");
    }

    public static void mostrar(Statement stmt) throws SQLException {
        String sentencia = "SELECT * FROM EMPLEADO";
        ResultSet rs = stmt.executeQuery(sentencia);

        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            double salario = rs.getDouble("salario");
            System.out.println(id + " " + nombre + " " + salario);
        }
        System.out.println();
    }

    public static void actualizar(Connection conn) throws SQLException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el identificador del empleado:");
        int id = sc.nextInt();

        System.out.println("Ingrese el salario nuevo del empleado:");
        int salario = sc.nextInt();

        String update = "UPDATE empleado SET salario = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(update);
        ps.setDouble(1, salario);
        ps.setInt(2, id);
        ps.executeUpdate();
        System.out.println("Valor modificado");
        System.out.println();
    }

    public static void eliminar(Connection conn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el identificador del empleado:");
        int id = sc.nextInt();

        String eliminarEmpleado = "DELETE FROM empleado WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(eliminarEmpleado);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Empleado eliminado");
    }
}