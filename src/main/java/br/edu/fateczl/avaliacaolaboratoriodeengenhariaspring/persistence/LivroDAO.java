package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Livro;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LivroDAO {

    private GenericDAO gDao;

    public LivroDAO(GenericDAO gDao) {
        this.gDao = gDao;
    }

    public List<Livro> listar() throws SQLException, ClassNotFoundException {
        List<Livro> livros = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT codigo, titulo, genero, autor, preco, estoque FROM livro";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Livro l = new Livro();
            l.setCodigo(rs.getInt("codigo"));
            l.setTitulo(rs.getString("titulo"));
            l.setGenero(rs.getString("genero"));
            l.setAutor(rs.getString("autor"));
            l.setPreco(rs.getDouble("preco"));
            l.setEstoque(rs.getInt("estoque"));

            livros.add(l);
        }
        rs.close();
        ps.close();
        c.close();
        return livros;
    }

    public List<Livro> buscar(String nome) throws SQLException, ClassNotFoundException {
        List<Livro> livros = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT codigo, titulo, genero, autor, preco, estoque FROM livro WHERE titulo LIKE ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "%" + nome + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Livro l = new Livro();
            l.setCodigo(rs.getInt("codigo"));
            l.setTitulo(rs.getString("titulo"));
            l.setTitulo(rs.getString("titulo"));
            l.setGenero(rs.getString("genero"));
            l.setAutor(rs.getString("autor"));
            l.setPreco(rs.getDouble("preco"));
            l.setEstoque(rs.getInt("estoque"));

            livros.add(l);
        }
        rs.close();
        ps.close();
        c.close();
        return livros;
    }

    public Livro visualizar(int codigo) throws SQLException, ClassNotFoundException {
        Livro l = new Livro();
        Connection c = gDao.getConnection();
        String sql = "SELECT * FROM livro WHERE codigo = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            l.setCodigo(rs.getInt("codigo"));
            l.setData_publicacao(rs.getDate("dt_publilcacao"));
            l.setTitulo(rs.getString("titulo"));
            l.setDescricao(rs.getString("descricao"));
            l.setGenero(rs.getString("genero"));
            l.setAutor(rs.getString("autor"));
            l.setPreco(rs.getDouble("preco"));
            l.setEstoque(rs.getInt("estoque"));
            l.setPaginas(rs.getInt("num_paginas"));
        }
        rs.close();
        ps.close();
        c.close();
        return l;
    }

}