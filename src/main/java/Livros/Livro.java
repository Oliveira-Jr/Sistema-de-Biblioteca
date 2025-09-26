package Livros;

import Acoes.Emprestimo;
import Biblioteca.Biblioteca;
import Usuario.Leitor;

import java.util.Scanner;

//todo criar metodo devolver

public class Livro extends Emprestavel{

    private static int contadorLivro = 0;
    private String idUnico;
    private StatusLivro status;

    public Livro(String titulo, String autor, int anoPublicacao, String genero, StatusLivro status) {
        super(titulo, autor, anoPublicacao, genero);
        contadorLivro++;
        this.idUnico = "liv-" + contadorLivro;

        this.status = status;
    }

    public String getIdUnico() {
        return idUnico;
    }

    public StatusLivro getStatus() {
        return status;
    }

    public void setStatus(StatusLivro status){
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public Livro() {
    }


    public void mostrarLivro(){
        System.out.println("\nNome: " + titulo);
        System.out.println("Id: " + idUnico);
        System.out.println("Autor: " + autor);
        System.out.println("Gênero: " + genero);
        System.out.println("Ano de publicação: " + anoPublicacao);
        System.out.println("Status: " + status);
    }
}