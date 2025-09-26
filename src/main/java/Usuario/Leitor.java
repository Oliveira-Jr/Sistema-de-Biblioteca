package Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Acoes.*;
import Biblioteca.Biblioteca;
import Livros.Livro;
import Livros.StatusLivro;

public class Leitor extends Usuario {

    private List<Emprestimo> historicoEmprestimo = new ArrayList<>();
    private List<Reserva> livrosReservados = new ArrayList<>();
    private static int contador=0;
    private String idLeitor;

    public Leitor() {
    }

    public Leitor(String nome, String email) {
        super(nome, email);
        contador++;
        idLeitor = "user-" + contador;
    }

    @Override
    public void mostrarUsuario(){
        System.out.println("\n---------------------------");
        System.out.println("Id: " + idLeitor);
        System.out.println("Nome: " + this.getNome());
        System.out.println("Email: " + this.getEmail());
        System.out.println("---------------------------");

    }

    public void realizarEmprestimo(Biblioteca biblioteca) {
        Scanner dados = new Scanner(System.in);
        Livro livro;

        System.out.println("\n--Realizar emprestimo de livro--");

        biblioteca.mostrarAcervo();
        if(biblioteca.getAcervo().isEmpty()) return;

        System.out.print("Qual livro deseja pegar emprestado(Id)? ");
        String idLivro = dados.nextLine();

        try {
            livro = biblioteca.buscarLivro(idLivro);
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        // se o livro estiver disponível -> empresta direto
        if (livro.getStatus() == StatusLivro.Disponivel) {
            System.out.print("Por quantos dias deseja pegar o livro? ");
            int prazoDevolucao = dados.nextInt();
            dados.nextLine();

            Emprestimo emprestimo = new Emprestimo(livro, prazoDevolucao);
            historicoEmprestimo.add(emprestimo);
            livro.setStatus(StatusLivro.Emprestado);

            System.out.println("Emprestimo realizado com sucesso!");
            return;
        }

        // se o livro está reservado, conferir se a reserva pertence a este leitor
        Optional<Reserva> minhaReserva = livrosReservados.stream()
                .filter(reserva -> reserva.getLivroReservado().getIdUnico().equals(livro.getIdUnico())
                        && reserva.getStatusReserva() == statusReserva.Ativa)
                .findFirst();

        if (minhaReserva.isPresent()) {
            System.out.print("Por quantos dias deseja pegar o livro? ");
            int prazoDevolucao = dados.nextInt();
            dados.nextLine();

            Emprestimo emprestimo = new Emprestimo(livro, prazoDevolucao);
            historicoEmprestimo.add(emprestimo);
            livro.setStatus(StatusLivro.Emprestado);

            // atualiza a reserva como confirmada
            minhaReserva.get().setStatusReserva(statusReserva.Confirmada);

            System.out.println("Você tinha uma reserva, agora ela virou empréstimo!");
        } else {
            System.out.println("O livro não está disponível para você no momento.");
        }
    }

    public void devolverEmprestimo(Biblioteca biblioteca){
        Scanner dados = new Scanner(System.in);
        LocalDate dataDevol = LocalDate.now(); //data de entrega;
        Livro livro;

        System.out.println("\n--Devolver emprestimo de livro--");

        mostrarEmprestimos();
        if(historicoEmprestimo.stream().filter(Emprestimo -> Emprestimo.getStatusReserva()!=statusReserva.Confirmada).findFirst().isEmpty()){
            return;
        }

        System.out.print("\nQual emprestimo deseja devolver?(Id) ");
        String idEmprestimo = dados.nextLine();
        //procura no historico do leitor o Id do livro;
        Optional<Emprestimo> emprestimoLeitor = historicoEmprestimo.stream().filter(Emprestimo -> Emprestimo.getId().equals(idEmprestimo)).findFirst();
        //caso nao encontre;
        if(emprestimoLeitor.isEmpty()){
            System.out.println("Empréstimo não encontrado!");
            return;
        }
        Emprestimo emprestimo = emprestimoLeitor.get();

        if(emprestimo.getStatusReserva().equals(statusReserva.Confirmada)){
            System.out.println("\n-Esse emprestimo já foi devolvido-");
            return;
        }
        emprestimo.setDataEfetivaDevolucao(dataDevol);

        livro = emprestimoLeitor.get().getLivroEmprestado();
        //chama a função de multa
        //adicionei a data que devolveu no emprestimo
        if(dataDevol.isAfter(emprestimo.getDataDevolucao())){
            System.out.println( "Você tem um taxa aplicada sobre atraso.");
            float valor = emprestimoLeitor.get().calcularMulta(dataDevol);
            System.out.println("Valor: " + valor);
            emprestimo.setStatusReserva(statusReserva.Confirmada);
        }

        System.out.println("Livro devolvido com sucesso!");

        livro.setStatus(StatusLivro.Disponivel);
    }

    public void fazerReserva(Biblioteca biblioteca) {
        Scanner dados = new Scanner(System.in);
        Livro livro;

        System.out.println("\n--Realizar reserva de livro--");

        biblioteca.mostrarAcervo();
        if(biblioteca.getAcervo().isEmpty()) return;

        System.out.print("\nQual livro deseja reservar?(Id) ");
        String idLivro = dados.nextLine();

        try{
            livro = biblioteca.buscarLivro(idLivro);
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }
        //verefica nos livros reservados por meio do id, se esse livro ja foi reservado pelo leitor;
        //confere se o status de reserva está ativa;
        Optional<Reserva> livroReserva = livrosReservados.stream().filter(reserva -> reserva.getLivroReservado().getIdUnico().equals(livro.getIdUnico()) && reserva.getStatusReserva() == statusReserva.Ativa).findFirst();

        if(livroReserva.isPresent()){ //caso encontre o livro no FindFirst
            System.out.println("\nVoce ja reservou esse livro!");
            return;
        }

        if(livro.getStatus() !=  StatusLivro.Disponivel){
            System.out.println("\nO livro não está disponível para reserva");
        }else {

            System.out.print("Por quantos dias deseja reservar? ");
            int tempoParaBuscar = dados.nextInt();
            dados.nextLine();

            Reserva reservado = new Reserva(livro, tempoParaBuscar);

            livrosReservados.add(reservado);

            System.out.println("Reserva realizada com sucesso!");

        }
    }

    public void cancelarReserva(Biblioteca biblioteca) {

        if(livrosReservados.isEmpty()){
            System.out.println("\n-!O usuário não tem reservas feitas!-");
            return;
        }

        Scanner dados = new Scanner(System.in);

        System.out.println("\n--Cancelar reserva--\n");

        mostrarReservas();

        System.out.print("Digite o código da reserva:(Id) ");
        String idReserva = dados.nextLine();

        //confere se o livro está reservado
        Optional<Reserva> buscaReserva = livrosReservados.stream()
                .filter(reserva -> reserva.getIdReserva().equals(idReserva))
                .findFirst();

        if (buscaReserva.isEmpty()) {
            System.out.println("Reserva não encontrada!");
            return;
        }

        Reserva reserva = buscaReserva.get();

        if (reserva.getStatusReserva() == statusReserva.Cancelada) {
            System.out.println("Essa reserva já foi cancelada anteriormente!");
            return;
        }

        if (reserva.getStatusReserva() == statusReserva.Confirmada) {
            System.out.println("Essa reserva já foi coletada, não pode ser cancelada!");
            return;
        }

        // Cancela a reserva
        reserva.setStatusReserva(statusReserva.Cancelada);

        reserva.getLivroReservado().setStatus(StatusLivro.Disponivel);
        System.out.println("Reserva cancelada! O livro agora está disponível.");

    }

    public void pegarReserva(Biblioteca biblioteca){
        Scanner dados = new Scanner(System.in);

        if(livrosReservados.isEmpty()){
            System.out.println("\n-!O usuário não tem reservas feitas!-");
            return;
        }

        System.out.println("\n--Pegar livro reservado--\n");

        mostrarReservas();

        System.out.print("Digite o código da reserva: ");
        String idReserva= dados.nextLine();

        Optional<Reserva> buscaReserva = livrosReservados.stream().filter(Reserva -> Reserva.getIdReserva().equals(idReserva)).findFirst();

        if(buscaReserva.isPresent()){
            Reserva reserva = buscaReserva.get();

            if(reserva.getStatusReserva() == statusReserva.Cancelada){
                System.out.println("\n-!Não é possível pegar uma reserva cancelada!-");
                return;
            }
            reserva.setStatusReserva(statusReserva.Confirmada);

            System.out.print("Por quantos dias deseja pegar o livro? ");
            int prazoDevolucao = dados.nextInt();
            dados.nextLine();

            Emprestimo emprestimo = new Emprestimo(reserva.getLivroReservado(), prazoDevolucao); //Cria o emprestimo
            historicoEmprestimo.add(emprestimo);

            System.out.println("Reserva coletada com sucesso! Agora você tem um emprestimo!");
        } else{
            System.out.println("Reserva inexistente!");
        }
    }

    public void mostrarEmprestimos(){
        System.out.println("\n-Histórico de Empréstimos-\n");
        if (historicoEmprestimo.isEmpty()) {
            System.out.println("\n-O usuário não tem empréstimos feitos-\n");
        } else{
            for(Emprestimo emprestimo : historicoEmprestimo){
                emprestimo.mostrarEmprestimo();
            }
            System.out.println("\n-Fim dos Empréstimos-");
        }
    }

    public void mostrarReservas(){
        if (livrosReservados.isEmpty()) {
            System.out.println("\n-O usuário não tem reservas feitas-");
        } else{
            System.out.println("-Histórico de Reservas-");
            for(Reserva reserva : livrosReservados){
                reserva.mostrarReserva();
            }
            System.out.println("\n-Fim das Reservas-");
        }
    }

    public List<Emprestimo> getHistoricoEmprestimo() {
        return historicoEmprestimo;
    }

    public List<Reserva> getLivrosReservados() {
        return livrosReservados;
    }

    public String getId(){
        return this.idLeitor;
    }
}

