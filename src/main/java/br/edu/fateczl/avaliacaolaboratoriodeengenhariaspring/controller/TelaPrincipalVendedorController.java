package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import java.util.Map;

@Controller
public class TelaPrincipalVendedorController {

    @RequestMapping(name = "tela_principal_vendedor", value = "/tela_principal_vendedor", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {
        return new ModelAndView("tela_principal_vendedor");
    }

    @RequestMapping(name = "tela_principal_vendedor", value = "/tela_principal_vendedor", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        return new ModelAndView("tela_principal_vendedor");
    }
}
