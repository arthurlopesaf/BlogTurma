package org.example;

import controllers.AutenticacaoController;
import controllers.PublicacaoController;
import data.Conexao;
import services.*;
import views.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Conexao conexao = new Conexao();
            MenuView menu = new MenuView(scanner);
            PublicacaoView pubView = new PublicacaoView(scanner);
            AutenticacaoView authView = new AutenticacaoView(scanner);
            SenhaService senhaService = new SenhaService();
            UsuarioService usuarioService = new UsuarioService(conexao, senhaService);
            PublicacaoService pubService = new PublicacaoService(conexao);
            AutenticacaoController authController = new AutenticacaoController(authView, usuarioService);
            PublicacaoController pubController = new PublicacaoController(pubService, pubView);

            boolean executando = true;
            while (executando) {
                if (SessaoUsuario.estaAutenticado())
                    System.out.println("\nConectado como: " + SessaoUsuario.getNomeUsuario());
                int opcao = menu.mostrarMenu();
                try {
                    switch (opcao) {
                        case 1 -> authController.cadastro();
                        case 2 -> authController.autenticar();
                        case 3 -> pubController.ListarPostagens();
                        case 4 -> { if (exigirLogin()) pubController.Visualizar(); }
                        case 5 -> { if (exigirLogin()) pubController.Criar(); }
                        case 6 -> { if (exigirLogin()) pubController.Comentar(); }
                        case 7 -> { if (exigirLogin()) pubController.Editar(); }
                        case 8 -> { if (exigirLogin()) pubController.Excluir(); }
                        case 9 -> {
                            if (SessaoUsuario.estaAutenticado()) { SessaoUsuario.encerrar(); System.out.println("Sessão encerrada."); }
                            else System.out.println("Você não está conectado.");
                        }
                        case 0 -> { SessaoUsuario.encerrar(); executando = false; System.out.println("Obrigado por utilizar o Blog da Turma!"); }
                        default -> System.out.println("Opção inválida. Digite um número de 0 a 9.");
                    }
                } catch (RuntimeException e) {
                    System.out.println("Ocorreu um erro: " + e.getMessage());
                }
            }
            try { conexao.getConexao().close(); } catch (Exception ignored) { }
        } catch (RuntimeException e) {
            System.err.println("Não foi possível iniciar o Blog da Turma: " + e.getMessage());
        }
    }

    private static boolean exigirLogin() {
        if (!SessaoUsuario.estaAutenticado()) {
            System.out.println("Faça login antes de utilizar essa funcionalidade.");
            return false;
        }
        return true;
    }
}
