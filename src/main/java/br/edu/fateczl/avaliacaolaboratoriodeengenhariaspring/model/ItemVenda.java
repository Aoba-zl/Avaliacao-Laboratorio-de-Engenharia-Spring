package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItemVenda
{
    private int id;
    private int quantidade;
    private double total;
    Livro livro;
}