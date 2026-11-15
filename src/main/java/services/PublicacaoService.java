package services;

import data.Conexao;
import models.Comentario;
import models.Publicacoes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicacaoService {
    private final Conexao con;
    public PublicacaoService(Conexao con) { this.con = con; }

    public List<Publicacoes> ListarPublicacao() {
        String sql = "SELECT p.id_publicacoes, p.titulo, p.conteudo, u.nome AS autor, DATE_FORMAT(p.data_postado, '%d/%m/%Y %H:%i') AS data_postado " +
                "FROM publicacoes p JOIN usuario u ON u.id_usuario = p.idusuario ORDER BY p.data_postado DESC, p.id_publicacoes DESC";
        List<Publicacoes> lista = new ArrayList<>();
        try (PreparedStatement stmt = con.getConexao().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapPublicacao(rs));
            return lista;
        } catch (SQLException e) { throw new RuntimeException("Erro ao listar publicações: " + e.getMessage(), e); }
    }

    public Publicacoes BuscarPorId(int id) {
        String sql = "SELECT p.id_publicacoes, p.titulo, p.conteudo, u.nome AS autor, DATE_FORMAT(p.data_postado, '%d/%m/%Y %H:%i') AS data_postado FROM publicacoes p JOIN usuario u ON u.id_usuario = p.idusuario WHERE p.id_publicacoes = ?";
        try (PreparedStatement stmt = con.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) { return rs.next() ? mapPublicacao(rs) : null; }
        } catch (SQLException e) { throw new RuntimeException("Erro ao buscar publicação: " + e.getMessage(), e); }
    }

    public boolean Criar(String titulo, String conteudo, String emailAutor) {
        String sql = "INSERT INTO publicacoes (titulo, conteudo, idusuario) SELECT ?, ?, id_usuario FROM usuario WHERE email = ?";
        try (PreparedStatement stmt = con.getConexao().prepareStatement(sql)) {
            stmt.setString(1, titulo.trim()); stmt.setString(2, conteudo.trim()); stmt.setString(3, emailAutor);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) { throw new RuntimeException("Erro ao criar publicação: " + e.getMessage(), e); }
    }

    public boolean Editar(int id, String titulo, String conteudo, String emailAutor) {
        String sql = "UPDATE publicacoes p JOIN usuario u ON u.id_usuario = p.idusuario SET p.titulo = ?, p.conteudo = ? WHERE p.id_publicacoes = ? AND u.email = ?";
        try (PreparedStatement stmt = con.getConexao().prepareStatement(sql)) {
            stmt.setString(1, titulo.trim()); stmt.setString(2, conteudo.trim()); stmt.setInt(3, id); stmt.setString(4, emailAutor);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) { throw new RuntimeException("Erro ao editar publicação: " + e.getMessage(), e); }
    }

    public boolean Excluir(int id, String emailAutor) {
        Connection conn = con.getConexao();
        String verifica = "SELECT p.id_publicacoes FROM publicacoes p JOIN usuario u ON u.id_usuario = p.idusuario WHERE p.id_publicacoes = ? AND u.email = ?";
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement check = conn.prepareStatement(verifica)) {
                check.setInt(1, id); check.setString(2, emailAutor);
                try (ResultSet rs = check.executeQuery()) {
                    if (!rs.next()) { conn.rollback(); return false; }
                }
            }
            try (PreparedStatement comentarios = conn.prepareStatement("DELETE FROM comentario WHERE idpublicacoes = ?")) {
                comentarios.setInt(1, id); comentarios.executeUpdate();
            }
            try (PreparedStatement postagem = conn.prepareStatement("DELETE FROM publicacoes WHERE id_publicacoes = ?")) {
                postagem.setInt(1, id);
                boolean removida = postagem.executeUpdate() == 1;
                conn.commit();
                return removida;
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignored) { }
            throw new RuntimeException("Erro ao excluir publicação: " + e.getMessage(), e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) { }
        }
    }

    public boolean AdicionarComentario(int idPublicacao, String texto, String emailAutor) {
        String sql = "INSERT INTO comentario (texto, idpublicacoes, idusuario) SELECT ?, ?, id_usuario FROM usuario WHERE email = ? AND EXISTS (SELECT 1 FROM publicacoes WHERE id_publicacoes = ?)";
        try (PreparedStatement stmt = con.getConexao().prepareStatement(sql)) {
            stmt.setString(1, texto.trim()); stmt.setInt(2, idPublicacao); stmt.setString(3, emailAutor); stmt.setInt(4, idPublicacao);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) { throw new RuntimeException("Erro ao adicionar comentário: " + e.getMessage(), e); }
    }

    public List<Comentario> ListarComentarios(int idPublicacao) {
        String sql = "SELECT c.id_comentario, c.texto, u.nome AS autor, DATE_FORMAT(c.data_postagem, '%d/%m/%Y %H:%i') AS data_postagem FROM comentario c JOIN usuario u ON u.id_usuario = c.idusuario WHERE c.idpublicacoes = ? ORDER BY c.data_postagem ASC, c.id_comentario ASC";
        List<Comentario> lista = new ArrayList<>();
        try (PreparedStatement stmt = con.getConexao().prepareStatement(sql)) {
            stmt.setInt(1, idPublicacao);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(new Comentario(rs.getInt("id_comentario"), rs.getString("texto"), rs.getString("autor"), rs.getString("data_postagem")));
            }
            return lista;
        } catch (SQLException e) { throw new RuntimeException("Erro ao listar comentários: " + e.getMessage(), e); }
    }

    private Publicacoes mapPublicacao(ResultSet rs) throws SQLException {
        return new Publicacoes(rs.getInt("id_publicacoes"), rs.getString("titulo"), rs.getString("conteudo"), rs.getString("autor"), rs.getString("data_postado"));
    }
}
