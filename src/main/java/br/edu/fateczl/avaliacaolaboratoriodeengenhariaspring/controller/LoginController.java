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
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Vendedor;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.GenericDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.LoginDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	GenericDAO gdao;
	@Autowired
	LoginDAO ldao;
	
    @RequestMapping(name = "login_cliente", value = "/login_cliente", method = RequestMethod.GET)
    public ModelAndView loginClienteDoGet(ModelMap model) {
        return new ModelAndView("login_cliente");
    }

    @RequestMapping(name = "login_cliente", value = "/login_cliente", method = RequestMethod.POST)
    public ModelAndView loginClienteDoPost(@RequestParam Map<String, String> allRequestParam, ModelMap model, HttpServletRequest request) {
        String pagina = "redirect:/index";

        String cmd = allRequestParam.get("botao");
        String email = allRequestParam.get("login");
        String senha = allRequestParam.get("senha");

        String erro = "";
        String saida = "";

        Cliente cliente = new Cliente();

        try {
            if (cmd.equalsIgnoreCase("Realizar Login")) {
                cliente.setEmail(email);
                cliente.setSenha(senha);

                saida = validarCliente(cliente);
                HttpSession session = request.getSession();
                session.setAttribute("login_c", email);
                session.removeAttribute("login_v");
            }
            if (cmd.equalsIgnoreCase("Criar uma Conta"))
                pagina = "redirect:/cadastrar_cliente";
        } catch ( Exception e) {
            erro = e.getMessage();
            pagina = "login_cliente";
        }
        finally {
            model.addAttribute("cliente", cliente);
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
        }

        return new ModelAndView(pagina);
    }

    private String validarCliente(Cliente cliente) throws Exception, SQLException, ClassNotFoundException
    {
        String saida = ldao.validarLoginCliente(cliente);

        if (!saida.contains("válido"))
        	throw new Exception(saida);

        return saida;
    }
    
    @RequestMapping(name = "login_vendedor", value = "/login_vendedor", method = RequestMethod.GET)
    public ModelAndView loginVendedorDoGet(ModelMap model) {
        return new ModelAndView("login_vendedor");
    }

    @RequestMapping(name = "login_vendedor", value = "/login_vendedor", method = RequestMethod.POST)
    public ModelAndView loginVendedorDoPost(@RequestParam Map<String, String> allRequestParam, ModelMap model, HttpServletRequest request) {
        String pagina = "redirect:/index";

        String cmd = allRequestParam.get("botao");
        String login = allRequestParam.get("login");
        String senha = allRequestParam.get("senha");

        String erro = "";
        String saida = "";

        Vendedor vendedor = new Vendedor();

        try {
            if (cmd.equalsIgnoreCase("Realizar Login")) {
            	vendedor.setLogin(login);
            	vendedor.setSenha(senha);

                saida = validarVendador(vendedor);
                HttpSession session = request.getSession();
                session.setAttribute("login_v", login);
                session.removeAttribute("login_c");
            }
        } catch ( Exception e) {
            erro = e.getMessage();
            pagina = "login_vendedor";
        }
        finally {
            model.addAttribute("vendedor", vendedor);
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
        }

        return new ModelAndView(pagina);
    }
    
    private String validarVendador(Vendedor vendedor) throws Exception, SQLException, ClassNotFoundException
    {
    	String saida = ldao.validarLoginVendedor(vendedor);

        if (!saida.contains("válido"))
        	throw new Exception(saida);

        return saida;
    }
    
    @RequestMapping(name = "logout", value = "/logout", method = RequestMethod.GET)
    public ModelAndView loginOutDoGet(ModelMap model, HttpServletRequest request) 
    {
    	HttpSession session = request.getSession(false);

        if (session != null)
        	session.removeAttribute("login_v");
        
        return new ModelAndView("redirect:/index");
    }
}
