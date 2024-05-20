package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.ItemVenda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Venda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.GenericDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.VendaDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class ConsultarComprasController {
    @Autowired
    GenericDAO genericDAO;
    @Autowired
    VendaDAO vendaDAO;

    List<Venda> vendas= new ArrayList<>();
    List<ItemVenda> itens= new ArrayList<>();

    @RequestMapping(name = "consultar_compras", value = "/consultar_compras", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model,
    		HttpServletRequest request) {
        String venda_codigo = allRequestParam.get("codigo");
        String cmd= allRequestParam.get("acao");
        String email = "";
        HttpSession session = request.getSession(false);

        // TODO: corrigir validação de email
        if (session != null)
        	email = (String) session.getAttribute("login_c");


        String erro = "";
        String saida = "";
        double subTotal = 0;
        String subTotalFormatado = "";
        String total = "";
        String desconto = "";
        Venda venda = new Venda();

        try {
            vendas = vendaDAO.listarCompras(email); // TEST
            if (vendas.isEmpty()){
                saida = "Cliente não possui compras registradas";
            }

            if (venda_codigo != null & cmd != null) {
                venda.setCodigo(Integer.parseInt(venda_codigo));
                if (cmd.equalsIgnoreCase("Consultar")){
                    venda = vendaDAO.listItemVenda(venda);
                    itens = venda.getItens();

                    for (ItemVenda item : itens){
                        subTotal += item.getTotal();
                    }

                    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    total = formatoMoeda.format(venda.getPrecoTotal());
                    desconto = formatoMoeda.format(venda.getDesconto());
                    subTotalFormatado = formatoMoeda.format(subTotal);


                    saida = "Detalhes da compra carregados com sucesso";
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("vendas", vendas);
            model.addAttribute("itens", itens);
            model.addAttribute("subTotal", subTotalFormatado);
            model.addAttribute("desconto", desconto);
            model.addAttribute("total", total);

            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);

        }
        return new ModelAndView("consultar_compras");
    }

    @RequestMapping(name = "consultar_compras", value = "/consultar_compras", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        return new ModelAndView("consultar_compras");
    }
}
