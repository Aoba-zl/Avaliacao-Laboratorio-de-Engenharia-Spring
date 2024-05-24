package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.ItemVenda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Livro;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Venda;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarrinhoDAO {
    private GenericDAO gDao;

    public CarrinhoDAO(GenericDAO gDao) {
        this.gDao = gDao ;
    }

    public Venda getProdutosCarrinho(String email) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql = """
                select iv.id,
                       l.titulo,
                       iv.quantidade,
                       iv.total,
                       l.codigo,
                       l.preco
                from item_venda iv, venda v, livro l, cliente c
                where iv.codigo_venda = v.codigo and
                      iv.codigo_livro = l.codigo and
                      v.email_cliente = c.email and
                      v.data_pagamento is null and
                      c.email = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        Venda venda = new Venda();
        List<ItemVenda> produtosCarrinho = new ArrayList<>();
        while (rs.next()) {
            ItemVenda itemVenda = new ItemVenda();
            Livro livro= new Livro();

            itemVenda.setId(rs.getInt("id"));
            livro.setTitulo(rs.getString(2));
            itemVenda.setQuantidade(rs.getInt(3));
            itemVenda.setTotal(rs.getDouble(4));
            livro.setCodigo(rs.getInt(5));
            livro.setPreco(rs.getDouble(6));

            itemVenda.setLivro(livro);
            produtosCarrinho.add(itemVenda);
        }
        venda.setItens(produtosCarrinho);

        ps.close();
        rs.close();
        c.close();

        return venda;
    }

    public void removerProdutoCarrinho(int idItem) throws SQLException, ClassNotFoundException {

        Connection c= gDao.getConnection();
        String sql= """
                delete
                from item_venda
                where id = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, idItem);

        ps.executeUpdate();

        ps.close();
        c.close();

    }

    public void deletarCarrinho(int id_venda) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql= """
                delete
                from venda
                where codigo = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id_venda);

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public int consultarVenda(String email) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql= """
                select codigo
                from venda v, cliente c
                where data_pagamento IS NULL and
                      v.email_cliente = c.email and
                      c.email = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return 0;
    }

    public void finalizarCompra(int id_venda, double preco_total, double desconto) throws SQLException, ClassNotFoundException {

        Connection c= gDao.getConnection();
        String sql = """
                update venda
                set data_pagamento = getdate(), preco_total = ?, desconto = ?
                where codigo = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDouble(1, preco_total);
        ps.setDouble(2, desconto);
        ps.setInt(3, id_venda);

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public void diminuirEstoque(List<ItemVenda> itens) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql = "CALL spDiminuirEstoque ?, ?";
        CallableStatement cs = c.prepareCall(sql);

        for (ItemVenda itemVenda : itens) {
            cs.setInt(1, itemVenda.getQuantidade());
            cs.setInt(2, itemVenda.getLivro().getCodigo());

            cs.execute();
        }

        cs.close();
        c.close();
    }

    public boolean verificarDescontoAno(String email) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql= """
                select IIF(((v.desconto + v.preco_total) * 0.6 = v.desconto), 1, 0)
                    as verificacao
                from cliente c, venda v
                where c.email = v.email_cliente and
                    v.data_pagamento is not null and
                    year(v.data_pagamento) = year(getdate()) and
                    c.email = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getInt(1) == 1){
                return true;
            }
        }

        return false;
    }

    public boolean verificarDescontoTotal(String email) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql = """
                select sum(v.preco_total) as total_gasto
                from cliente c, venda v
                where c.email = v.email_cliente and
                      v.data_pagamento is not null and
                      year(v.data_pagamento) = year(getdate()) and
                      c.email = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            if (rs.getDouble(1) > 1000){
                return true;
            }
        }

        return false;
    }

    public int novoCarrinho(String email) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql = "CALL spNovoCarrinho ?";
        CallableStatement cs = c.prepareCall(sql);
        cs.setString(1, email);
        cs.execute();

        cs.close();
        c.close();
        return consultarVenda(email);
    }

    public void adicionarItem(int codigoL, int codigoV, int qntd) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql = "CALL spInsereItem ?, ?, ?";
        CallableStatement cs = c.prepareCall(sql);
        cs.setInt(1, codigoL);
        cs.setInt(2, codigoV);
        cs.setInt(3, qntd);
        cs.execute();

        cs.close();
        c.close();
    }

    public boolean verificarEstoque(int codigo_livro, int novaQuantidade) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql= """
                select *
                from livro
                where codigo = ? and
                      estoque >= ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, codigo_livro);
        ps.setInt(2, novaQuantidade);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return true;
        }


        return false;
    }

    public void alterarQuantidade(int codigo_livro, int id_item, int novaQuantidade, double preco_livro) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        double subTotal= preco_livro * novaQuantidade;
        String sql = """
                update item_venda
                set quantidade= ?, total = ?
                where id = ? and
                      codigo_livro = ?
                """;
        PreparedStatement ps= c.prepareStatement(sql);
        ps.setInt(1, novaQuantidade);
        ps.setDouble(2, subTotal);
        ps.setInt(3, id_item);
        ps.setInt(4, codigo_livro);

        ps.executeUpdate();

        c.close();
        ps.close();

    }
}
