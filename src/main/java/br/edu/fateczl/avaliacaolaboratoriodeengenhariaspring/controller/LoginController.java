package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import java.sql.SQLException;
import java.util.Map;

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
public class LoginController {


    @RequestMapping(name = "login_cliente", value = "/login_cliente", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {

        return new ModelAndView("login_cliente");
    }

    @RequestMapping(name = "login_cliente", value = "/login_cliente", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model, HttpServletRequest request) {
        String pagina = "redirect:/index";

        String cmd = allRequestParam.get("botao");
        String email = allRequestParam.get("login");
        String senha = allRequestParam.get("senha");

        String erro = "";
        String saida = "";

        Cliente cliente = new Cliente();

        try
        {
            if (cmd.equalsIgnoreCase("Realizar Login"))
            {
                cliente.setEmail(email);
                cliente.setSenha(senha);

                saida = validarCliente(cliente);
                HttpSession session = request.getSession();
                session.setAttribute("login_c", email);
            }
            if (cmd.equalsIgnoreCase("Criar uma Conta"))
            {
                pagina = "redirect:/cadastrar_cliente";
                
            }
        }
        catch ( Exception e)
        {
            erro = e.getMessage();
            pagina = "login_cliente";
        }
        finally
        {
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
        }


        return new ModelAndView(pagina);
    }

    private String validarCliente(Cliente cliente) throws Exception, SQLException, ClassNotFoundException
    {
        GenericDAO gdao = new GenericDAO();
        ClienteDAO cdao = new ClienteDAO(gdao);
        
        String saida = cdao.validarLogin(cliente);

        if (!saida.contains("válido"))
        {
        	throw new Exception(saida);
        }

        return saida;
    }
}
