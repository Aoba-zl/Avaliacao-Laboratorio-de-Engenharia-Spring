package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Cliente;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.ClienteDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.GenericDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.Map;

@Controller
public class LoginController {


    @RequestMapping(name = "login_cliente", value = "/login_cliente", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {

        return new ModelAndView("login_cliente");
    }

    @RequestMapping(name = "login_cliente", value = "/login_cliente", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        String pagina = "index.jsp";//"login_cliente.jsp";

        String cmd = allRequestParam.get("botao");
        String login = allRequestParam.get("login");
        String senha = allRequestParam.get("senha");

        String erro = "";
        String saida = "";

        Cliente cliente = new Cliente();
//        Cookie cookie_login = null;
//        Cookie cookie_senha = null;

        try
        {
            if (cmd.equalsIgnoreCase("Realizar Login"))
            {
                cliente.setEmail(login);
                cliente.setSenha(senha);

                saida = validarCliente(cliente);
                if (saida.contains("válido"))
                {
//                    cookie_login = new Cookie("login", login);
//                    cookie_senha = new Cookie("senha", senha);
                }
            }
            if (cmd.equalsIgnoreCase("Criar uma Conta"))
            {
                pagina = "cadastrar_cliente.jsp";
            }
        }
        catch ( SQLException | ClassNotFoundException e)
        {
            erro = e.getMessage();
        }
        finally
        {
//            if (cookie_login != null)
//                resp.addCookie(cookie_login);
//            if (cookie_senha != null)
//                resp.addCookie(cookie_senha);

            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
        }


        return new ModelAndView("login_cliente");
    }

    private String validarCliente(Cliente cliente) throws SQLException, ClassNotFoundException
    {
        GenericDAO gdao = new GenericDAO();
        ClienteDAO cdao = new ClienteDAO(gdao);

        return cdao.validarLogin(cliente);
    }
}
