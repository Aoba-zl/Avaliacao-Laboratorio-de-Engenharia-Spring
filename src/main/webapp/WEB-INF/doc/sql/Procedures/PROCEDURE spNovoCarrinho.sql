USE livraria

GO

CREATE PROCEDURE spNovoCarrinho @email VARCHAR(255)
AS
BEGIN
    DECLARE @codigo INT
    SELECT @codigo = (MAX(codigo) + 1) FROM venda
    IF (@codigo IS NULL)
    BEGIN
        SET @codigo = 1
    END
    INSERT INTO venda VALUES (@codigo, @email, 0.0, 0.0, NULL, NULL)
END