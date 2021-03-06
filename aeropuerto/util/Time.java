package aeropuerto.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
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
    
    public Time(String interval){
        HashMap<String, Integer> datos = obtenerDatos(interval);
        this.dia = datos.get("dias");
        this.horas = datos.get("horas");
        this.minutos = datos.get("minutos");
    }
    
    private HashMap<String, Integer> obtenerDatos(String fecha) {
        HashMap<String, Integer> datos = new HashMap<>();
        
        String[] dias_hmin = fecha.split(" ");
        datos.put("dias", Integer.parseInt(dias_hmin[0]));
        
        String[] h_min = dias_hmin[1].split(":");
        datos.put("horas", Integer.parseInt(h_min[0]));
        datos.put("minutos", Integer.parseInt(h_min[1]));
        
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
    
    public Time() {
        this.ano = 0;
        this.mes = 0;
        this.dia = 0;
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
        //System.out.println("Data real: " + getStringSql() + ".Data nova: " + (new Timestamp(miliseg)).toString());

        return new Timestamp(miliseg);
    }
    
    public LocalDate toLocalDate(){
        LocalDate fecha= LocalDate.of(this.getAno(), this.getMes(), this.getDia());
        return fecha;
    }

    @Override
    public String toString() {
        String data = "%02d:%02d %02d/%02d/%04d";

        return String.format(data, horas, minutos, dia, mes, ano);
    }

    public String toStringFecha() {
        String data = "%02d/%02d/%04d";

        return String.format(data, dia, mes, ano);
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
    
    
    /*Diferencia entre dos fechas en horas, minutos y segundos. Si las fechas son iguales devuelve false*/
    public Boolean diferencia(Time fecha, Integer dias, Integer horas, Integer minutos){
        
        Time fechaMay;
        Time fechaMen;
        long dif;
        Float min;
        Float hor;
        Float di;
        
        if(fecha.toTimestamp().after(this.toTimestamp())){
            fechaMay=fecha;
            fechaMen=this;
        }
        else if(this.toTimestamp().after(fecha.toTimestamp())){
            fechaMay=this;
            fechaMen=fecha;
        }
        else{
            dias=0;
            horas=0;
            minutos=0;
            return false;
        }
        dif=fechaMay.toTimestamp().getTime()-fechaMen.toTimestamp().getTime();
        min=dif/(float)(60000);
        if(min>=60){
            hor=min/60;
            if(hor>24){
                di=hor/24;
                dias=di.intValue();
                hor=hor-dias*24;
                horas=hor.intValue();
                min=hor*60;
                minutos=Math.round(min-60*horas);
            }
            else{
               horas=hor.intValue();
               minutos=Math.round(min-60*horas);
            }
        }
        else{
            minutos=Math.round(min);
        }
        
        return true;
    }

    public static Integer obtenerDias(LocalDate fecha1, LocalDate fecha2) {
        Timestamp f1 = (new Time(fecha1)).toTimestamp();
        Timestamp f2 = (new Time(fecha2)).toTimestamp();

        Timestamp nDiasMilis;
        if (f1.getTime() >= f2.getTime()) {
            nDiasMilis = new Timestamp(f1.getTime() - f2.getTime());
        } else {
            nDiasMilis = new Timestamp(f2.getTime() - f1.getTime());
        }

        Integer dias = (int) (nDiasMilis.getTime() / 86400000); //Quedamos coa parte enteira

        return dias;
    }
    
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Time)){
            return false;
        }
        Time data = (Time) o;
        
        return ((data.getAno().equals(ano)) && (data.getMes().equals(mes)) && (data.getDia().equals(dia))
                && (data.getHoras().equals(horas)) && (data.getMinutos().equals(minutos)));
    }
}
