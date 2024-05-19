USE livraria

GO

/*
    DROP PROCEDURE iud_cliente
*/

CREATE PROCEDURE iud_cliente(@modo CHAR(1),@email VARCHAR(100), @senha VARCHAR(100), @nome VARCHAR(100),
    @enderco_log VARCHAR(100), @endereco_num INT, @saida VARCHAR(100) OUTPUT)
AS
BEGIN
    SET @modo = UPPER(@modo)
    IF (@modo = 'I' OR @modo = 'U' OR @modo = 'D')
    BEGIN
        DECLARE @ok INT, @erro VARCHAR(250)
        SET @ok = 1

        IF (@email IS NULL)
        BEGIN; SET @ok = 0; RAISERROR (N'E-mail não pode ser NULL', 16, 1); END


        IF (@ok = 1 AND (@modo = 'I' OR @modo = 'U') AND
            (@senha IS NULL OR @nome IS NULL OR @enderco_log IS NULL OR @endereco_num IS NULL))
        BEGIN; SET @ok = 0; RAISERROR (N'Para Inserir ou Alterar nenhum valor pode ser NULL', 16, 1); END

        IF (@ok = 1)
        BEGIN
            IF (@modo = 'I')
            BEGIN
                BEGIN TRY
                    INSERT INTO cliente (email, senha, nome, enderco_log, endereco_num) VALUES
                    (@email, @senha, @nome, @enderco_log, @endereco_num)
                    SET @saida = N'Cliente inserido com SUCESSO'
                END TRY
                BEGIN CATCH
                    SET @erro = ERROR_MESSAGE()
                    IF (@erro LIKE '%PRIMARY KEY%')
                    BEGIN
                        SET @erro = N'E-mail já Cadastrado'
                    END
                    ELSE
                    BEGIN
                        SET @erro = 'ERRO AO INSTERIR: ' + ERROR_MESSAGE()
                    END
                    RAISERROR (@erro, 16, 1)
                END CATCH
            END
            IF (@modo = 'U')
            BEGIN
                BEGIN TRY
                    UPDATE cliente
                    SET senha = @senha, nome = @nome,
                    enderco_log = @enderco_log, endereco_num = @endereco_num
                    WHERE email = @email
                    SET @saida = N'Cliente alterado com SUCESSO'
                END TRY
                BEGIN CATCH
                    SET @erro = 'ERRO AO ALTERAR: ' + ERROR_MESSAGE()
                    RAISERROR (@erro, 16, 1)
                END CATCH
            END
            IF (@modo = 'D')
            BEGIN
                BEGIN TRY
                    DELETE cliente WHERE email = @email
                    SET @saida = N'Cliente excluido com SUCESSO'
                END TRY
                BEGIN CATCH
                    SET @erro = 'ERRO AO EXCLUIR: ' + ERROR_MESSAGE()
                    RAISERROR (@erro, 16, 1)
                END CATCH
            END
        END
    END
    ELSE
    BEGIN
        RAISERROR (N'Modo inválido', 16, 1)
    END
END
