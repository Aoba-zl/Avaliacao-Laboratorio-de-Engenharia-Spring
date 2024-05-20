USE livraria

GO

/*
    DROP FUNCTION fn_validar_login
*/

CREATE FUNCTION fn_validar_login (@email VARCHAR(100), @senha VARCHAR(100))
RETURNS VARCHAR(50)
BEGIN
    DECLARE @valido VARCHAR(50),
    @email_existe INT, @senha_valida INT

    SELECT @email_existe=COUNT(email) FROM cliente WHERE email=@email
    SELECT @senha_valida=COUNT(email) FROM cliente WHERE email=@email AND senha=@senha

    IF (@email_existe = 1 AND @senha_valida = 1)
    BEGIN; SET @valido=N'Login válido'; END
    IF (@email_existe = 1 AND @senha_valida = 0)
    BEGIN; SET @valido=N'Senha inválida'; END
    IF (@email_existe = 0)
    BEGIN; SET @valido=N'E-mail inválida'; END

    RETURN @valido
END