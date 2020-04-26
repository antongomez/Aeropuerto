package aeropuerto.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
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

                    String[] seg_milis = hora_min_seg[2].split("."); //Non está funcionando

                    if (seg_milis.length == 2) {
                        datos.put("segundos", Integer.parseInt(seg_milis[0]));
                        datos.put("milis", Integer.parseInt(seg_milis[1]));
                    } else {
                        datos.put("segundos", 0);
                        datos.put("milis", 0);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro, no tipo de dato int\n");
                }
            }
        }

        return datos;
    }

    public Time(LocalDate fecha) {
        this.ano = fecha.getYear();
        this.mes = fecha.getMonthValue();
        this.dia = fecha.getDayOfMonth();
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
        this.milis = 0;
    }

    public String getStringSql() {
        String data = "%04d-%02d-%02d %02d:%02d:%02d.%d";

        return String.format(data, ano, mes, dia, horas, minutos, segundos, milis);
    }

    public Timestamp toTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia, horas, minutos, segundos);

        return new Timestamp(calendar.getTimeInMillis());
    }

    @Override
    public String toString() {
        String data = "%02d:%02d %02d/%02d/%04d";

        return String.format(data, horas, minutos, dia, mes, ano);
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

    public static Time diaActual() {
        return new Time(LocalDate.now());
    }

    public static Boolean fechaMayorActual(Time fecha) {
        return (fecha.toTimestamp().after(Time.diaActual().toTimestamp())
                || !fecha.toTimestamp().before(Time.diaActual().toTimestamp()));
    }

}
