package services;

import data.Conexao;
import models.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioService {
    private final Conexao con;
    private final SenhaService senhaService;

    public UsuarioService(Conexao con, SenhaService senhaService) {
        this.con = con;
        this.senhaService = senhaService;
    }

    public boolean Cadastrar(String email, String nome, String senha) {
        if (email == null || nome == null || senha == null || email.isBlank() || nome.isBlank() || senha.length() < 6 || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) return false;
        String sql = "INSERT INTO usuario (email, nome, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.getConexao().prepareStatement(sql)) {
            stmt.setString(1, email.trim().toLowerCase());
            stmt.setString(2, nome.trim());
            stmt.setString(3, senhaService.GerarHash(senha));
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) return false; // e-mail já cadastrado
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage(), e);
        }
    }

    public Usuario Autenticar(String email, String senhaDigitada) {
        String sql = "SELECT nome, email, senha FROM usuario WHERE email = ?";
        try (PreparedStatement stmt = con.getConexao().prepareStatement(sql)) {
            stmt.setString(1, email.trim().toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && senhaService.Verificar(senhaDigitada, rs.getString("senha")))
                    return new Usuario(rs.getString("nome"), null, rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário: " + e.getMessage(), e);
        }
        return null;
    }
}
