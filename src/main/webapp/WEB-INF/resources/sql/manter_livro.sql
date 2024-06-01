
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
SET tota = quantidade * @preco
WHERE codigo_livro = @codigo AND codigo_venda IN (SELECT codigo FROM venda WHERE data_pagamento IS NULL)
UPDATE venda SET preco_total = (SELECT COALESCE(SUM(tota),0) FROM item_venda WHERE codigo_venda = venda.codigo)
WHERE data_pagamento IS NULL

    SET @mensagem = 'Livro atualizado com sucesso!'
END
END

CREATE PROCEDURE sp_manutencao_estoque
    @codigo INT,
    @antigo_estoque INT,
    @novo_estoque INT
AS
BEGIN
    DECLARE @diferenca INT
    SET @diferenca = @antigo_estoque - @novo_estoque

    PRINT @diferenca

    -- Loop pelos itens em carrinhos não finalizados
    WHILE @diferenca > 0
BEGIN
            DECLARE @carrinho INT
            DECLARE @quantidade_no_carrinho INT

            -- Encontrar carrinho com mais itens
SELECT TOP 1 @carrinho = v.codigo, @quantidade_no_carrinho = iv.quantidade
FROM item_venda iv
         JOIN venda v ON iv.codigo_venda = v.codigo
WHERE iv.codigo_livro = @codigo AND v.data_pagamento IS NULL
ORDER BY iv.quantidade DESC

    PRINT @carrinho
            PRINT @quantidade_no_carrinho


            -- Se não houver mais carrinhos, retorne
            IF @carrinho IS NULL
                BREAK

            -- Se a diferenca for maior que a quantidade no carrinho, delete o item do carrinho
            IF @diferenca >= @quantidade_no_carrinho
BEGIN
                    PRINT 'teste 1'

DELETE FROM item_venda WHERE codigo_venda = @carrinho AND codigo_livro = @codigo
    SET @diferenca = @diferenca - @quantidade_no_carrinho
END
ELSE
BEGIN
                    -- Se a diferenca for menor, diminuir a diferenca da quantidade do item no carrinho
                    PRINT 'teste 2'

UPDATE item_venda
SET quantidade = @quantidade_no_carrinho - @diferenca
WHERE codigo_venda = @carrinho AND codigo_livro = @codigo
    SET @diferenca = 0
END
END
END