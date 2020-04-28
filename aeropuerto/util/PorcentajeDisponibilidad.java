package aeropuerto.util;

public class PorcentajeDisponibilidad {

    private Integer totalPlazas;
    private Integer plazasLibres;

    public PorcentajeDisponibilidad(Integer totalPlazas, Integer plazasLibres) {
        if ((totalPlazas != null) && (totalPlazas > 0)) {
            this.totalPlazas = totalPlazas;
            if (plazasLibres == null) {
                this.plazasLibres = 0;
            } else if (totalPlazas >= plazasLibres) {
                this.plazasLibres = plazasLibres;
            } else {
                this.totalPlazas = 0;
                this.plazasLibres = 0;
            }
        } else {
            this.totalPlazas = 0;
            this.plazasLibres = 0;
        }
    }

    public Double calcularPorcentajeDisp() {
        return ((double) plazasLibres / (double) totalPlazas);
    }

    public Double calcularPorcentajeOcup() {
        return ((double) (totalPlazas - plazasLibres) / (double) totalPlazas);
    }

    public Integer getTotalPlazas() {
        return totalPlazas;
    }

    public void setTotalPlazas(Integer totalPlazas) {
        this.totalPlazas = totalPlazas;
    }

    public Integer getPlazasLibres() {
        return plazasLibres;
    }

    public void setPlazasLibres(Integer plazasLibres) {
        this.plazasLibres = plazasLibres;
    }

}
