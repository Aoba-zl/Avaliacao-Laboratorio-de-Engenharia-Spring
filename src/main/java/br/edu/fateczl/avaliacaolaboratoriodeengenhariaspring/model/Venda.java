package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Cliente;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.ItemVenda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Venda
{
    private int codigo;
    private double precoTotal;
    private double desconto;
    private Cliente cliente;
    private String dataPagamento;
    private boolean finalizada;
    private List<ItemVenda> itens;

}