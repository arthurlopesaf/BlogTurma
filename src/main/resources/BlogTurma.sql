CREATE DATABASE IF NOT EXISTS BlogTurma;
USE BlogTurma;

CREATE TABLE IF NOT EXISTS usuario (
                                       id_usuario INT PRIMARY KEY AUTO_INCREMENT,
                                       email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS publicacoes (
                                           id_publicacoes INT PRIMARY KEY AUTO_INCREMENT,
                                           titulo VARCHAR(255) NOT NULL,
    conteudo TEXT NOT NULL,
    data_postado DATETIME DEFAULT CURRENT_TIMESTAMP,
    idusuario INT,
    FOREIGN KEY (idusuario)
    REFERENCES usuario(id_usuario)
    );

CREATE TABLE IF NOT EXISTS comentario (
                                          id_comentario INT PRIMARY KEY AUTO_INCREMENT,
                                          texto TEXT NOT NULL,
                                          data_postagem DATETIME DEFAULT CURRENT_TIMESTAMP,
                                          idpublicacoes INT,
                                          idusuario INT,
                                          FOREIGN KEY (idpublicacoes)
    REFERENCES publicacoes(id_publicacoes),
    FOREIGN KEY (idusuario)
    REFERENCES usuario(id_usuario)
    );