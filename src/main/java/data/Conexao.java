package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private final Connection conexao;

    public Conexao() {
        String url = System.getenv().getOrDefault("BLOG_DB_URL", "jdbc:mariadb://localhost:3308/BlogTurma");
        String user = System.getenv().getOrDefault("BLOG_DB_USER", "root");
        String password = System.getenv().getOrDefault("BLOG_DB_PASSWORD", "root");
        try {
            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com o MariaDB realizada com sucesso!\n");
        } catch (SQLException e) {
            throw new IllegalStateException("Não foi possível conectar ao banco de dados. Confira se o MariaDB está ligado, se o banco BlogTurma existe e se a porta/credenciais estão corretas. Detalhe: " + e.getMessage(), e);
        }
    }

    public Connection getConexao() {
        return conexao;
    }
}
