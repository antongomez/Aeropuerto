package aeropuerto.elementos;

public class Usuario {

    private String dni;
    private String id;
    private String email;
    private String contrasenha;
    private String nombre;
    private String ap1;
    private String ap2;
    private String paisProcedencia;
    private Integer telefono;
    private String sexo; //Estaría mejor usar char, pero no se como pasar un char en el dao a la base de datos

    public Usuario(String dni, String id, String email, String contrasenha, String nombre, String ap1, String ap2, String paisProcedencia, Integer telefono, String sexo) {

        //No sé si hay que poner comprobaciones de que no sean nulos, dni correcto y sexo m/h/- si ya lo hace sql
        this.dni = dni;
        this.id = id;
        this.email = email;
        this.contrasenha = contrasenha;
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.paisProcedencia = paisProcedencia;
        this.telefono = telefono;
        if (sexo.equals("Hombre")) {
            this.sexo = "h";
        } else if (sexo.equals("Mujer")) {
            this.sexo = "m";
        } else {
            this.sexo = "-";
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

    public String getContrasenha() {
        return contrasenha;
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

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
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

}
