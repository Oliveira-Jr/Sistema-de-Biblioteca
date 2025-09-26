import Biblioteca.Biblioteca;
import Usuario.Administrador;
import Usuario.Leitor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner dados = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        System.out.println("---INICIO DA BIBLIOTECA---");

        System.out.println("Seja bem vindo ao sistema de gerenciamento de biblioteca!");

        int opcao = -1;

        System.out.println("---Antes de acessar o menu adicione um Administrador---");
        biblioteca.cadastrarAdministrador();

        while(opcao!=0){
            System.out.println("\n---Menu da Biblioteca---");
            System.out.println("0-Encerrar programa");
            System.out.println("1-Cadastrar administrador");
            System.out.println("2-Fazer login como administrador");
            System.out.println("3-Cadastrar novo usuário");
            System.out.println("4-Fazer login como usuário");
            System.out.println("5-Mostrar acervo");
            System.out.print("Escolha uma das opções: ");
            try{
                opcao = dados.nextInt();
            } catch (InputMismatchException e) {
                opcao = -1;
            }
            dados.nextLine();

            switch (opcao){
                case -1:
                    System.out.println("\n---!!!DIGITE APENAS NÚMEROS!!!---");
                    break;
                case 0:
                    System.out.println("\n---O programa está sendo encerrado---");
                    break;
                case 1:
                    biblioteca.cadastrarAdministrador();
                    break;
                case 2:
                    System.out.println("\n--Login do Administrador--");
                    System.out.print("Digite o Id do usuário: ");
                    String idAdm = dados.nextLine();

                    Administrador administrador;

                    try{
                        administrador = biblioteca.loginAdministrador(idAdm);
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                        break;
                    }

                    int opcaoAdm = -1;

                    while(opcaoAdm !=0) {
                        System.out.println("\n--Menu do Administrador--");
                        System.out.println("0-Encerrar sessão");
                        System.out.println("1-Mostrar dados do usuário");
                        System.out.println("2-Fazer cadastro de livro");
                        System.out.println("3-Remover livro");
                        System.out.println("4-Gerenciar usuário");
                        System.out.println("5-Gerar relatório");
                        System.out.print("Escolha uma das opções: ");
                        try{
                            opcaoAdm = dados.nextInt();
                        } catch (InputMismatchException e) {
                            opcaoAdm = -1;
                        }
                        dados.nextLine();

                        switch (opcaoAdm) {
                            case -1:
                                System.out.println("\n---!!!DIGITE APENAS NÚMEROS!!!---");
                                break;
                            case 0:
                                System.out.println("\n--Desconectando usuário--");
                                break;
                            case 1:
                                administrador.mostrarUsuario();
                                break;
                            case 2:
                                administrador.cadastrarLivro(biblioteca);
                                break;
                            case 3:
                                administrador.removerLivro(biblioteca);
                                break;
                            case 4:
                                administrador.gerenciarUsuario(biblioteca);
                                break;
                            case 5:
                                administrador.gerarRelatorio(biblioteca);
                                break;
                            default:
                                System.out.println("\n---OPÇÃO INVALIDA---\n");
                                break;
                        }
                    }
                    break;
                case 3:
                    biblioteca.cadastrarLeitor();
                    break;
                case 4:
                    System.out.println("\n--Login do leitor--");
                    System.out.print("Digite o Id do usuário: ");
                    String idLeitor = dados.nextLine();

                    Leitor leitor;
                try{
                    leitor = biblioteca.loginLeitor(idLeitor);
                } catch (RuntimeException e){
                    System.out.println(e.getMessage());
                    break;
                }
                    int opcaoLeitor = -1;

                    while(opcaoLeitor!=0){
                        System.out.println("\n--Menu do Leitor--");
                        System.out.println("0-Encerrar sessão");
                        System.out.println("1-Mostrar dados do usuário");
                        System.out.println("2-Fazer reserva de livro");
                        System.out.println("3-Cancelar reserva");
                        System.out.println("4-Pegar reserva feita");
                        System.out.println("5-Fazer empréstimo de livro");
                        System.out.println("6-Devolver empréstimo");
                        System.out.println("7-Mostrar reservas");
                        System.out.println("8-Mostrar empréstimos");
                        System.out.print("Escolha uma das opções: ");

                        try{
                            opcaoLeitor = dados.nextInt();
                        } catch (InputMismatchException e) {
                            opcaoLeitor = -1;
                        }
                        dados.nextLine();

                        switch(opcaoLeitor){
                            case -1:
                                System.out.println("\n---!!!DIGITE APENAS NÚMEROS!!!---");
                                break;
                            case 0:
                                System.out.println("--Desconectando usuário--");
                                break;
                            case 1:
                                leitor.mostrarUsuario();
                                break;
                            case 2:
                                leitor.fazerReserva(biblioteca);
                                break;
                            case 3:
                                leitor.cancelarReserva(biblioteca);
                                break;
                            case 4:
                                leitor.pegarReserva(biblioteca);
                                break;
                            case 5:
                                leitor.realizarEmprestimo(biblioteca);
                                break;
                            case 6:
                                leitor.devolverEmprestimo(biblioteca);
                                break;
                            case 7:
                                leitor.mostrarReservas();
                                break;
                            case 8:
                                leitor.mostrarEmprestimos();
                                break;
                            default:
                                System.out.println("\n---OPÇÃO INVALIDA---\n");
                                break;
                        }
                    }
                    break;
                case 5:
                    biblioteca.mostrarAcervo();
                    break;
                default:
                    System.out.println("\n---OPÇÃO INVALIDA---\n");
            }
        }
    }
}
