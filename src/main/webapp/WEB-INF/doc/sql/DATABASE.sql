USE master
GO

-- CRIAR DATABASE
BEGIN TRY
    CREATE DATABASE livraria
    PRINT 'DATABASE Criada'
END TRY
BEGIN CATCH
END CATCH

GO

USE livraria

GO

-- DROPPAR TABELAS PRA RESETAR
BEGIN TRY
PRINT 'DROPANDO TABELAS'
    DROP TABLE item_venda;
    DROP TABLE venda;
    DROP TABLE livro;
    DROP TABLE cliente;
    DROP TABLE vendedor
END TRY
BEGIN CATCH
PRINT 'SEM TABELAS PRA DROPPAR'
END CATCH

GO

-- CRIAR TABELAS

BEGIN TRY

CREATE TABLE livro
(
    codigo INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    genero VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    preco DECIMAL(6, 2),
    dt_publilcacao DATE NOT NULL,
    num_paginas INT NOT NULL,
    estoque INT NOT NULL,

    PRIMARY KEY (codigo)
)

CREATE TABLE cliente
(
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    enderco_log VARCHAR(100) NOT NULL,
    endereco_num INT NOT NULL,

    PRIMARY KEY (email)
)

CREATE TABLE venda
(
    codigo INT NOT NULL,
    email_cliente VARCHAR(100) NOT NULL,
    preco_total DECIMAL(6, 2) NOT NULL,
    desconto DECIMAL(6, 2) NOT NULL,
    data_pagamento DATE,
    finalizada BIT,

    PRIMARY KEY (codigo),
    FOREIGN KEY (email_cliente) REFERENCES cliente
)

CREATE TABLE item_venda
(
    id INT NOT NULL,
    codigo_livro INT NOT NULL,
    codigo_venda INT NOT NULL,
    quantidade INT NOT NULL,
    total DECIMAL(6, 2) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (codigo_livro) REFERENCES livro,
    FOREIGN KEY (codigo_venda) REFERENCES venda
)

CREATE TABLE vendedor
(
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,

    PRIMARY KEY (login)
)

PRINT 'TABELAS CRIADAS'

END TRY
BEGIN CATCH
PRINT 'TABELAS JÁ CRIADAS'
END CATCH

-- ************* --