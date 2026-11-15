package controllers;

import models.Publicacoes;
import services.PublicacaoService;
import services.SessaoUsuario;
import views.PublicacaoView;

public class PublicacaoController {
    private final PublicacaoService service;
    private final PublicacaoView view;

    public PublicacaoController(PublicacaoService service, PublicacaoView view) {
        this.service = service; this.view = view;
    }

    public void ListarPostagens() { view.ListarPostagens(service.ListarPublicacao()); }

    public void Visualizar() {
        int id = view.SolicitarId(); if (id < 1) return;
        Publicacoes pub = service.BuscarPorId(id);
        if (pub == null) { view.Mensagem("Publicação não encontrada."); return; }
        view.ExibirPublicacao(pub, service.ListarComentarios(id));
    }

    public void Criar() {
        String[] dados = view.SolicitarNovaPublicacao();
        if (dados[0].isBlank() || dados[1].isBlank()) { view.Mensagem("Título e conteúdo são obrigatórios."); return; }
        view.Mensagem(service.Criar(dados[0], dados[1], SessaoUsuario.getUsuarioEmail()) ? "Publicação criada com sucesso!" : "Não foi possível criar a publicação.");
    }

    public void Comentar() {
        int id = view.SolicitarId(); if (id < 1) return;
        if (service.BuscarPorId(id) == null) { view.Mensagem("Publicação não encontrada."); return; }
        String texto = view.SolicitarComentario();
        if (texto.isBlank()) { view.Mensagem("O comentário não pode ficar vazio."); return; }
        view.Mensagem(service.AdicionarComentario(id, texto, SessaoUsuario.getUsuarioEmail()) ? "Comentário publicado!" : "Não foi possível publicar o comentário.");
    }

    public void Editar() {
        int id = view.SolicitarId(); if (id < 1) return;
        Publicacoes atual = service.BuscarPorId(id);
        if (atual == null) { view.Mensagem("Publicação não encontrada."); return; }
        String[] dados = view.SolicitarEdicao(atual);
        view.Mensagem(service.Editar(id, dados[0], dados[1], SessaoUsuario.getUsuarioEmail()) ? "Publicação atualizada!" : "Você só pode editar suas próprias publicações.");
    }

    public void Excluir() {
        int id = view.SolicitarId(); if (id < 1) return;
        if (service.BuscarPorId(id) == null) { view.Mensagem("Publicação não encontrada."); return; }
        if (!view.ConfirmarExclusao()) { view.Mensagem("Exclusão cancelada."); return; }
        view.Mensagem(service.Excluir(id, SessaoUsuario.getUsuarioEmail()) ? "Publicação excluída!" : "Você só pode excluir suas próprias publicações.");
    }
}
