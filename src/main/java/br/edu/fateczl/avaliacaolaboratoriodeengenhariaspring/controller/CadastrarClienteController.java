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
public class CadastrarClienteController {

    @RequestMapping(name = "cadastrar_cliente", value = "/cadastrar_cliente", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {

        return new ModelAndView("cadastrar_cliente");
    }

    @RequestMapping(name = "cadastrar_cliente", value = "/cadastrar_cliente", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        String cmd = allRequestParam.get("botao");
        String email = allRequestParam.get("email");
        String senha = allRequestParam.get("senha");
        String nome = allRequestParam.get("nome");
        String log = allRequestParam.get("log");
        String numero = allRequestParam.get("numero");

        String erro = "";
        String saida = "";

        Cliente cliente = new Cliente();

        try
        {
            if (cmd.equalsIgnoreCase("Cadastrar"))
            {
                cliente.setEmail(email);
                cliente.setSenha(senha);
                cliente.setNome(nome);
                cliente.setEndereco_logradouro(log);
                cliente.setEndereco_numero(Integer.parseInt(numero));
                saida = cadastarCliente(cliente);
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


        return new ModelAndView("cadastrar_cliente");
    }

    private String cadastarCliente(Cliente cliente) throws SQLException, ClassNotFoundException
    {
        GenericDAO gdao = new GenericDAO();
        ClienteDAO cdao = new ClienteDAO(gdao);

        return cdao.inserir(cliente);
    }
}
