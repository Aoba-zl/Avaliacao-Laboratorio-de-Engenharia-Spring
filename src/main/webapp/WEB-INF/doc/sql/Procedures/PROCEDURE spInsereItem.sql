USE livraria

GO

CREATE PROCEDURE spInsereItem @livro INT, @venda INT, @qntd INT
AS
BEGIN
    DECLARE @codigo INT,
            @total DECIMAL(6, 2),
            @total_carrinho DECIMAL (6,2),
            @estoque_atual INT
    SELECT @total = preco FROM livro where codigo = @livro
    SELECT @total_carrinho = preco_total FROM venda WHERE codigo = @venda
    SELECT @codigo = (MAX(id) + 1) FROM item_venda
    SELECT @estoque_atual = estoque FROM livro WHERE codigo = @livro
    IF (@codigo IS NULL)
        BEGIN
            SET @codigo = 1
        END
    INSERT INTO item_venda VALUES (@codigo, @livro, @venda, @qntd, (@total * @qntd))
    UPDATE venda SET preco_total = (@total_carrinho + (@total * @qntd)) WHERE codigo = @venda
    UPDATE livro SET estoque = (@estoque_atual - @qntd) WHERE codigo = @livro
END