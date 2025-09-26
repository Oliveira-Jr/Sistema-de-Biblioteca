package Usuario;
import Acoes.statusReserva;
import Livros.*;
import Biblioteca.*;
import java.util.Scanner;

//todo criar metodos gerenciarUsuario

public class Administrador extends Usuario{

    private String senha, codigo;
    private static int contador=0;

    public Administrador(String nome, String email, String senha) {
        super( nome, email);
        this.senha = senha;
        contador++;
        codigo = "adm-" + contador;
    }

    public Administrador() {
    }

    public String getId(){
        return codigo;
    }

    @Override
    public void mostrarUsuario() {
        System.out.println("\n---------------------------");
        System.out.println("Código: " + this.codigo);
        System.out.println("Nome: " + this.getNome());
        System.out.println("Email: " + this.getEmail());
        System.out.println("---------------------------");

    }

    //Chama o metodo cadastrarLivro da biblioteca, serve para assegurar que apenas administradores consigam
    public void cadastrarLivro(Biblioteca biblioteca){
        biblioteca.cadastrarLivro();
    }

    //Chama o metodo removerLivro da biblioteca, serve para assegurar que apenas administradores consigam
    public void removerLivro(Biblioteca biblioteca){
        biblioteca.removerLivro();
    }

    public boolean conferirSenha(String senha){
        return this.senha.equals(senha);
    }

    public void gerenciarUsuario(Biblioteca biblioteca){

        Scanner dados = new Scanner(System.in);
        int opcao = -1;

        while(opcao != 0) {
            System.out.println("\n--Gerenciar Usuários--");
            System.out.println("0-Voltar");
            System.out.println("1-Listar leitores cadastrados");
            System.out.println("2-Listar administradores cadastrados");
            System.out.println("3-Remover leitor");
            System.out.println("4-Remover administrador");
            System.out.println("5-Ver histórico de um leitor");
            System.out.print("Escolha uma opção: ");
            opcao = dados.nextInt();
            dados.nextLine();

            switch(opcao) {
                case 0:
                    System.out.println("Voltando ao menu do administrador...");
                    break;
                case 1:
                    biblioteca.listarLeitor();
                    break;
                case 2:
                    biblioteca.listarAdministradores();
                    break;
                case 3:
                    biblioteca.listarLeitor();
                    biblioteca.removerLeitor();
                    break;
                case 4:
                    biblioteca.listarAdministradores();
                    biblioteca.removerAdm();
                    break;
                case 5:
                    biblioteca.historicoLeitor();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void gerarRelatorio(Biblioteca  biblioteca){

        int disponivel = 0;
        int reservado = 0;
        int emprestado = 0;

        //percorre o  acervo e checa o status dos livros;
        for(Livro livro : biblioteca.getAcervo()){
            switch (livro.getStatus()){
                case Disponivel -> disponivel++;
                case Reservado -> reservado++;
                case Emprestado -> emprestado++;
            }
        }

        System.out.println("\n==== RELATÓRIO DA BIBLIOTECA ====");

        System.out.println("\n-- Acervo de Livros --\n");
        System.out.println("Livros Disponíveis: " + disponivel);
        System.out.println("Livros Emprestados: " + emprestado);
        System.out.println("Livros Reservados: " +  reservado);

        System.out.println("\n-- Leitores --\n");
        if(biblioteca.getLeitores().isEmpty()){
            System.out.println("Não há leitores cadastrados!");
        }
        else {
            //Percorre todos os leitores consultando seus atributos
            for (Leitor leitor : biblioteca.getLeitores()) {
                System.out.println("Nome: " + leitor.getNome() + " | Empréstimos: " + leitor.getHistoricoEmprestimo().stream().filter(emp -> emp.getLivroEmprestado().getStatus() == StatusLivro.Emprestado).count()
                        + " | Reservas ativas: " + leitor.getLivrosReservados().stream().filter(res -> res.getStatusReserva() == statusReserva.Ativa).count());
            }
        }
        System.out.println("\n-- Administradores --\n");
        if(biblioteca.getAdministradores().isEmpty()){
            System.out.println("Não há administradores cadastrados!");
        }
        else {
            //Percorre todos os administradores consultando seus atributos
            for (Administrador administrador : biblioteca.getAdministradores()) {
                System.out.println("Nome: " + administrador.getNome() + " | Email: " + administrador.getEmail());
            }
        }
        System.out.println("\n==== FIM RELATÓRIO ====");
    }
}