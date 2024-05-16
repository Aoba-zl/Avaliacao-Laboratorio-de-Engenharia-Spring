package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Livro;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.LivroDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    LivroDAO lDao;

    @RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {
        List<Livro> livros = new ArrayList<>();
        String erro = "";

        try {
            livros = lDao.listar();
        } catch (Exception e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("livros", livros);
            model.addAttribute("erro", erro);

        }
        return new ModelAndView("index");
    }

    @RequestMapping(name = "index", value = "/index", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        List<Livro> livros = new ArrayList<>();
        String busca= allRequestParam.get("nome");
        String erro = "";

        try {
            livros = lDao.buscar(busca);
        } catch (Exception e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("livros", livros);
            model.addAttribute("erro", erro);

        }
        return new ModelAndView("index");
    }
}
