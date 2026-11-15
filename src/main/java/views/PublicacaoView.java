package views;

import models.Comentario;
import models.Publicacoes;
import java.util.List;
import java.util.Scanner;

public class PublicacaoView {
    private final Scanner scanner;
    public PublicacaoView(Scanner scanner) { this.scanner = scanner; }

    public void ListarPostagens(List<Publicacoes> publicacoes) {
        System.out.println("\n========== PUBLICAÇÕES ==========");
        if (publicacoes.isEmpty()) { System.out.println("Ainda não existem publicações."); return; }
        for (Publicacoes pub : publicacoes) System.out.println("\n" + pub + "\n----------------------------------");
    }

    public int SolicitarId() {
        System.out.print("Digite o número/ID da publicação: ");
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID inválido."); return -1; }
    }

    public String[] SolicitarNovaPublicacao() {
        System.out.println("\n--- NOVA PUBLICAÇÃO ---");
        System.out.print("Título: "); String titulo = scanner.nextLine().trim();
        System.out.println("Texto (escreva todo o conteúdo em uma linha):");
        String conteudo = scanner.nextLine().trim();
        return new String[]{titulo, conteudo};
    }

    public String[] SolicitarEdicao(Publicacoes atual) {
        System.out.println("\n--- EDITAR PUBLICAÇÃO ---");
        System.out.println("Título atual: " + atual.getTitulo());
        System.out.print("Novo título (Enter mantém): "); String titulo = scanner.nextLine().trim();
        System.out.println("Conteúdo atual: " + atual.getConteudo());
        System.out.print("Novo conteúdo (Enter mantém): "); String conteudo = scanner.nextLine().trim();
        return new String[]{titulo.isEmpty() ? atual.getTitulo() : titulo, conteudo.isEmpty() ? atual.getConteudo() : conteudo};
    }

    public String SolicitarComentario() {
        System.out.print("Digite seu comentário: "); return scanner.nextLine().trim();
    }

    public boolean ConfirmarExclusao() {
        System.out.print("Tem certeza que deseja excluir? (S/N): ");
        return scanner.nextLine().trim().equalsIgnoreCase("S");
    }

    public void ExibirPublicacao(Publicacoes pub, List<Comentario> comentarios) {
        System.out.println("\n========== PUBLICAÇÃO ==========");
        System.out.println(pub);
        System.out.println("\n---------- COMENTÁRIOS ----------");
        if (comentarios.isEmpty()) System.out.println("Ainda não há comentários.");
        else for (Comentario comentario : comentarios) System.out.println(comentario);
    }

    public void Mensagem(String mensagem) { System.out.println(mensagem); }
}
