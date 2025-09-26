package Biblioteca;

import Acoes.*;
import Livros.*;
import Usuario.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Biblioteca {  //Inicia as listas da biblioteca
    private List<Livro> acervo = new ArrayList<>();
    private List<Leitor> leitores = new ArrayList<>();
    private List<Administrador> administradores = new ArrayList<>();

    public Biblioteca(){
        inicializarDados();
    }

    private void inicializarDados() {
        // Adiciona livros
        acervo.add(new Livro("Dom Casmurro", "Machado de Assis", 1899, "Romance", StatusLivro.Disponivel));
        acervo.add(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943, "Infantil", StatusLivro.Disponivel));
        acervo.add(new Livro("1984", "George Orwell", 1949, "Distopia", StatusLivro.Disponivel));

        // Adiciona leitores
        leitores.add(new Leitor("João Silva", "joao@email.com"));
        leitores.add(new Leitor("Maria Souza", "maria@email.com"));
    }
    public void cadastrarLeitor() {
            //cadastra o leitor no sistema
            String nome, email;
            Scanner dados = new Scanner(System.in);
        System.out.println("\n--Cadastro de Usuário--\n");
            System.out.print("Digite seu nome: ");
            nome = dados.nextLine();
            System.out.print("Digite seu email: ");
            email = dados.nextLine();

            System.out.println("\nLeitor cadastrado com sucesso!");

            Leitor leitor = new Leitor(nome, email);

            leitor.mostrarUsuario();
            leitores.add(leitor);
        }

    public void cadastrarAdministrador() {
        //cadastra o leitor no sistema
        String nome, email, senha, conf_senha;
        Scanner dados = new Scanner(System.in);
        System.out.println("\n--Cadastro de Administrator--\n");
        System.out.print("Digite seu nome: ");
        nome = dados.nextLine();
        System.out.print("Digite seu email: ");
        email = dados.nextLine();
        do {//confirmação da senha
            System.out.print("Digite a senha: ");
            senha = dados.nextLine();

            System.out.print("Confirme a senha: ");
            conf_senha = dados.nextLine();

            if (!senha.equals(conf_senha)) {
                System.out.println("\n*** Confirmação inválida: as senhas não correspondem. ***\n");
            }
        } while (!senha.equals(conf_senha));

        System.out.println("\nAdministrador cadastrado com sucesso!");

        Administrador adm = new Administrador(nome, email, senha);

        adm.mostrarUsuario();
        administradores.add(adm);
    }

    public void listarLeitor(){
        System.out.println("\n--Lista de Leitores--");

        if(leitores.isEmpty()){
            System.out.println("\n-!Não a leitores cadastrados!-");
        } else {
            for (Leitor leitor : leitores) {
                leitor.mostrarUsuario();
            }
        }
    }

    public void listarAdministradores(){
        System.out.println("\n--Lista de Administradores--");

        if(administradores.isEmpty()){
            System.out.println("\n-!Não a Administradores cadastrados!-");
        } else {
            for (Administrador adm : administradores) {
                adm.mostrarUsuario();
            }
        }
    }

    public void removerAdm(){
        Scanner dados = new Scanner(System.in);

        System.out.println("\n--Remover Administrador--\n");

        if(administradores.isEmpty()) return;

        System.out.print("Digite o id do administrador: ");
        String idAdm = dados.nextLine();

        Optional<Administrador> administrador = administradores.stream()
                .filter(a -> a.getId().equals(idAdm))
                .findFirst();

        if (administrador.isEmpty()) {
            System.out.println("Administrador não encontrado!");
            return;
        }

        Administrador adm = administrador.get();
        adm.mostrarUsuario(); // mostra quem vai ser removido
        administradores.remove(adm);

        System.out.println("Administrador removido com sucesso!");
    }

    public void removerLeitor(){
        Leitor leitor;
        String idUsuario;
        Scanner dados = new Scanner(System.in);

        System.out.println("\n--Remover Leitor--\n");

        if(leitores.isEmpty()) return;

        System.out.print("Digite id do leitor:: ");
        idUsuario = dados.nextLine();

        Optional<Leitor> leitorList = leitores.stream().filter(l -> l.getId().equals(idUsuario)).findFirst();

        if (leitorList.isPresent()) {
            leitor = leitorList.get();

            leitor.mostrarUsuario();
            leitor.mostrarEmprestimos();
            leitor.mostrarReservas();

            //Liberar os livros reservados pelo leitor

            leitor.getLivrosReservados().stream().filter(reserva -> reserva.getStatusReserva() ==statusReserva.Ativa
            || reserva.getStatusReserva() ==statusReserva.Confirmada).forEach(reserva -> {
                reserva.setStatusReserva(statusReserva.Cancelada);
                reserva.getLivroReservado().setStatus(StatusLivro.Disponivel);
            });

            //Liberar os livros emprestados para o leitor

            leitor.getHistoricoEmprestimo().stream().filter(emprestimo -> emprestimo.getLivroEmprestado().getStatus() == StatusLivro.Emprestado)
            .forEach(emprestimo -> {
            emprestimo.getLivroEmprestado().setStatus(StatusLivro.Disponivel);
            });

            leitores.remove(leitor);

            System.out.println("\n***Leitor removido e todos os livros foram liberados!***\n");
        }
        else{
            System.out.println("Leitor não encontrado!");
        }
    }

    public void historicoLeitor(){
        Scanner dados = new Scanner(System.in);

        System.out.println("--Histórico do Leitor--\n");
        System.out.print("Digite o id do leitor: ");
        String idLeitor = dados.nextLine();

        Optional<Leitor> leitorOptional = leitores.stream().filter(l -> l.getId().equals(idLeitor)).findFirst();

        if (leitorOptional.isPresent()) {
            Leitor leitor = leitorOptional.get();
            leitor.mostrarEmprestimos();
            leitor.mostrarReservas();
        }
        else{
            System.out.println("Leitor não encontrado!");
        }
    }

    public void cadastrarLivro(){ //efetua a ação de criar o livro solicitada pelo adm
        String  titulo, autor, genero;
        int anoPublicacao;
        StatusLivro status;
        Scanner dados = new Scanner(System.in);

        System.out.println("\n--Cadastro de Livro--\n");

        System.out.print("Titulo: ");
        titulo = dados.nextLine();
        System.out.print("Autor: ");
        autor = dados.nextLine();
        System.out.print("Gênero: ");
        genero = dados.nextLine();
        System.out.print("Ano de Publicação: ");
        anoPublicacao = dados.nextInt();
        dados.nextLine();

        Livro novo = new Livro(titulo, autor, anoPublicacao, genero, StatusLivro.Disponivel); //cria o livro
        acervo.add(novo); //adiciona o livro no acervo

        System.out.println("\nLivro criado com sucesso!");
    }

    public void removerLivro(){ //remove o livro do acervo
        Scanner dados = new Scanner(System.in);

        System.out.println("\n--Apagar Livro--\n");

        this.mostrarAcervo();

        if (acervo.isEmpty()){
            System.out.println("\n--O acervo está vazio no momento--");
            return;
        }

        System.out.print("Qual o Id do livro que deseja apagar? ");
        String IdparaApagar = dados.nextLine();
        //faz uma busca no acervo procurando um livro com esse Id, o Optional é para caso não encontre ele retorn vazio
        Optional<Livro> livroParaApagar = acervo.stream().filter(Livro -> Livro.getIdUnico().equals(IdparaApagar)).findFirst();

        if(livroParaApagar.isPresent()){ //Ve se achou um livro no acervo
            Livro livro= livroParaApagar.get();
            if(livro.getStatus() == StatusLivro.Disponivel){
                acervo.remove(livro); //apaga o livro
                System.out.println("O livro " +livro.getTitulo() + " foi apagado com sucesso!");
            } else{
                System.out.println("Não foi possível apagar o livro, ele está emprestado ou reservado.");
            }
        } else{ // Se ele retornar vazio para o Optional
            System.out.println("Nenhum livro com esse Id foi encontrado.");
        }
    }

    public Livro buscarLivro(String idLivro) throws RuntimeException {

        //faz uma busca no acervo procurando um livro com esse Id, o Optional é para caso não encontre ele retorn vazio
        Optional<Livro> livroParaBuscar = acervo.stream().filter(Livro -> Livro.getIdUnico().equals(idLivro)).findFirst();

        if(livroParaBuscar.isPresent()){ //Ve se achou um livro no acervo
            return livroParaBuscar.get();
        } else{ // Se ele retornar vazio para o Optional
                throw new RuntimeException("Livro não cadastrado");
        }
    }

    public Leitor loginLeitor(String idLeitor) throws RuntimeException {

        //faz uma busca no acervo procurando o leitor com esse Id, o Optional é para caso não encontre ele retorn vazio
        Optional<Leitor> leitorParaBuscar = leitores.stream().filter(Leitor -> Leitor.getId().equals(idLeitor)).findFirst();

        if(leitorParaBuscar.isPresent()){ //Ve se achou o leitor na lista
            return leitorParaBuscar.get();
        } else{ // Se ele retornar vazio para o Optional
            throw new RuntimeException("\n-!Leitor não cadastrado!-");
        }
    }

    public Administrador loginAdministrador(String idAdministrador) throws RuntimeException {

        //faz uma busca no acervo procurando o leitor com esse Id, o Optional é para caso não encontre ele retorn vazio
        Optional<Administrador> AdministradorParaLogin = administradores.stream().filter(Administrador -> Administrador.getId().equals(idAdministrador)).findFirst();

        if(AdministradorParaLogin.isPresent()){ //Ve se achou o leitor na lista
            Administrador administrador = AdministradorParaLogin.get();

            Scanner dados = new Scanner(System.in);

            System.out.print("Digite a Senha: ");
            String senha = dados.nextLine();

            if(administrador.conferirSenha(senha)){
                System.out.println("\n-Login feito com sucesso!-");
                return administrador;
            } else{
                System.out.println("--SENHA INCORRETA! Tente novamente--");
            }
            return administrador;
        } else{ // Se ele retornar vazio para o Optional
            throw new RuntimeException("Administrador não cadastrado");
        }
    }

    public void mostrarAcervo(){  //estrutura base para mostrar o acervo
        if (acervo.isEmpty()) {
            System.out.println("\n--O acervo está vazio no momento--");
        } else{
            System.out.println("\n--Livros no acervo--");
            for(Livro livro : acervo){
                livro.mostrarLivro();
            }
            System.out.println("\n--Fim do acervo--");
        }
    }

    public List<Livro> getAcervo() {
            return acervo;
        }

    public List<Leitor> getLeitores() {
        return leitores;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }
    }