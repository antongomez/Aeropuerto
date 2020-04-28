package aeropuerto.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

                    String[] seg_milis = hora_min_seg[2].split(hora_min_seg[2].charAt(2) + ""); //Non está funcionando

                    if (seg_milis.length == 2) {
                        datos.put("segundos", Integer.parseInt(seg_milis[0]));
                        datos.put("milis", Integer.parseInt(seg_milis[1]));
                    } else { //Estamos utilizando isto, polo que se suprimen os segundos e os milis
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
        Long miliseg = (long) milis;
        miliseg += (long) segundos * 1000;
        miliseg += (long) (minutos * 60) * 1000;
        miliseg += (long) (((horas - 1) * 60) * 60) * 1000;
        miliseg += (long) ((((dia - 1) * 24) * 60) * 60) * 1000;

        Integer anos = 1970;
        GregorianCalendar calendar = new GregorianCalendar();
        while (anos < ano) {
            if (calendar.isLeapYear(anos)) {
                miliseg += (long) (366 * 24) * 60 * 60 * 1000;
                anos++;
            } else {
                miliseg += (long) (365 * 24) * 60 * 60 * 1000;
                anos++;
            }
        }

        Integer meses = 1;
        while (meses < mes) {
            switch (meses) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    miliseg += (long) (31 * 24) * 60 * 60 * 1000;
                    meses++;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    miliseg += (long) (30 * 24) * 60 * 60 * 1000;
                    meses++;
                    break;
                case 2:
                    if (calendar.isLeapYear(ano)) {
                        miliseg += (long) (29 * 24) * 60 * 60 * 1000;
                        meses++;
                    } else {
                        miliseg += (long) (28 * 24) * 60 * 60 * 1000;
                        meses++;
                    }
                    break;

            }

        }
        miliseg -= 60 * 60 * 1000; //Apaño
        System.out.println("Data real: " + getStringSql() + ".Data nova: " + (new Timestamp(miliseg)).toString());

        return new Timestamp(miliseg);
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

    public static Boolean fechaMayorIgualActual(Time fecha) {
        return (fecha.toTimestamp().after(Time.diaActual().toTimestamp())
                || !fecha.toTimestamp().before(Time.diaActual().toTimestamp()));
    }

    public static Boolean compararMayor(Time fecha1, Time fecha2) {
        return (fecha1.toTimestamp().after(fecha2.toTimestamp()));
    }

}
