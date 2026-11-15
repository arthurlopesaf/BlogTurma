package views;

import java.util.Scanner;

public class AutenticacaoView {
    private final Scanner scanner;
    public AutenticacaoView(Scanner scanner) { this.scanner = scanner; }

    public record DadosCadastro(String email, String nome, String senha) {}
    public record DadosLogin(String email, String senha) {}

    public DadosCadastro SolicitarDadosCadastro() {
        System.out.println("\n--- CRIAR CONTA ---");
        System.out.print("E-mail: "); String email = scanner.nextLine().trim();
        System.out.print("Nome: "); String nome = scanner.nextLine().trim();
        System.out.print("Senha (mínimo 6 caracteres): "); String senha = scanner.nextLine();
        return new DadosCadastro(email, nome, senha);
    }

    public DadosLogin Solicitar() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("E-mail: "); String email = scanner.nextLine().trim();
        System.out.print("Senha: "); String senha = scanner.nextLine();
        return new DadosLogin(email, senha);
    }

    public void ExibirMensagem(String mensagem) { System.out.println(mensagem); }
}
