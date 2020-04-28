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

/**
 *
 * @author anton
 */
public class daoTerminal extends AbstractDAO {

    public daoTerminal(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public Parking buscarParking(Integer terminal, Time inicio, Time fin) {
        Connection con;
        PreparedStatement stmRes = null;
        ResultSet rsRes;
        con = super.getConexion();
        Parking parking = null;

        try {
            stmRes = con.prepareStatement("select p.piso, p.numplazas\n"
                    + "from parking as p natural join (select terminal, piso, count(*) as plazasOc\n"
                    + "								from reservarParking\n"
                    + "								where fechaentrada <= ?\n"
                    + "  								  and fechafin >= ?\n"
                    + "								group by terminal, piso) as c\n"
                    + "where terminal = ?\n"
                    + "group by p.piso, p.numplazas, c.plazasOc\n"
                    + "having (p.numplazas - c.plazasOc) = max(p.numplazas - c.plazasOc)");
            stmRes.setTimestamp(1, fin.toTimestamp());
            stmRes.setTimestamp(2, inicio.toTimestamp());
            stmRes.setInt(3, terminal);

            rsRes = stmRes.executeQuery();
            if (rsRes.next()) {
                parking = new Parking(terminal, rsRes.getInt("piso"), rsRes.getInt("numplazas"));
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
        return parking;
    }

    public PorcentajeDisponibilidad obterPrazasRestantesParkingTerminal(Integer numTerminal, Time inicio, Time fin) {
        Connection con;
        PreparedStatement stmRes = null;
        ResultSet rsRes;
        con = super.getConexion();
        PorcentajeDisponibilidad per = null;

        try {
            stmRes = con.prepareStatement("select sum(numPlazas) as totalPlazas, sum(p.numplazas - coalesce(c.plazasOc, 0)) as plazasLibres\n"
                    + "from parking as p natural left outer join (select terminal, piso, count(*) as plazasOc\n"
                    + "								from reservarParking\n"
                    + "								where fechaentrada <= ?\n"
                    + "								  and fechafin >= ?\n"
                    + "								group by terminal, piso) as c\n"
                    + "where terminal = ?");
            stmRes.setTimestamp(1, fin.toTimestamp());
            stmRes.setTimestamp(2, inicio.toTimestamp());
            stmRes.setInt(3, numTerminal);

            rsRes = stmRes.executeQuery();
            if (rsRes.next()) {
                per = new PorcentajeDisponibilidad(rsRes.getInt("totalPlazas"), rsRes.getInt("plazasLibres"));
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
        return per;
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
