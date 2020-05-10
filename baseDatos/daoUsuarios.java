package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Administrador;
import aeropuerto.elementos.ElemHistorial;
import aeropuerto.elementos.PersonalLaboral;
import aeropuerto.elementos.Usuario;
import aeropuerto.util.EstadisticasUsuario;
import aeropuerto.util.Time;
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
            stmUsuario = con.prepareStatement("INSERT into usuario(dni,id,correoElectronico,contrasenha, nombre, \n"
                    + "primerApellido, segundoApellido, paisProcedencia, telefono, sexo) "
                    + "VALUES(?,?,?,crypt(?, gen_salt('md5')),?,?,?,?,?,?)");

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
            String m = e.getMessage();
            if (m.contains("usuario_dni_check") || m.contains("character(9)")) {
                getFachadaAplicacion().mostrarError("Formato de dni incorrecto");
            } else if (m.contains("usuario_pkey")) {
                getFachadaAplicacion().mostrarError("Usuario ya registrado");
            } else if (m.contains("usuario_id_key")) {
                getFachadaAplicacion().mostrarError("El id elegido ya corresponde a otro usuario");
            } else if (m.contains("usuario_correoelectronico_key")) {
                getFachadaAplicacion().mostrarError("Ya existe un usuario registrado con ese email");
            } else if (m.contains("demasiado largo")) {
                getFachadaAplicacion().mostrarError("Uno de los campos contiene un valor demasiado largo. "
                        + "Revise los datos introducidos.");
            } else {
                getFachadaAplicacion().mostrarError(e.getMessage());

            }
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
        PreparedStatement stmAdmin = null;
        PreparedStatement stmPL = null;
        ResultSet rsUsuario;
        ResultSet rsAdmin_PL;
        Boolean esAdmin = false, esPL = false;

        con = this.getConexion();

        try {
            stmUsuario = con.prepareStatement("SELECT dni,id,correoElectronico,contrasenha, nombre,\n"
                    + "primerApellido, segundoApellido, paisProcedencia, telefono, sexo \n"
                    + "FROM usuario \n"
                    + "WHERE id = ? and contrasenha = crypt(?, contrasenha)");
            stmUsuario.setString(1, idUsuario);
            stmUsuario.setString(2, clave);
            rsUsuario = stmUsuario.executeQuery();

            //Se existe un usuario, comprobamos se e administrador ou persoal laboral
            if (rsUsuario.next()) {
                //Administrador
                try {

                    stmAdmin = con.prepareStatement("SELECT usuario,fechainicio,curriculum \n"
                            + "FROM administrador \n"
                            + "WHERE usuario = ? ");
                    stmAdmin.setString(1, rsUsuario.getString("dni"));
                    rsAdmin_PL = stmAdmin.executeQuery();

                    //Comprobamos se e admin
                    if (rsAdmin_PL.next()) {
                        resultado = new Administrador(rsUsuario.getString("dni"), rsUsuario.getString("id"),
                                rsUsuario.getString("correoElectronico"),
                                rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                                rsUsuario.getString("segundoApellido"), rsUsuario.getString("paisProcedencia"),
                                rsUsuario.getInt("telefono"), rsUsuario.getString("sexo"),
                                rsAdmin_PL.getTimestamp("fechainicio"), rsAdmin_PL.getString("curriculum"));
                        esAdmin = true;
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    this.getFachadaAplicacion().mostrarError(e.getMessage());
                } finally {
                    try {
                        stmAdmin.close();
                    } catch (SQLException e) {
                        System.out.println("Imposible cerrar cursores");
                    }
                }

                if (!esAdmin) {
                    try {
                        //Personal Laboral
                        stmPL = con.prepareStatement("SELECT usuario, labor, descripciontarea, fechainicio \n"
                                + "FROM personallaboral \n"
                                + "WHERE usuario = ? ");
                        stmPL.setString(1, rsUsuario.getString("dni"));
                        rsAdmin_PL = stmPL.executeQuery();

                        //Comprobamos se e PL
                        if (rsAdmin_PL.next()) {
                            resultado = new PersonalLaboral(rsUsuario.getString("dni"), rsUsuario.getString("id"),
                                    rsUsuario.getString("correoElectronico"),
                                    rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                                    rsUsuario.getString("segundoApellido"), rsUsuario.getString("paisProcedencia"),
                                    rsUsuario.getInt("telefono"), rsUsuario.getString("sexo"),
                                    rsAdmin_PL.getString("labor"), rsAdmin_PL.getString("descripciontarea"),
                                    rsAdmin_PL.getTimestamp("fechainicio"));
                            esPL = true;

                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        this.getFachadaAplicacion().mostrarError(e.getMessage());
                    } finally {
                        try {
                            stmPL.close();
                        } catch (SQLException e) {
                            System.out.println("Imposible cerrar cursores");
                        }
                    }

                    if (!esPL) {

                        resultado = new Usuario(rsUsuario.getString("dni"), rsUsuario.getString("id"),
                                rsUsuario.getString("correoElectronico"),
                                rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                                rsUsuario.getString("segundoApellido"), rsUsuario.getString("paisProcedencia"),
                                rsUsuario.getInt("telefono"), rsUsuario.getString("sexo"));
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

            stmUsuario = con.prepareStatement("UPDATE usuario \n"
                    + "SET contrasenha=crypt(?,gen_salt('md5')) \n"
                    + "WHERE id=?");

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
        PreparedStatement stmAdmin = null;
        Boolean correcto = false;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("UPDATE usuario \n"
                    + "SET id=?,correoElectronico=?, nombre=?, \n"
                    + "primerApellido=?, segundoApellido=?, paisProcedencia=?, \n"
                    + "telefono=?,sexo=? \n"
                    + "WHERE dni=?");

            stmUsuario.setString(1, us.getId());
            stmUsuario.setString(2, us.getEmail());
            stmUsuario.setString(3, us.getNombre());
            stmUsuario.setString(4, us.getAp1());
            stmUsuario.setString(5, us.getAp2());
            stmUsuario.setString(6, us.getPaisProcedencia());
            stmUsuario.setInt(7, us.getTelefono());
            stmUsuario.setString(8, us.cambiarFormatoSexo(us.getSexo()));
            stmUsuario.setString(9, us.getDni());

            if (stmUsuario.executeUpdate() > 0) {
                correcto = true;
            }
            /*Si es admin se actualiza el curriculum*/
            if (us instanceof Administrador) {
                try {
                    stmAdmin = con.prepareStatement("UPDATE administrador \n"
                            + "SET curriculum=? \n"
                            + "WHERE usuario=?");
                    stmAdmin.setString(1, ((Administrador) us).getCurriculum());
                    stmAdmin.setString(2, us.getDni());
                    stmAdmin.executeUpdate();
                } catch (SQLException e) {
                    if (e.getMessage().contains("demasiado largo")) {
                        this.getFachadaAplicacion().mostrarError("El currículum no debe superar los 500 caracteres");
                    } else {
                        System.out.println(e.getMessage());
                        this.getFachadaAplicacion().mostrarError(e.getMessage());
                    }
                    correcto = false;
                } finally {
                    try {
                        stmAdmin.close();
                    } catch (SQLException e) {
                        System.out.println("Imposible cerrar cursores");
                        correcto = false;
                    }
                }
            }

        } catch (SQLException e) {
            String m = e.getMessage();
            if (m.contains("usuario_id_key")) {
                getFachadaAplicacion().mostrarError("El id elegido ya corresponde a otro usuario");
            } else if (m.contains("usuario_correoelectronico_key")) {
                getFachadaAplicacion().mostrarError("Ya existe un usuario registrado con ese email");
            } else if (m.contains("demasiado largo")) {
                getFachadaAplicacion().mostrarError("Uno de los campos contiene un valor demasiado largo. "
                        + "Revise los datos introducidos.");
            } else {
                getFachadaAplicacion().mostrarError(e.getMessage());

            }
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

    public Boolean eliminarUsuario(String dni) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto;

        con = super.getConexion();

        try {
            stmUsuario = con.prepareStatement("DELETE \n"
                    + "FROM usuario \n"
                    + "WHERE dni = ?");
            stmUsuario.setString(1, dni);
            stmUsuario.executeUpdate();
            correcto = true;
        } catch (SQLException e) {
            String m = e.getMessage();
            if (m.contains("fkey")) {
                this.getFachadaAplicacion().mostrarError("Ya has disfrutado de nuestros servicios. No puedes darte de baja.");
            } else {
                System.out.println(e.getMessage());
                this.getFachadaAplicacion().mostrarError(e.getMessage());
            }
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

        consulta = "SELECT aerolinea.aerolineaFav as aerolinea, destino.destinoFav as destino, \n"
                + "tarifa.tarifaFav as tarifa, billete.vecesViajadas as veces \n"
                + "FROM (SELECT cb.usuario as usuario, a.aerolinea as aerolineaFav \n"
                + "      FROM comprarbillete cb, vuelo v, avion a \n"
                + "      WHERE cb.usuario = ? \n"
                + "      and cb.vuelo = v.numvuelo\n"
                + "      and v.avion = a.codigo \n"
                + "      and " + aux + " = ? \n"
                + "      and v.cancelado = false \n"
                + "      GROUP BY a.aerolinea, cb.usuario \n"
                + "      HAVING count(*)>=all (SELECT count(*) \n"
                + "                            FROM comprarbillete cb, vuelo v, avion a \n"
                + "                            WHERE cb.usuario = ? \n"
                + "                            and cb.vuelo = v.numvuelo \n"
                + "                            and v.avion = a.codigo \n"
                + "                            and " + aux + " = ? \n "
                + "                            and v.cancelado = false \n"
                + "                            GROUP BY a.aerolinea)) \n"
                + "as aerolinea \n"
                + "NATURAL FULL JOIN \n"
                + "     (SELECT cb.usuario as usuario, v.destino as destinoFav \n"
                + "      FROM comprarbillete cb, vuelo v \n"
                + "      WHERE cb.usuario = ? \n"
                + "      and v.destino != 'Folgoso do Courel' \n"
                + "      and cb.vuelo = v.numvuelo \n"
                + "      and " + aux + " = ? \n"
                + "      and v.cancelado = false \n"
                + "      GROUP BY v.destino, cb.usuario\n"
                + "      HAVING count(*)>=all(SELECT count(*) \n"
                + "                           FROM comprarbillete cb, vuelo v \n"
                + "                           WHERE cb.usuario = ? \n"
                + "                           and v.destino != 'Folgoso do Courel' \n"
                + "                           and cb.vuelo = v.numvuelo \n"
                + "                           and " + aux + " = ? \n"
                + "                           and v.cancelado = false \n"
                + "                           GROUP BY v.destino)) \n"
                + "as destino \n"
                + "NATURAL FULL JOIN \n"
                + "     (SELECT cb.usuario as usuario, tipoAsiento as tarifaFav \n"
                + "      FROM comprarbillete cb, vuelo v \n"
                + "      WHERE cb.usuario = ? \n"
                + "      and cb.vuelo = v.numvuelo \n"
                + "      and " + aux + " = ? \n"
                + "      and v.cancelado = false \n"
                + "      GROUP BY tipoAsiento, cb.usuario \n"
                + "      HAVING count(*)>=all(SELECT count(*) \n"
                + "                           FROM comprarbillete cb, vuelo v \n"
                + "                           WHERE cb.usuario = ? \n"
                + "                           and cb.vuelo = v.numvuelo \n"
                + "                           and " + aux + " = ? \n"
                + "                           and v.cancelado = false \n"
                + "                           GROUP BY cb.tipoAsiento)) \n"
                + "as tarifa \n"
                + "NATURAL FULL JOIN \n"
                + "     (SELECT cb.usuario as usuario, count(*) as vecesViajadas \n"
                + "      FROM comprarBillete cb, vuelo v \n"
                + "      WHERE cb.usuario = ? \n"
                + "      and cb.vuelo = v.numvuelo \n"
                + "      and v.cancelado = false \n"
                + "      and " + aux + " = ? \n"
                + "      GROUP BY cb.usuario) \n"
                + "as billete";

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
                if (rsUsuario.getString("destino") != null) {
                    resultado.anadirDestino(rsUsuario.getString("destino"));
                }
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

    public EstadisticasUsuario obtenerEstadisticasGlobalesUsuario(String dniUs) {
        EstadisticasUsuario resultado = null;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        String consulta;
        con = this.getConexion();

        consulta = "SELECT aerolinea.aerolineaFav as aerolinea, destino.destinoFav as destino, tarifa.tarifaFav as tarifa \n"
                + "FROM (SELECT cb.usuario as usuario, a.aerolinea as aerolineaFav \n"
                + "      FROM comprarbillete cb, vuelo v, avion a \n"
                + "      WHERE cb.usuario = ? \n"
                + "      and cb.vuelo = v.numvuelo \n"
                + "      and v.avion = a.codigo \n"
                + "      and v.cancelado = false \n"
                + "      GROUP BY a.aerolinea, cb.usuario \n"
                + "      HAVING count(*)>=all (SELECT count(*) \n"
                + "                            FROM comprarbillete cb, vuelo v, avion a \n"
                + "                            WHERE cb.usuario = ? \n"
                + "                            and cb.vuelo = v.numvuelo \n"
                + "                            and v.avion = a.codigo \n"
                + "                            and v.cancelado = false \n"
                + "                            GROUP BY a.aerolinea)) \n"
                + "as aerolinea \n"
                + "NATURAL FULL JOIN \n"
                + "     (SELECT cb.usuario as usuario, v.destino as destinoFav \n"
                + "      FROM comprarbillete cb, vuelo v \n"
                + "      WHERE cb.usuario = ? \n"
                + "      and v.destino != 'Folgoso do Courel' \n"
                + "      and cb.vuelo = v.numvuelo\n"
                + "      and v.cancelado = false \n"
                + "      GROUP BY v.destino, cb.usuario \n"
                + "      HAVING count(*)>=all(SELECT count(*) \n"
                + "                           FROM comprarbillete cb, vuelo v \n"
                + "                           WHERE cb.usuario = ? \n"
                + "                           and v.destino != 'Folgoso do Courel' \n"
                + "                           and cb.vuelo = v.numvuelo \n"
                + "                           and v.cancelado = false \n"
                + "                           GROUP BY v.destino)) \n"
                + "as destino \n"
                + "NATURAL FULL JOIN \n"
                + "     (SELECT cb.usuario as usuario, tipoAsiento as tarifaFav \n"
                + "      FROM comprarbillete cb, vuelo v \n"
                + "      WHERE cb.usuario = ? \n"
                + "      and cb.vuelo = v.numvuelo \n"
                + "      and v.cancelado = false \n"
                + "      GROUP BY tipoAsiento, cb.usuario \n"
                + "      HAVING count(*)>=all(SELECT count(*) \n"
                + "                           FROM comprarbillete cb, vuelo v \n"
                + "                           WHERE cb.usuario = ? \n"
                + "                           and cb.vuelo = v.numvuelo \n"
                + "                           and v.cancelado = false \n"
                + "                           GROUP BY cb.tipoAsiento)) \n"
                + "as tarifa";

        try {
            stmUsuario = con.prepareStatement(consulta);
            stmUsuario.setString(1, dniUs);
            stmUsuario.setString(2, dniUs);
            stmUsuario.setString(3, dniUs);
            stmUsuario.setString(4, dniUs);
            stmUsuario.setString(5, dniUs);
            stmUsuario.setString(6, dniUs);
            rsUsuario = stmUsuario.executeQuery();

            if (rsUsuario.next()) {
                resultado = new EstadisticasUsuario();
                resultado.anadirAerolinea(rsUsuario.getString("aerolinea"));
                if (rsUsuario.getString("destino") != null) {
                    resultado.anadirDestino(rsUsuario.getString("destino"));
                }
                resultado.anadirTarifa(rsUsuario.getString("tarifa"));

            } else {
                resultado = new EstadisticasUsuario();
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

    public Usuario obtenerUsuario(String dni) {

        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        Usuario us = null;

        con = this.getConexion();

        try {
            stmUsuario = con.prepareStatement("SELECT dni, nombre, primerApellido, segundoApellido\n"
                    + "FROM usuario \n"
                    + "WHERE dni=?");
            stmUsuario.setString(1, dni);
            rsUsuario = stmUsuario.executeQuery();

            if (rsUsuario.next()) {
                us = new Usuario(rsUsuario.getString("dni"), rsUsuario.getString("nombre"), rsUsuario.getString("primerApellido"),
                        rsUsuario.getString("segundoApellido"));
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
        return us;

    }

    public Boolean pasarControlPersExt(String dni) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto = false;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("UPDATE personalexterno \n"
                    + "SET estardentro=true \n"
                    + "WHERE usuario=?");

            stmUsuario.setString(1, dni);

            if (stmUsuario.executeUpdate() > 0) {
                correcto = true;
            } else {
                getFachadaAplicacion().mostrarError("Estos datos no corresponden con ningún personal externo");
            }

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

    public Boolean salirControlPersExt(String dni) {
        Connection con;
        PreparedStatement stmUsuario = null;
        Boolean correcto = false;

        con = super.getConexion();

        try {

            stmUsuario = con.prepareStatement("UPDATE personalexterno \n"
                    + "SET estardentro=false \n"
                    + "WHERE usuario=?");

            stmUsuario.setString(1, dni);

            if (stmUsuario.executeUpdate() > 0) {
                correcto = true;
            } else {
                getFachadaAplicacion().mostrarError("Estos datos no corresponden con ningún personal externo");
            }

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

    public Boolean estaDentroPersLaboral(String dni) {
        Connection con;
        PreparedStatement stmHistorial = null;
        ResultSet rsHistorial;
        Boolean result = null;

        con = this.getConexion();

        try {
            stmHistorial = con.prepareStatement("SELECT count(*)>0 as dentro \n"
                    + "FROM historialtrabajo \n"
                    + "WHERE personallaboral=? and fechasalida is null");
            stmHistorial.setString(1, dni);
            rsHistorial = stmHistorial.executeQuery();

            if (rsHistorial.next()) {
                result = rsHistorial.getBoolean("dentro");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmHistorial.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return result;
    }

    public void entrarPersLaboral(String dni) {
        Connection con;
        PreparedStatement stmHist = null;

        con = super.getConexion();

        try {

            stmHist = con.prepareStatement("INSERT into historialtrabajo(personallaboral) \n"
                    + "VALUES (?)");

            stmHist.setString(1, dni);
            stmHist.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmHist.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public void salirPersLaboral(String dni) {
        Connection con;
        PreparedStatement stmHist = null;

        con = super.getConexion();

        try {

            stmHist = con.prepareStatement("UPDATE historialtrabajo \n"
                    + "SET fechasalida=NOW() \n"
                    + "WHERE fechasalida is null and personallaboral=?");

            stmHist.setString(1, dni);
            stmHist.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmHist.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public Boolean obtenerHistorialPersLaboral(PersonalLaboral usu, Time fechaInicio, Time fechaFin) {
        Connection con;
        PreparedStatement stmHistorial = null;
        ResultSet rsHistorial;
        Boolean result = false;

        con = this.getConexion();

        try {
            stmHistorial = con.prepareStatement("SELECT fechaentrada,fechasalida \n"
                    + "FROM historialtrabajo where personallaboral=? \n"
                    + "and cast(fechaentrada as date)>=? and cast(fechaentrada as date)<=? \n"
                    + "ORDER BY fechaEntrada desc");
            stmHistorial.setString(1, usu.getDni());
            stmHistorial.setTimestamp(2, fechaInicio.toTimestamp());
            stmHistorial.setTimestamp(3, fechaFin.toTimestamp());
            rsHistorial = stmHistorial.executeQuery();

            while (rsHistorial.next()) {
                usu.addElemHistorial(new ElemHistorial(new Time(rsHistorial.getTimestamp("fechaentrada")),
                        new Time(rsHistorial.getTimestamp("fechaentrada"))));
                result = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
            result = false;
        } finally {
            try {
                stmHistorial.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
                result = false;
            }
        }
        return result;

    }

    public Boolean comprobarRegistrado(String dni) {
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        Boolean registrado = false;

        con = this.getConexion();

        try {
            stmUsuario = con.prepareStatement("SELECT dni \n"
                    + "FROM usuario \n"
                    + "WHERE dni=? ");
            stmUsuario.setString(1, dni);
            rsUsuario = stmUsuario.executeQuery();

            if (rsUsuario.next()) {
                registrado = true;
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
        return registrado;
    }

    public Boolean obtenerDatosPersLab(PersonalLaboral trab) {
        Connection con;
        PreparedStatement stmUsu = null;
        ResultSet rsUsu;
        Boolean correcto = false;

        con = this.getConexion();

        try {
            stmUsu = con.prepareStatement("SELECT labor, descripciontarea \n"
                    + "FROM personallaboral \n"
                    + "WHERE usuario=?");
            stmUsu.setString(1, trab.getDni());
            rsUsu = stmUsu.executeQuery();

            if (rsUsu.next()) {
                trab.setLabor(rsUsu.getString("labor"));
                trab.setDescripcionTarea(rsUsu.getString("descripciontarea"));
                correcto = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
            correcto = false;
        } finally {
            try {
                stmUsu.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
                correcto = false;
            }
        }
        return correcto;

    }

}
