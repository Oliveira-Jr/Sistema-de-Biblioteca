package Acoes;

import Livros.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

//todo adicionar calcularMulta
public class Emprestimo{
    private static int contador=0;
    private Livro livroEmprestado;
    private String id;
    private LocalDate dataEmprestimo;
    private int prazoDevolucao;
    private LocalDate dataDevolucao;
    private LocalDate dataEfetivaDevolucao;
    private statusReserva statusReserva;

    public Emprestimo(Livro livroEmprestado, int prazoDevolucao) { //inicia os dados do emprestimo com as datas atuais

        contador++;
        id = "emp-" + contador;
        dataEmprestimo = LocalDate.now();
        this.livroEmprestado = livroEmprestado;
        this.prazoDevolucao = prazoDevolucao;
        dataDevolucao = dataEmprestimo.plusDays(prazoDevolucao);
        livroEmprestado.setStatus(StatusLivro.Emprestado);
        statusReserva = Acoes.statusReserva.Ativa;
    }

    public float calcularMulta(LocalDate dataEntrega){

        long diasAtraso = ChronoUnit.DAYS.between(dataDevolucao, dataEntrega);


        if (diasAtraso <= 0) {
            return 0f; // sem multa
        }
        //usei final para ser alo imutavel;

        final float TAXA_POR_DIA = 0.3f;
        final float VALOR_BASE = 20f;


        return VALOR_BASE * TAXA_POR_DIA * diasAtraso;
    }

    public void mostrarEmprestimo(){
        System.out.println("\nCódigo: " + id);
        System.out.println("Livro: " + livroEmprestado.getTitulo());
        System.out.println("Data de emprestimo: " + dataEmprestimo.toString());
        if(statusReserva == Acoes.statusReserva.Ativa) {
            System.out.println("Data para devolução: " + dataDevolucao.toString());
            System.out.println("Status: ativo");
        } else if (statusReserva == Acoes.statusReserva.Confirmada) {
            System.out.println("Devolvido em " + getDataEfetivaDevolucao());
            System.out.println("Status: devolvido");
        }
    }

    public void setStatusReserva(statusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }

    public Livro getLivroEmprestado() {
        return livroEmprestado;
    }

    public statusReserva getStatusReserva() {
        return statusReserva;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public LocalDate getDataEfetivaDevolucao() {
        return dataEfetivaDevolucao;
    }

    public String getId() {
        return id;
    }

    public void setDataEfetivaDevolucao(LocalDate dataEfetivaDevolucao) {
        this.dataEfetivaDevolucao = dataEfetivaDevolucao;
    }
}


