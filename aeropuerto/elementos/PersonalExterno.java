package aeropuerto.elementos;

public class PersonalExterno extends Usuario {

    private Boolean estarDentro;

    public PersonalExterno(String dni, String id, String email, String contrasenha, String nombre, String ap1, String ap2, String paisProcedencia, Integer telefono, String sexo) {

        super(dni, id, email, contrasenha, nombre, ap1, ap2, paisProcedencia, telefono, sexo);

        //cuando se registra un personal externo no se introduce nunca este campo, pues se usa solo para cuando pasa el control
        this.estarDentro = false;
    }

}
