package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence;


import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Cliente;

import java.sql.*;

public class ClienteDAO
{
    private GenericDAO gDAO;

    public ClienteDAO (GenericDAO gDAO)
    {
        this.gDAO = gDAO;
    }

    public String validarLogin(Cliente cliente) throws SQLException, ClassNotFoundException
    {
        Connection con = gDAO.getConnection();
        String sql = "SELECT dbo.fn_validar_login(?, ?) AS saida";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, cliente.getEmail());
        ps.setString(2, cliente.getSenha());

        String saida = "";
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            saida = rs.getString("saida");

        con.close();
        return saida;
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


}