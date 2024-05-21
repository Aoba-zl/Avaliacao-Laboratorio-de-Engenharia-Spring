package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Cliente;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.ClienteDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.GenericDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ManterClienteController {

    @Autowired
    private GenericDAO gdao;
    @Autowired
    private ClienteDAO cdao;

    @RequestMapping(name = "manter_cliente", value = "/manter_cliente", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model, 
    		HttpServletRequest request) {
        String login_c = allRequestParam.get("email") == null ? "" : allRequestParam.get("email");
        HttpSession session = request.getSession(false);

        if (session != null && login_c.equalsIgnoreCase(""))
        {
        	login_c = (String) session.getAttribute("login_c");
        	login_c = login_c == null ? "" : login_c;
        }
        
        String erro = "";
        String saida = "";

        Cliente cliente = new Cliente();
        try {
            if (!login_c.equalsIgnoreCase(""))
                cliente = cdao.buscarCliente(login_c);
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
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model, 
    		HttpServletRequest request) {
    	String cmd= allRequestParam.get("botao");
        String email = allRequestParam.get("email");
        String senha = allRequestParam.get("senha");
        String nome = allRequestParam.get("nome");
        String log = allRequestParam.get("log");
        String numero = allRequestParam.get("numero");
    	
        String login_c= "";
        HttpSession session = request.getSession(false);

        if (session != null)
        {
        	login_c = (String) session.getAttribute("login_c");
        	login_c = login_c == null ? "" : login_c;
        }

        String erro = "";
        String saida = "";

        Cliente cliente = new Cliente();
        
        try 
        {
            if (cmd.equalsIgnoreCase("Alterar Dados"))
            {
            	cliente.setEmail(email);
                cliente.setSenha(senha);
                cliente.setNome(nome);
                cliente.setEndereco_logradouro(log);
                cliente.setEndereco_numero(Integer.parseInt(numero));
                saida = cdao.alterar(cliente);
            }
            if (cmd.equalsIgnoreCase("Sair"))
            {
            	session.removeAttribute("login_c");
                return new ModelAndView("redirect:/index");
            }
            if (cmd.equalsIgnoreCase("Voltar"))
            {
                return new ModelAndView("redirect:/consultar_clientes");
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
