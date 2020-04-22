package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class daoUsuarios extends AbstractDAO {

    public daoUsuarios(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public Boolean insertarUsuario(Usuario u) {//true si se insertó y false si no
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto;

        con = super.getConexion();

        try {
            stmUsuario = con.prepareStatement("insert into usuario(dni,id,correoElectronico,contrasenha, nombre,"
                    + " primerApellido, segundoApellido, paisProcedencia, telefono, sexo) "
                    + "values (?,?,?,?,?,?,?,?,?,?)");
            stmUsuario.setString(1, u.getDni());
            stmUsuario.setString(2, u.getId());
            stmUsuario.setString(3, u.getEmail());
            stmUsuario.setString(4, u.getContrasenha());
            stmUsuario.setString(5, u.getNombre());
            stmUsuario.setString(6, u.getAp1());
            stmUsuario.setString(7, u.getAp2());
            stmUsuario.setString(8, u.getPaisProcedencia());
            stmUsuario.setInt(9, u.getTelefono());
            stmUsuario.setString(10, u.cambiarFormatoSexo(u.getSexo()));
            stmUsuario.executeUpdate();
            correcto = true;

        } catch (SQLException e) {
            getFachadaAplicacion().mostrarError(e.getMessage());
            correcto = false;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
                correcto = false;
            }
        }
        return correcto;
    }

    public Usuario comprobarCredenciales(String idUsuario, String clave) {
        Usuario resultado = null;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;

        con = this.getConexion();

        try {
            stmUsuario = con.prepareStatement("select dni,id,correoElectronico,contrasenha, nombre,"
                    + " primerApellido, segundoApellido, paisProcedencia, telefono, sexo "
                    + "from usuario "
                    + "where id = ? and contrasenha = ?");
            stmUsuario.setString(1, idUsuario);
            stmUsuario.setString(2, clave);
            rsUsuario = stmUsuario.executeQuery();
            if (rsUsuario.next()) {
                resultado = new Usuario(rsUsuario.getString("dni"), rsUsuario.getString("id"),
                        rsUsuario.getString("correoElectronico"), rsUsuario.getString("contrasenha"),
                        rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                        rsUsuario.getString("segundoApellido"), rsUsuario.getString("paisProcedencia"),
                        rsUsuario.getInt("telefono"), rsUsuario.getString("sexo"));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public Boolean modificarUsuario(Usuario us) {
        Connection con;
        PreparedStatement stmUsuario = null;
        PreparedStatement stmBorrado = null;
        Boolean correcto;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("update usuario "
                    + "set id=?,correoElectronico=?,contrasenha=?, nombre=?,"
                    + "primerApellido=?, segundoApellido=?, paisProcedencia=?,"
                    + "telefono=?,sexo=? "
                    + "where dni=?");

            stmUsuario.setString(1, us.getId());
            stmUsuario.setString(2, us.getEmail());
            stmUsuario.setString(3, us.getContrasenha());
            stmUsuario.setString(4, us.getNombre());
            stmUsuario.setString(5, us.getAp1());
            stmUsuario.setString(6, us.getAp2());
            stmUsuario.setString(7, us.getPaisProcedencia());
            stmUsuario.setInt(8, us.getTelefono());
            stmUsuario.setString(9, us.getSexo());
            stmUsuario.setString(10, us.getDni());

            stmUsuario.executeUpdate();
            correcto = true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
            correcto = false;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }

    public Boolean eliminarUsuario(String dni) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto;

        con = super.getConexion();

        try {
            stmUsuario = con.prepareStatement("delete from usuario where dni = ?");
            stmUsuario.setString(1, dni);
            stmUsuario.executeUpdate();
            correcto = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
            correcto = false;
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return correcto;
    }
}
