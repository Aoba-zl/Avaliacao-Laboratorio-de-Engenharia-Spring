package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Cliente;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.ClienteDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.GenericDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.utils.ManipularCoockies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.Map;

@Controller
public class ManterClienteController {

    @Autowired
    private GenericDAO gdao;
//    @Autowired dando erro e eu não o PQ
    private ClienteDAO cdao;


    ManipularCoockies valCoockie = new ManipularCoockies();


    @RequestMapping(name = "manter_cliente", value = "/manter_cliente", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {
//        Cookie[] cookies = req.getCookies();
//        String login = valCoockie.buscaValorCookie("login", cookies);
//        String senha = valCoockie.buscaValorCookie("senha", cookies);
        String login= "";
        String senha= "";

        String erro = "";
        String saida = "";

        Cliente cliente = new Cliente();
        try {
            if (!login.equalsIgnoreCase("") && !senha.equalsIgnoreCase(""))
            {
                cliente = cdao.buscarCliente(login);
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            erro = e.getMessage();
        }
        finally
        {
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
            model.addAttribute("cliente", cliente);

        }
        return new ModelAndView("manter_cliente");
    }

    @RequestMapping(name = "manter_cliente", value = "/manter_cliente", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
//        Cookie[] cookies = req.getCookies();
//        String login = valCoockie.buscaValorCookie("login", cookies);
//        String senha = valCoockie.buscaValorCookie("senha", cookies);
        String login= "";
        String senha= "";

        String erro = "";
        String saida = "";

        Cliente cliente = new Cliente();
        try {
            if (!login.equalsIgnoreCase("") && !senha.equalsIgnoreCase(""))
            {
                cliente = cdao.buscarCliente(login);
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            erro = e.getMessage();
        }
        finally
        {
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
            model.addAttribute("cliente", cliente);
        }
        return new ModelAndView("manter_cliente");
    }
}
