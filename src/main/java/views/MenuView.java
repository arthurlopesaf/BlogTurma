package views;

import java.util.Scanner;

public class MenuView {
    private final Scanner scanner;
    public MenuView(Scanner scanner) { this.scanner = scanner; }

    public int mostrarMenu() {
        System.out.println("""
                \n========== BLOG DA TURMA ==========
                1 - Criar conta
                2 - Login
                3 - Listar publicações
                4 - Visualizar publicação e comentários
                5 - Publicar texto
                6 - Comentar em uma postagem
                7 - Editar publicação (somente suas)
                8 - Excluir publicação (somente suas)
                9 - Sair da conta
                0 - Encerrar programa
                ==================================
                """);
        System.out.print("Escolha uma opção: ");
        String entrada = scanner.nextLine().trim();
        try { return Integer.parseInt(entrada); }
        catch (NumberFormatException e) { return -1; }
    }
}
