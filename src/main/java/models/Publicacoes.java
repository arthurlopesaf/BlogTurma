package models;

public class Publicacoes {
    private final int id;
    private final String titulo;
    private final String conteudo;
    private final String autor;
    private final String dataPostado;

    public Publicacoes(int id, String titulo, String conteudo, String autor, String dataPostado) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.autor = autor;
        this.dataPostado = dataPostado;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getConteudo() { return conteudo; }
    public String getAutor() { return autor; }
    public String getDataPostado() { return dataPostado; }

    @Override
    public String toString() {
        return "[" + id + "] " + titulo + "\nPor: " + autor + " | Em: " + dataPostado + "\n" + conteudo;
    }
}
