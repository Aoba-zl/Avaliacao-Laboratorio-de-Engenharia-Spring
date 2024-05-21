USE livraria

GO

/*
    DROP FUNCTION fn_buscar_clientes
*/

CREATE FUNCTION  fn_buscar_clientes(@tipo CHAR(1), @valor VARCHAR(200))
RETURNS @table TABLE
(
    email VARCHAR(100),
    nome VARCHAR(100),
    enderco_log VARCHAR(100)
)
BEGIN
    SET @tipo = UPPER(@tipo)
    SET @valor = UPPER(@valor)
    -- ******************************* --

    -- E email ; N nome ; L logradouro
    IF (@tipo = 'E')
    BEGIN
        INSERT INTO @table
            SELECT email, nome,
            CASE WHEN (LEN(enderco_log) > 30) THEN
                SUBSTRING(enderco_log, 0, 30) + '..., ' + CAST(endereco_num AS VARCHAR(10))
            ELSE
                enderco_log + ', ' + CAST(endereco_num AS VARCHAR(10))
            END AS endereco
            FROM cliente WHERE email LIKE '%' + @valor + '%'
    END
    IF (@tipo = 'N')
    BEGIN
        INSERT INTO @table
            SELECT email, nome,
            CASE WHEN (LEN(enderco_log) > 30) THEN
                SUBSTRING(enderco_log, 0, 30) + '..., ' + CAST(endereco_num AS VARCHAR(10))
            ELSE
                enderco_log + ', ' + CAST(endereco_num AS VARCHAR(10))
            END AS endereco
            FROM cliente WHERE nome LIKE '%' + @valor + '%'
    END
    IF (@tipo = 'L')
    BEGIN
        INSERT INTO @table
            SELECT email, nome,
            CASE WHEN (LEN(enderco_log) > 30) THEN
                SUBSTRING(enderco_log, 0, 30) + '..., ' + CAST(endereco_num AS VARCHAR(10))
            ELSE
                enderco_log + ', ' + CAST(endereco_num AS VARCHAR(10))
            END AS endereco
            FROM cliente WHERE enderco_log LIKE '%' + @valor + '%'
    END

    RETURN
END

