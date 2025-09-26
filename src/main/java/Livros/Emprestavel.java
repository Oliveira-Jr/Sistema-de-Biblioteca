package Livros;

public abstract class Emprestavel {
    protected String titulo;
    protected String autor;
    protected int anoPublicacao;
    protected String genero;

    public Emprestavel(){
    };

    public Emprestavel(String titulo, String autor, int anoPublicacao, String genero){
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
    }
}
