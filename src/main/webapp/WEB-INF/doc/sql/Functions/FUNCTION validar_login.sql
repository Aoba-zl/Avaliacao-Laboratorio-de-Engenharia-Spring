USE livraria

GO

/*
    DROP FUNCTION fn_validar_login
*/

CREATE FUNCTION fn_validar_login (@tipo VARCHAR(50), @login VARCHAR(100), @senha VARCHAR(100))
RETURNS VARCHAR(50)
BEGIN
    DECLARE @valido VARCHAR(50),
    @login_existe INT, @senha_valida INT

    IF (@tipo = 'cliente')
    BEGIN
        SELECT @login_existe=COUNT(email) FROM cliente WHERE email=@login
        SELECT @senha_valida=COUNT(email) FROM cliente WHERE email=@login AND senha=@senha
    END
    IF (@tipo = 'vendedor')
    BEGIN
        SELECT @login_existe=COUNT(login) FROM vendedor WHERE login=@login
        SELECT @senha_valida=COUNT(login) FROM vendedor WHERE login=@login AND senha=@senha
    END


    IF (@tipo = 'vendedor' OR @tipo = 'cliente')
    BEGIN
        IF (@login_existe = 1 AND @senha_valida = 1)
        BEGIN; SET @valido=N'Login válido'; END
        IF (@login_existe = 1 AND @senha_valida = 0)
        BEGIN; SET @valido=N'Senha inválida'; END
        IF (@login_existe = 0)
        BEGIN; SET @valido=N'Login inválido'; END
    END
    ELSE
    BEGIN
        SET @valido = N'Tipo inválido'
    END

    RETURN @valido
END