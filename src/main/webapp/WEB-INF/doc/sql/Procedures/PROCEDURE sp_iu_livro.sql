USE livraria

GO

-- Este arquivo define utilidades para as funções dos usuários lojistas para manutenção de livros
-- Utilizadas no arquivo LivroDAO

CREATE PROCEDURE sp_iu_livro
    @codigo INT,
    @titulo VARCHAR(100),
    @descricao VARCHAR(100),
    @genero VARCHAR(100),
    @autor VARCHAR(100),
    @preco DECIMAL(6, 2),
    @dt_publicacao DATE,
    @num_paginas INT,
    @estoque INT,
    @mensagem VARCHAR(255) OUTPUT
AS
BEGIN
    -- Inserir livro
    IF (SELECT COUNT(codigo) FROM livro WHERE codigo = @codigo) = 0
    BEGIN
        INSERT INTO livro
        VALUES (@codigo, @titulo, @descricao, @genero, @autor, @preco, @dt_publicacao, @num_paginas, @estoque)
            SET @mensagem = 'Livro inserido com sucesso!'
    END
        -- Atualizar livro
    ELSE
    BEGIN
                -- Pegar a quantidade anteriro
                DECLARE @antigo_estoque INT
        SELECT @antigo_estoque = estoque FROM livro WHERE codigo = @codigo

        -- Atualizar livro
        UPDATE livro
        SET titulo = @titulo, descricao = @descricao, genero = @genero,
            autor = @autor, preco = @preco, dt_publilcacao = @dt_publicacao,
            num_paginas = @num_paginas, estoque = @estoque
        WHERE codigo = @codigo

          -- Se o novo estoque for menor que o estoque anterior
        IF @antigo_estoque > @estoque
        BEGIN
            EXEC sp_manutencao_estoque @codigo, @antigo_estoque, @estoque
        END

                -- Atualizar todos valores em carrinhos
        UPDATE item_venda
        SET total = quantidade * @preco
        WHERE codigo_livro = @codigo AND codigo_venda IN (SELECT codigo FROM venda WHERE data_pagamento IS NULL)
        UPDATE venda SET preco_total = (SELECT COALESCE(SUM(total),0) FROM item_venda WHERE codigo_venda = venda.codigo)
        WHERE data_pagamento IS NULL

        SET @mensagem = 'Livro atualizado com sucesso!'
    END
END

