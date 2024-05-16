package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence;


import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.ItemVenda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Livro;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Venda;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VendaDAO {
    private GenericDAO gDao;

    public VendaDAO(GenericDAO gDao) {
        this.gDao = gDao;
    }

    public List<Venda> listarCompras(String email) throws SQLException, ClassNotFoundException {
        List<Venda> vendas = new ArrayList<>();

        Connection c= gDao.getConnection();
        String sql= """
                select v.codigo,
                       convert(varchar(10), v.data_pagamento, 103) as dataformatada,
                       v.preco_total,
                       v.finalizada
                from venda v, livro l, item_venda iv, cliente c
                where v.codigo = iv.codigo_venda and
                      l.codigo = iv.codigo_livro and
                      v.email_cliente = c.email and
                      c.email = ?
                group by v.codigo, v.data_pagamento, v.preco_total, v.finalizada
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Venda venda = new Venda();

            venda.setCodigo(rs.getInt("codigo"));
            venda.setDataPagamento((rs.getString(2)));
            venda.setPrecoTotal(rs.getDouble("preco_total"));
            venda.setFinalizada(rs.getBoolean("finalizada"));

            vendas.add(venda);
        }
        rs.close();
        ps.close();
        c.close();
        return vendas;
    }

    public Venda listItemVenda(Venda venda) throws SQLException, ClassNotFoundException {
        Connection c= gDao.getConnection();
        String sql = """
                select l.titulo,
                       iv.quantidade,
                       iv.total,
                       v.desconto,
                       v.preco_total
                from venda v, livro l, item_venda iv, cliente c
                where v.codigo = iv.codigo_venda and
                    l.codigo = iv.codigo_livro and
                    v.email_cliente = c.email and
                    v.codigo = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, venda.getCodigo());

        ResultSet rs = ps.executeQuery();

        List<ItemVenda> itemVendas = new ArrayList<>();

        while (rs.next()) {
            ItemVenda itemVenda = new ItemVenda();
            Livro livro = new Livro();

            livro.setTitulo(rs.getString("titulo"));
            itemVenda.setQuantidade(rs.getInt("quantidade"));
            itemVenda.setTotal(rs.getDouble("total"));
            itemVenda.setLivro(livro);

            venda.setDesconto(rs.getDouble("desconto"));
            venda.setPrecoTotal(rs.getDouble("preco_total"));

            itemVendas.add(itemVenda);

        }
        venda.setItens(itemVendas);

        rs.close();
        ps.close();
        c.close();

        return venda;
    }
}