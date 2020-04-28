/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aeropuerto.FachadaAplicacion;
import aeropuerto.elementos.Parking;
import aeropuerto.util.PorcentajeDisponibilidad;
import aeropuerto.util.Time;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class daoTerminal extends AbstractDAO {

    public daoTerminal(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public Parking buscarParking(Integer terminal, Time inicio, Time fin) {
        Connection con;
        PreparedStatement stmParking = null;
        ResultSet rsParking;
        con = super.getConexion();
        Parking parking = null;

        try {
            stmParking = con.prepareStatement("select p.piso, p.numplazas\n"
                    + "from parking as p natural left outer join (select terminal, piso, count(*) as plazasOc\n"
                    + "								from reservarParking\n"
                    + "								where fechaentrada <= ?\n"
                    + "  								  and fechafin >= ?\n"
                    + "								group by terminal, piso) as c\n"
                    + "where terminal = ?\n"
                    + "group by p.piso, p.numplazas, c.plazasOc\n"
                    + "having (p.numplazas - coalesce(c.plazasOc, 0)) = max(p.numplazas - coalesce(c.plazasOc, 0))");
            stmParking.setTimestamp(1, fin.toTimestamp());
            stmParking.setTimestamp(2, inicio.toTimestamp());
            stmParking.setInt(3, terminal);

            rsParking = stmParking.executeQuery();
            if (rsParking.next()) {
                parking = new Parking(terminal, rsParking.getInt("piso"), rsParking.getInt("numplazas"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmParking.close();
            } catch (SQLException e) {

                System.out.println("Imposible cerrar cursores");
            }
        }
        return parking;
    }

    public PorcentajeDisponibilidad obterPrazasRestantesParkingTerminal(Integer numTerminal, Time inicio, Time fin) {
        Connection con;
        PreparedStatement stmParking = null;
        ResultSet rsParking;
        con = super.getConexion();
        PorcentajeDisponibilidad per = null;

        try {
            stmParking = con.prepareStatement("select sum(numPlazas) as totalPlazas, sum(p.numplazas - coalesce(c.plazasOc, 0)) as plazasLibres\n"
                    + "from parking as p natural left outer join (select terminal, piso, count(*) as plazasOc\n"
                    + "								from reservarParking\n"
                    + "								where fechaentrada <= ?\n"
                    + "								  and fechafin >= ?\n"
                    + "								group by terminal, piso) as c\n"
                    + "where terminal = ?");
            stmParking.setTimestamp(1, fin.toTimestamp());
            stmParking.setTimestamp(2, inicio.toTimestamp());
            stmParking.setInt(3, numTerminal);

            rsParking = stmParking.executeQuery();
            if (rsParking.next()) {
                per = new PorcentajeDisponibilidad(rsParking.getInt("totalPlazas"), rsParking.getInt("plazasLibres"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmParking.close();
            } catch (SQLException e) {

                System.out.println("Imposible cerrar cursores");
            }
        }
        return per;
    }

    public List<Integer> obterPlazasOcupadas(Integer numTerminal, Integer piso, Time inicio, Time fin) {
        Connection con;
        PreparedStatement stmParking = null;
        ResultSet rsRes;
        con = super.getConexion();
        List<Integer> plazas = new ArrayList<>();

        try {
            stmParking = con.prepareStatement("select numPlaza as plazasOcupadas\n"
                    + "from reservarParking \n"
                    + "where fechaentrada <= ? \n"
                    + "  and fechafin >= ? \n"
                    + "  and terminal = ?\n"
                    + "  and piso = ?");
            stmParking.setTimestamp(1, fin.toTimestamp());
            stmParking.setTimestamp(2, inicio.toTimestamp());
            stmParking.setInt(3, numTerminal);
            stmParking.setInt(4, piso);

            rsRes = stmParking.executeQuery();
            while (rsRes.next()) {
                plazas.add(rsRes.getInt("plazasOcupadas"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmParking.close();
            } catch (SQLException e) {

                System.out.println("Imposible cerrar cursores");
            }
        }
        return plazas;
    }

    public Integer obterNumPlazasParking(Integer numTerminal, Integer piso) {
        Connection con;
        PreparedStatement stmParking = null;
        ResultSet rsRes;
        con = super.getConexion();
        Integer numPlazas = null;

        try {
            stmParking = con.prepareStatement("select numPlazas \n"
                    + "from parking \n"
                    + "where terminal = ? \n"
                    + "  and piso = ? \n");
            stmParking.setInt(1, numTerminal);
            stmParking.setInt(2, piso);

            rsRes = stmParking.executeQuery();
            if (rsRes.next()) {
                numPlazas = rsRes.getInt("numPlazas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmParking.close();
            } catch (SQLException e) {

                System.out.println("Imposible cerrar cursores");
            }
        }
        return numPlazas;
    }

    public List<Integer> buscarTerminais() {
        Connection con;
        PreparedStatement stmRes = null;
        ResultSet rsRes;
        con = super.getConexion();
        ArrayList<Integer> terminais = new ArrayList<>();

        try {
            stmRes = con.prepareStatement("select numero from terminal");
            rsRes = stmRes.executeQuery();
            while (rsRes.next()) {
                terminais.add(rsRes.getInt("numero"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().mostrarError(e.getMessage());
        } finally {
            try {
                stmRes.close();
            } catch (SQLException e) {

                System.out.println("Imposible cerrar cursores");
            }
        }
        return terminais;
    }

}
