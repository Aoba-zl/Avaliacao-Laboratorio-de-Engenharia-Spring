package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

@Controller
public class CosultarClientesController 
{
	// 
	@Autowired
    GenericDAO genericDAO;
	@Autowired
	ClienteDAO cDAO;
	
	@RequestMapping(name = "consultar_clientes", value = "/consultar_clientes", method = RequestMethod.GET)
    public ModelAndView loginClienteDoGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) 
	{
		String cmd = allRequestParam.get("acao") == null ? "" : allRequestParam.get("acao");
		String email = allRequestParam.get("email") == null ? "" : allRequestParam.get("email");
		
		String erro = "";
        String saida = "";
        String pagina = "consultar_clientes";
        
        List<Cliente> clientes = new ArrayList<>();
        
        try
        {
        	if (cmd.equalsIgnoreCase("ALTERAR") && !email.equalsIgnoreCase(""))
        		pagina = "redirect:/manter_cliente?email="+email;
        	else
        		clientes = cDAO.pesquisarClientes("n", "%");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            erro = e.getMessage();
        }
        finally
        {
            model.addAttribute("clientes", clientes);
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
        }
        
        return new ModelAndView(pagina);
    }

	@RequestMapping(name = "consultar_clientes", value = "/consultar_clientes", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) 
	{
		String cmd = allRequestParam.get("botao") == null ? "" : allRequestParam.get("botao");
		String tipo_busca = allRequestParam.get("tipo_busca") == null ? "" : allRequestParam.get("tipo_busca");
		String valor_busca = allRequestParam.get("valor_busca") == null ? "" : allRequestParam.get("valor_busca");
		
		String erro = "";
        String saida = "";
        
        List<Cliente> clientes = new ArrayList<>();
        
        try
        {
        	if (cmd.equalsIgnoreCase("Buscar"))
        		clientes = cDAO.pesquisarClientes(tipo_busca, valor_busca);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            erro = e.getMessage();
        }
        finally
        {
            model.addAttribute("clientes", clientes);
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
        }
        
        return new ModelAndView("consultar_clientes");
    }
}
