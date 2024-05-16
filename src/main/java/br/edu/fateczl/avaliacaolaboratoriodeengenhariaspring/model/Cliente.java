package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Cliente
{
    private String nome;
    private String email;
    private String senha;
    private String endereco_logradouro;
    private int endereco_numero;
}