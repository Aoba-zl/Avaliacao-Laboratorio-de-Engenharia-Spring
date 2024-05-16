package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Livro
{
    private int codigo;
    private String titulo;
    private String descricao;
    private String genero;
    private String autor;
    private double preco;
    private Date data_publicacao;
    private int paginas;
    private int estoque;
}