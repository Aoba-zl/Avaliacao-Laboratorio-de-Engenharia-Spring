package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Cliente;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Vendedor;

@Repository
public class LoginDAO 
{
	private GenericDAO gDAO;

    public LoginDAO (GenericDAO gDAO)
    {
        this.gDAO = gDAO;
    }

	public String validarLoginCliente(Cliente cliente) throws SQLException, ClassNotFoundException
    {
        Connection con = gDAO.getConnection();
        String sql = "SELECT dbo.fn_validar_login(?, ?, ?) AS saida";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "cliente");
        ps.setString(2, cliente.getEmail());
        ps.setString(3, cliente.getSenha());

        String saida = "";
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            saida = rs.getString("saida");

        con.close();
        return saida;
    }
	
	public String validarLoginVendedor(Vendedor vendedor) throws SQLException, ClassNotFoundException
    {
        Connection con = gDAO.getConnection();
        String sql = "SELECT dbo.fn_validar_login(?, ?, ?) AS saida";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "vendedor");
        ps.setString(2, vendedor.getLogin());
        ps.setString(3, vendedor.getSenha());

        String saida = "";
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            saida = rs.getString("saida");

        con.close();
        return saida;
    }
}
