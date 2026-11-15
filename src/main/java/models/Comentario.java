package models;

public class Comentario {
    private final int id;
    private final String texto;
    private final String autor;
    private final String dataPostagem;

    public Comentario(int id, String texto, String autor, String dataPostagem) {
        this.id = id;
        this.texto = texto;
        this.autor = autor;
        this.dataPostagem = dataPostagem;
    }

    public int getId() { return id; }
    public String getTexto() { return texto; }
    public String getAutor() { return autor; }
    public String getDataPostagem() { return dataPostagem; }

    @Override
    public String toString() {
        return autor + " (" + dataPostagem + "): " + texto;
    }
}
