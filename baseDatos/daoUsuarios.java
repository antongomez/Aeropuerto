package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Administrador;
import aeropuerto.elementos.PersonalExterno;
import aeropuerto.elementos.PersonalLaboral;
import aeropuerto.elementos.Usuario;
import aeropuerto.util.EstadisticasUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class daoUsuarios extends AbstractDAO {

    public daoUsuarios(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public Boolean insertarUsuario(Usuario u, String clave) {//true si se insertó y false si no
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto;

        con = super.getConexion();

        try {
            stmUsuario = con.prepareStatement("insert into usuario(dni,id,correoElectronico,contrasenha, nombre,"
                    + " primerApellido, segundoApellido, paisProcedencia, telefono, sexo) "
                    + "values (?,?,?,crypt(?, gen_salt('md5')),?,?,?,?,?,?)");

            stmUsuario.setString(1, u.getDni());
            stmUsuario.setString(2, u.getId());
            stmUsuario.setString(3, u.getEmail());
            stmUsuario.setString(4, clave);
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
        PreparedStatement stmAdmin_PL;
        ResultSet rsUsuario;
        ResultSet rsAdmin_PL;

        con = this.getConexion();

        try {
            stmUsuario = con.prepareStatement("select dni,id,correoElectronico,contrasenha, nombre,"
                    + " primerApellido, segundoApellido, paisProcedencia, telefono, sexo "
                    + "from usuario "
                    + "where id = ? and contrasenha = crypt(?, contrasenha)");
            stmUsuario.setString(1, idUsuario);
            stmUsuario.setString(2, clave);
            rsUsuario = stmUsuario.executeQuery();

            //Se existe un usuario, comprobamos se e administrador ou persoal laboral
            if (rsUsuario.next()) {

                //Administrador
                stmAdmin_PL = con.prepareStatement("select usuario,fechainicio,curriculum "
                        + "from administrador "
                        + "where usuario = ? ");
                stmAdmin_PL.setString(1, rsUsuario.getString("dni"));
                rsAdmin_PL = stmAdmin_PL.executeQuery();

                //Comprobamos se e admin
                if (rsAdmin_PL.next()) {
                    resultado = new Administrador(rsUsuario.getString("dni"), rsUsuario.getString("id"),
                            rsUsuario.getString("correoElectronico"),
                            rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                            rsUsuario.getString("segundoApellido"), rsUsuario.getString("paisProcedencia"),
                            rsUsuario.getInt("telefono"), rsUsuario.getString("sexo"),
                            rsAdmin_PL.getTimestamp("fechainicio"),rsAdmin_PL.getString("curriculum"));
                    
                } else {
                    //Personal Laboral
                    stmAdmin_PL = con.prepareStatement("select usuario, labor, descripciontarea, fechainicio "
                            + "from personallaboral "
                            + "where usuario = ? ");
                    stmAdmin_PL.setString(1, rsUsuario.getString("dni"));
                    rsAdmin_PL = stmAdmin_PL.executeQuery();

                    //Comprobamos se e PL
                    if (rsAdmin_PL.next()) {
                        resultado = new PersonalLaboral(rsUsuario.getString("dni"), rsUsuario.getString("id"),
                                rsUsuario.getString("correoElectronico"),
                                rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                                rsUsuario.getString("segundoApellido"), rsUsuario.getString("paisProcedencia"),
                                rsUsuario.getInt("telefono"), rsUsuario.getString("sexo"),
                                rsAdmin_PL.getString("labor"), rsAdmin_PL.getString("descripciontarea"),
                                rsAdmin_PL.getTimestamp("fechainicio"));

                    } else {
                         System.out.println("user");
                        //Personal Externo
                        stmAdmin_PL = con.prepareStatement("select usuario, estardentro "
                                + "from personalexterno "
                                + "where usuario = ? ");
                        stmAdmin_PL.setString(1, rsUsuario.getString("dni"));
                        rsAdmin_PL = stmAdmin_PL.executeQuery();

                        //Comprobamos se e persoal Externo
                        if (rsAdmin_PL.next()) {
                            resultado = new PersonalExterno(rsUsuario.getString("dni"), rsAdmin_PL.getString("usuario"),
                                    rsUsuario.getString("correoElectronico"),
                                    rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                                    rsUsuario.getString("segundoApellido"), rsUsuario.getString("paisProcedencia"),
                                    rsUsuario.getInt("telefono"), rsUsuario.getString("sexo"),
                                    rsAdmin_PL.getBoolean("estardentro"));
                            //Noutro caso tomamolo como usuario normal
                        } else {
                            resultado = new Usuario(rsUsuario.getString("dni"), rsUsuario.getString("id"),
                                    rsUsuario.getString("correoElectronico"),
                                    rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                                    rsUsuario.getString("segundoApellido"), rsUsuario.getString("paisProcedencia"),
                                    rsUsuario.getInt("telefono"), rsUsuario.getString("sexo"));
                        }
                    }
                }

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

    public Boolean modificarContrasenha(String idUsuario, String clave) {
        Connection con;
        PreparedStatement stmUsuario = null;
        PreparedStatement stmBorrado = null;
        Boolean correcto;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("update usuario "
                    + "set contrasenha=crypt(?,gen_salt('md5')) "
                    + "where id=?");

            stmUsuario.setString(1, clave);
            stmUsuario.setString(2, idUsuario);

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

    public Boolean modificarUsuario(Usuario us) {
        Connection con;
        PreparedStatement stmUsuario = null;
        PreparedStatement stmBorrado = null;
        Boolean correcto;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("update usuario "
                    + "set id=?,correoElectronico=?, nombre=?,"
                    + "primerApellido=?, segundoApellido=?, paisProcedencia=?,"
                    + "telefono=?,sexo=? "
                    + "where dni=?");

            stmUsuario.setString(1, us.getId());
            stmUsuario.setString(2, us.getEmail());
            stmUsuario.setString(3, us.getNombre());
            stmUsuario.setString(4, us.getAp1());
            stmUsuario.setString(5, us.getAp2());
            stmUsuario.setString(6, us.getPaisProcedencia());
            stmUsuario.setInt(7, us.getTelefono());
            stmUsuario.setString(8, us.cambiarFormatoSexo(us.getSexo()));
            stmUsuario.setString(9, us.getDni());

            stmUsuario.executeUpdate();
            /*Si es admin se actualiza el curriculum*/
            if(us instanceof Administrador){
            stmUsuario=con.prepareStatement("update administrador set curriculum=? where usuario=?");
            stmUsuario.setString(1, ((Administrador)us).getCurriculum());
            stmUsuario.setString(2, us.getDni());
            stmUsuario.executeUpdate();
            }
            
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

    /*El tipo debe ser mes, estacion o anho*/
    public EstadisticasUsuario obtenerEstadisticasUsuario(String dniUs, String tipo, Integer num) {
        EstadisticasUsuario resultado = null;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        String consulta;
        String aux;
        con = this.getConexion();

        //Parte de la consulta que varía en función de los argumentos
        if (tipo.equals("anho")) {
            aux = "EXTRACT(YEAR FROM cast(v.fechaSalidaReal as date))";
        } else if (tipo.equals("mes")) {
            aux = "EXTRACT(MONTH FROM cast(v.fechaSalidaReal as date))";
        } else {
            aux = "Estacion(v.fechaSalidaReal)";
        }

        consulta = "select aerolinea.aerolineaFav as aerolinea, destino.destinoFav as destino, tarifa.tarifaFav as tarifa, billete.vecesViajadas as veces from "
                + "(select a.aerolinea as aerolineaFav from comprarbillete cb, vuelo v, avion a "
                + "where cb.usuario=? and cb.vuelo=v.numvuelo and v.avion=a.codigo and " + aux + "=?"
                + "group by a.aerolinea having count(*)>=all "
                + "(select count(*) from comprarbillete cb, vuelo v, avion a "
                + "where cb.usuario=? and cb.vuelo=v.numvuelo and v.avion=a.codigo and " + aux + "=?"
                + "group by a.aerolinea)) as aerolinea, "
                + "(select v.destino as destinoFav from comprarbillete cb, vuelo v "
                + "where cb.usuario=? and cb.vuelo=v.numvuelo and " + aux + "=?"
                + "group by v.destino having count(*)>=all "
                + "(select count(*) from comprarbillete cb, vuelo v "
                + "where cb.usuario=? and cb.vuelo=v.numvuelo and " + aux + "=? group by v.destino)) as destino, "
                + "(select tipoAsiento as tarifaFav from comprarbillete cb, vuelo v "
                + "where cb.usuario=? and cb.vuelo=v.numvuelo and " + aux + "=?"
                + "group by tipoAsiento having count(*)>=all "
                + "(select count(*) from comprarbillete cb, vuelo v "
                + "where cb.usuario=? and cb.vuelo=v.numvuelo and " + aux + "=? group by cb.tipoAsiento)) as tarifa, "
                + "(select count(*) as vecesViajadas from comprarBillete cb, vuelo v "
                + "where usuario=? and cb.vuelo=v.numvuelo and " + aux + "=?) as billete";

        try {
            stmUsuario = con.prepareStatement(consulta);
            stmUsuario.setString(1, dniUs);
            stmUsuario.setInt(2, num);
            stmUsuario.setString(3, dniUs);
            stmUsuario.setInt(4, num);
            stmUsuario.setString(5, dniUs);
            stmUsuario.setInt(6, num);
            stmUsuario.setString(7, dniUs);
            stmUsuario.setInt(8, num);
            stmUsuario.setString(9, dniUs);
            stmUsuario.setInt(10, num);
            stmUsuario.setString(11, dniUs);
            stmUsuario.setInt(12, num);
            stmUsuario.setString(13, dniUs);
            stmUsuario.setInt(14, num);
            rsUsuario = stmUsuario.executeQuery();

            if (rsUsuario.next()) {
                resultado = new EstadisticasUsuario(rsUsuario.getInt("veces"));
                resultado.anadirAerolinea(rsUsuario.getString("aerolinea"));
                resultado.anadirDestino(rsUsuario.getString("destino"));
                resultado.anadirTarifa(rsUsuario.getString("tarifa"));

            } else {
                resultado = new EstadisticasUsuario(0);
            }
            /*Si se encuentra más de una tupla*/
            while (rsUsuario.next()) {
                resultado.anadirAerolinea(rsUsuario.getString("aerolinea"));
                resultado.anadirDestino(rsUsuario.getString("destino"));
                resultado.anadirTarifa(rsUsuario.getString("tarifa"));
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
}
