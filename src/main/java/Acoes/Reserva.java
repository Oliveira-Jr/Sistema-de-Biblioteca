package Acoes;

import Livros.Livro;
import Livros.StatusLivro;

import java.time.LocalDate;

public class Reserva{
    private static int contador=0;
    private Livro livroReservado;
    private String idReserva;
    private LocalDate dataReserva;
    private int tempoParaBuscar;
    private statusReserva statusReserva;

    public Reserva(Livro livroReservado, int tempoParaBuscar){
        contador++;
        idReserva = "res-" + contador;
        this.livroReservado = livroReservado;
        dataReserva = LocalDate.now();
        this.tempoParaBuscar = tempoParaBuscar;
        statusReserva = Acoes.statusReserva.Ativa;
        livroReservado.setStatus(StatusLivro.Reservado);
    }

    public void mostrarReserva(){
        System.out.println("\nCódigo: " + idReserva);
        System.out.println("Livro: " + livroReservado.getTitulo());
        System.out.println("Data da Reserva: " + dataReserva.toString());
        if(statusReserva == Acoes.statusReserva.Ativa) {
            System.out.println("Status: Ativa");
        } else if (statusReserva == Acoes.statusReserva.Confirmada) {
            System.out.println("Status: Coletada");
        } else if (statusReserva == Acoes.statusReserva.Cancelada) {
            System.out.println("Status: Cancelada");
        }
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setStatusReserva(statusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }

    public statusReserva getStatusReserva() {
        return statusReserva;
    }

    public Livro getLivroReservado() {
        return livroReservado;
    }

    public static int getContador() {
        return contador;
    }
}
