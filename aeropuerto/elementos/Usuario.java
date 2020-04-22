package aeropuerto.elementos;

public class Usuario {

    private String dni;
    private String id;
    private String email;
    private String nombre;
    private String ap1;
    private String ap2;
    private String paisProcedencia;
    private Integer telefono;
    private String sexo; //Estaría mejor usar char, pero no se como pasar un char en el dao a la base de datos

    public Usuario(String dni, String id, String email,String nombre, String ap1, String ap2, String paisProcedencia, Integer telefono, String sexo) {

        //No sé si hay que poner comprobaciones de que no sean nulos, dni correcto y sexo m/h/- si ya lo hace sql
        this.dni = dni;
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.paisProcedencia = paisProcedencia;
        this.telefono = telefono;
        this.sexo = sexo;
        if(sexo.length()==1){
            this.sexo=cambiarFormatoSexo(sexo);
        }
        
    }

    public String getDni() {
        return dni;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public String getNombre() {
        return nombre;
    }

    public String getAp1() {
        return ap1;
    }

    public String getAp2() {
        return ap2;
    }

    public String getPaisProcedencia() {
        return paisProcedencia;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public String getSexo() {
        return sexo;
    }

    //No hay setter para dni porque no se puede cambiar
    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAp1(String ap1) {
        this.ap1 = ap1;
    }

    public void setAp2(String ap2) {
        this.ap2 = ap2;
    }

    public void setPaisProcedencia(String paisProcedencia) {
        this.paisProcedencia = paisProcedencia;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String cambiarFormatoSexo(String sexoFormatoOriginal) {
        String sexoFormatoFinal;
        if (sexoFormatoOriginal.length() == 1) {
            switch (sexoFormatoOriginal) {
                case "h":
                    sexoFormatoFinal = "Hombre";
                    break;
                case "m":
                    sexoFormatoFinal = "Mujer";
                    break;
                default:
                    sexoFormatoFinal = "Prefiero no contestar";
                    break;
            }
        } else {
            switch (sexoFormatoOriginal) {
                case "Hombre":
                    sexoFormatoFinal = "h";
                    break;
                case "Mujer":
                    sexoFormatoFinal = "m";
                    break;
                default:
                    sexoFormatoFinal = "-";
                    break;
            }
        }
        return sexoFormatoFinal;
    }
}
