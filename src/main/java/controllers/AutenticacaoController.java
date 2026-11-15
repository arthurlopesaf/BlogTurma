package controllers;

import services.SessaoUsuario;
import services.UsuarioService;
import views.AutenticacaoView;

public class AutenticacaoController {
    private final AutenticacaoView autenticacaoView;
    private final UsuarioService usuarioService;

    public AutenticacaoController(AutenticacaoView view, UsuarioService usuarioService) {
        this.autenticacaoView = view;
        this.usuarioService = usuarioService;
    }

    public void cadastro() {
        var dados = autenticacaoView.SolicitarDadosCadastro();
        if (dados.email().isBlank() || dados.nome().isBlank() || dados.senha().isBlank()) {
            autenticacaoView.ExibirMensagem("Preencha todos os campos."); return;
        }
        if (dados.senha().length() < 6) { autenticacaoView.ExibirMensagem("A senha precisa ter pelo menos 6 caracteres."); return; }
        if (usuarioService.Cadastrar(dados.email(), dados.nome(), dados.senha())) autenticacaoView.ExibirMensagem("Cadastro concluído! Você já pode fazer login.");
        else autenticacaoView.ExibirMensagem("Não foi possível cadastrar. Verifique o e-mail (formato válido e ainda não cadastrado).");
    }

    public void autenticar() {
        var dados = autenticacaoView.Solicitar();
        if (dados.email().isBlank() || dados.senha().isBlank()) { autenticacaoView.ExibirMensagem("Informe e-mail e senha."); return; }
        var usuario = usuarioService.Autenticar(dados.email(), dados.senha());
        if (usuario != null) {
            SessaoUsuario.iniciar(usuario.getEmail(), usuario.getNome());
            autenticacaoView.ExibirMensagem("Login efetuado! Bem-vindo(a), " + usuario.getNome() + ".");
        } else autenticacaoView.ExibirMensagem("E-mail ou senha inválidos.");
    }
}
