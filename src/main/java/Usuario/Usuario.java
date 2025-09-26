package Usuario;

public abstract class Usuario {

    private int contadorUsuario;
    private String nome;
    private String email;

    public Usuario() {
    }

    public  Usuario( String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    abstract public void mostrarUsuario();

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
