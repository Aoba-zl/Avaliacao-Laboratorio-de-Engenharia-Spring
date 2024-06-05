package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Livro;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.CarrinhoDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.LivroDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.util.Map;
import java.util.Objects;

@Controller
public class ManterLivroController {
    @Autowired
    LivroDAO lDao;
    @Autowired
    CarrinhoDAO cDAO;

    Livro l= new Livro();

    @RequestMapping(name = "manter_livro", value = "/manter_livro", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        String codigo= allRequestParam.get("codigo");
        String erro = "";

        try {
            if (codigo != null) {
                l = lDao.visualizar(Integer.parseInt(codigo));
            }
        } catch (Exception e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("livro", l);
            model.addAttribute("erro", erro);
            model.addAttribute("codigoLivro", codigo);

        }
        return new ModelAndView("manter_livro");
    }

    @RequestMapping(name = "manter_livro", value = "/manter_livro", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        String codigo = allRequestParam.get("codigoLivro");
        String titulo = allRequestParam.get("titulo");
        String descricao = allRequestParam.get("descricao");
        String genero = allRequestParam.get("genero");
        String autor = allRequestParam.get("autor");
        String preco = allRequestParam.get("preco");
        String dt_publicacao = allRequestParam.get("dt_publicacao");
        String num_paginas = allRequestParam.get("num_paginas");
        String estoque = allRequestParam.get("estoque");
        String erro = "";
        String saida = "";


        try {
            if (Objects.equals(titulo, "") || Objects.equals(descricao, "") || Objects.equals(genero, "") || Objects.equals(autor, "") ||
                    Objects.equals(preco, "") || Objects.equals(dt_publicacao, "") || Objects.equals(num_paginas, "") || Objects.equals(estoque, "")) {
                erro = "Nenhum campo pode estar vazio";

                atualizarCampos(titulo, descricao, genero, autor, preco, dt_publicacao, num_paginas, estoque);
            }
            else {
                l.setCodigo(Integer.parseInt(codigo));
                l.setTitulo(titulo);
                l.setDescricao(descricao);
                l.setGenero(genero);
                l.setAutor(autor);
                l.setPreco(Double.parseDouble(preco));
                l.setData_publicacao(Date.valueOf(dt_publicacao));
                l.setPaginas(Integer.parseInt(num_paginas));
                l.setEstoque(Integer.parseInt(estoque));
                lDao.manter(l, "U");
                saida = "Livro alterado com sucesso";
            }
        } catch (Exception e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("livro", l);
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
        }
        return new ModelAndView("manter_livro");
    }

    private void atualizarCampos(String titulo, String descricao, String genero, String autor, String preco,
                                 String dt_publicacao, String num_paginas, String estoque) {
        if (!Objects.equals(titulo, "")){
            l.setTitulo(titulo);
        }
        if (!Objects.equals(descricao, "")){
            l.setDescricao(descricao);
        }
        if (!Objects.equals(genero, "")){
            l.setGenero(genero);
        }
        if (!Objects.equals(autor, "")){
            l.setAutor(autor);
        }
        if (!Objects.equals(preco, "")){
            l.setPreco(Double.parseDouble(preco));
        }
        if (!Objects.equals(dt_publicacao, "")){
            l.setData_publicacao(Date.valueOf(dt_publicacao));
        }
        if (!Objects.equals(num_paginas, "")){
            l.setPaginas(Integer.parseInt(num_paginas));
        }
        if (!Objects.equals(estoque, "")){
            l.setEstoque(Integer.parseInt(estoque));
        }
    }
}
