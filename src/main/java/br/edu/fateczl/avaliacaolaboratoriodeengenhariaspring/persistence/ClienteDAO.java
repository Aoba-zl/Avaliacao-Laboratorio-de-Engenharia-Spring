package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Cliente;

@Repository
public class ClienteDAO
{
    private GenericDAO gDAO;

    public ClienteDAO (GenericDAO gDAO)
    {
        this.gDAO = gDAO;
    }

    public String inserir(Cliente cliente) throws SQLException, ClassNotFoundException
    {
        return iud_cliente("I", cliente);
    }

    public String alterar(Cliente cliente) throws SQLException, ClassNotFoundException
    {
        return iud_cliente("U", cliente);
    }

    public String excluir(Cliente cliente) throws SQLException, ClassNotFoundException
    {
        return iud_cliente("D", cliente);
    }

    private String iud_cliente(String modo, Cliente cliente) throws SQLException, ClassNotFoundException
    {
        Connection con = gDAO.getConnection();
        String sql = "{ CALL iud_cliente (?, ?, ?, ?, ?, ?, ?) }";
        CallableStatement cs = con.prepareCall(sql);
        cs.setString(1, modo);
        cs.setString(2, cliente.getEmail());
        cs.setString(3, cliente.getSenha());
        cs.setString(4, cliente.getNome());
        cs.setString(5, cliente.getEndereco_logradouro());
        cs.setInt(6, cliente.getEndereco_numero());
        cs.registerOutParameter(7, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(7);

        cs.close();
        con.close();
        return saida;
    }

    public Cliente buscarCliente (String login) throws SQLException, ClassNotFoundException
    {
        Connection con = gDAO.getConnection();
        String sql = "SELECT email AS e, senha AS s, nome AS n, enderco_log AS el, endereco_num AS en FROM cliente WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, login);

        Cliente cliente = new Cliente();
        ResultSet rs = ps.executeQuery();
        if (rs.next())
        {
            cliente.setEmail(rs.getString("e"));
            cliente.setSenha(rs.getString("s"));
            cliente.setNome(rs.getString("n"));
            cliente.setEndereco_logradouro(rs.getString("el"));
            cliente.setEndereco_numero(rs.getInt("en"));
        }

        con.close();
        return cliente;
    }
    
    public List<Cliente> pesquisarClientes (String tipoBusca, String valorBusca) 
    		throws SQLException, ClassNotFoundException
    {
    	Connection con = gDAO.getConnection();
    	String sql = "SELECT fn.email, fn.nome, fn.enderco_log AS endereco ";
    	sql += "FROM fn_buscar_clientes(?, ?) AS fn";
    	PreparedStatement ps = con.prepareStatement(sql);
    	ps.setString(1, tipoBusca);
    	ps.setString(2, valorBusca);
    	
    	ResultSet rs = ps.executeQuery();
    	List<Cliente> clientes = new ArrayList<>();
    	
    	while (rs.next())
    	{
    		Cliente c = new Cliente();
    		c.setEmail(rs.getString("email"));
            c.setNome(rs.getString("nome"));
            c.setEndereco_logradouro(rs.getString("endereco"));
    		
    		clientes.add(c);
    	}
    	
    	return clientes;
    }


}