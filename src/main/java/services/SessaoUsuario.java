package services;

public class SessaoUsuario {

    private static String usuarioEmail;
    private static String nomeUsuario;
    private static boolean estaAutenticado = false;

    public static void iniciar(String email, String nome) {
        usuarioEmail = email;
        nomeUsuario = nome;
        estaAutenticado = true;
    }

    public static void encerrar() {
        usuarioEmail = null;
        nomeUsuario = null;
        estaAutenticado = false;
    }

    public static boolean estaAutenticado() {
        return estaAutenticado;
    }

    public static String getUsuarioEmail() {
        return usuarioEmail;
    }

    public static String getNomeUsuario() {
        return nomeUsuario;
    }
}