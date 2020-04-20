package aeropuerto.util;

import java.sql.Timestamp;
import java.util.HashMap;

public class Time {

    private Integer ano;
    private Integer mes;
    private Integer dia;
    private Integer horas;
    private Integer minutos;
    private Integer segundos;
    private Integer milis;

    public Time(Timestamp fecha) {
        HashMap<String, Integer> datos = obtenerDatos(fecha);
        this.ano = datos.get("ano");
        this.mes = datos.get("mes");
        this.dia = datos.get("dia");
        this.horas = datos.get("horas");
        this.minutos = datos.get("minutos");
        this.segundos = datos.get("segundos");
        this.milis = datos.get("milis");
    }

    private HashMap<String, Integer> obtenerDatos(Timestamp fecha) {
        HashMap<String, Integer> datos = new HashMap<>();
        String[] dias_horas = fecha.toString().split(" ");

        if (dias_horas.length == 2) {
            String[] ano_mes_dia = dias_horas[0].split("-");
            String[] hora_min_seg = dias_horas[1].split(":");

            if ((ano_mes_dia.length == 3) && (hora_min_seg.length == 3)) {
                try {
                    datos.put("ano", Integer.parseInt(ano_mes_dia[0]));
                    datos.put("mes", Integer.parseInt(ano_mes_dia[1]));
                    datos.put("dia", Integer.parseInt(ano_mes_dia[2]));

                    datos.put("horas", Integer.parseInt(hora_min_seg[0]));
                    datos.put("minutos", Integer.parseInt(hora_min_seg[1]));

                    String[] seg_milis = hora_min_seg[2].split(".");

                    if (seg_milis.length == 2) {
                        datos.put("segundos", Integer.parseInt(seg_milis[0]));
                        datos.put("milis", Integer.parseInt(seg_milis[1]));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro, no tipo de dato int\n");
                }
            }
        }

        return datos;
    }

    public String getStringSql() {
        String data = "%04d-%02d-%02d %02d:%02d:%02d.%d";

        return String.format(data, ano, mes, dia, horas, minutos, segundos, milis);
    }

    //Getters
    public Integer getAno() {
        return ano;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getDia() {
        return dia;
    }

    public Integer getHoras() {
        return horas;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public Integer getSegundos() {
        return segundos;
    }

    public Integer getMilis() {
        return milis;
    }

    @Override
    public String toString() {
        String data = "%02d:%02d %02d/%02d/%04d";

        return String.format(data, horas, minutos, dia, mes, ano);
    }

}
