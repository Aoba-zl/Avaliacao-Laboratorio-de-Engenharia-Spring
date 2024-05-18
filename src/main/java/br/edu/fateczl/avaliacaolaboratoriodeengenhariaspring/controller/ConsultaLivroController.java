package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Livro;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Venda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.CarrinhoDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.LivroDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ConsultaLivroController {

    @Autowired
    LivroDAO lDao;
    @Autowired
    CarrinhoDAO cDAO;

    @RequestMapping(name = "iconsulta_livro", value = "/consulta_livro", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model, 
    		HttpServletRequest request) {
        String codigo= allRequestParam.get("codigo");
        String erro = "";
        Livro l = new Livro();

        try {
            l = lDao.visualizar(Integer.parseInt(codigo));
        } catch (Exception e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("livro", l);
            model.addAttribute("erro", erro);
            model.addAttribute("qntd", 1);
            model.addAttribute("codigoLivro", codigo);

        }
        return new ModelAndView("consulta_livro");
    }

    @RequestMapping(name = "consulta_livro", value = "/consulta_livro", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model, 
    		HttpServletRequest request) {
        String qntd = allRequestParam.get("qntd");
        String codigo = allRequestParam.get("codigoLivro");
        String erro = "";
        String saida = "";
        String email = "";
        HttpSession session = request.getSession(false);

        if (session != null)
        {
        	email = (String) session.getAttribute("login_c");
        	email = email == null ? "" : email;
        }

        Livro l = new Livro();
        Venda v = new Venda();

        try {
            l = lDao.visualizar(Integer.parseInt(codigo));

	        if (email.equalsIgnoreCase(""))
	        {
	        	return new ModelAndView("login_cliente");
	        }
	        else
	        {
	        	if (l.getEstoque() < Integer.parseInt(qntd)) {
	                erro = "Quantidade inválida, por favor, tente novamente.";
	
	            } else {
	                v.setCodigo(cDAO.consultarVenda(email));
	                if (v.getCodigo() == 0) {
	                    v.setCodigo(cDAO.novoCarrinho(email));
	                }
	
	                cDAO.adicionarItem(l.getCodigo(), v.getCodigo(), Integer.parseInt(qntd));
	                l = lDao.visualizar(Integer.parseInt(codigo));
	                saida = "Livro adicionado com sucesso";
	            }
	        }

        } catch (Exception e) {
            erro = e.getMessage();
            e.printStackTrace();
        } finally {
            model.addAttribute("livro", l);
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
            model.addAttribute("qntd", 1);
        }
        return new ModelAndView("consulta_livro");
    }
}
