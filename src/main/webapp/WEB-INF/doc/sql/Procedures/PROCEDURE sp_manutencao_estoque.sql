USE livraria

GO

-- Este arquivo define utilidades para as funções dos usuários lojistas para manutenção de livros
-- Utilizadas no arquivo LivroDAO

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