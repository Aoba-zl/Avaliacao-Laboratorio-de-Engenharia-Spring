package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.ItemVenda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Venda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.CarrinhoDAO;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.utils.ManipularCoockies;
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
public class CarrinhoController {
    @Autowired
    CarrinhoDAO carrinhoDAO;

    ManipularCoockies valCoockie = new ManipularCoockies();

    Venda carrinho = new Venda();
    List<ItemVenda> produtosCarrinho = new ArrayList<>();
    double subTotal= 0;
    double desconto= 0;
    double total;
    String stringSubTotal= "";
    String stringDesconto= "";
    String stringTotal= "";


    @RequestMapping(name = "carrinho", value = "/carrinho", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        String item_codigo= allRequestParam.get("codigo");
        String botao= allRequestParam.get("acao");
//        Cookie[] cookies = req.getCookies();
//        String email = valCoockie.buscaValorCookie("login", cookies);
        String email = "teste";

        String erro= "";
        String saida= "";
        int codigo_venda;
        subTotal = 0;
        desconto = 0;
        total = 0;

        try {
            carrinho = carrinhoDAO.getProdutosCarrinho(email);
            produtosCarrinho = carrinho.getItens();

            if (botao != null){
                if (botao.equals("Remover")){
                    carrinhoDAO.removerProdutoCarrinho(Integer.parseInt(item_codigo));

                    carrinho = carrinhoDAO.getProdutosCarrinho(email);
                    produtosCarrinho = carrinho.getItens();

                    calcularTotal(email);

                    if (produtosCarrinho.isEmpty()){
                        codigo_venda = carrinhoDAO.consultarVenda(email);
                        carrinhoDAO.deletarCarrinho(codigo_venda);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
            model.addAttribute("produtosCarrinho", produtosCarrinho);
            model.addAttribute("subTotal", stringSubTotal);
            model.addAttribute("desconto", stringDesconto);
            model.addAttribute("total", stringTotal);

        }

        return new ModelAndView("carrinho");
    }

    @RequestMapping(name = "carrinho", value = "/carrinho", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
        String botao= allRequestParam.get("botao");
//        Cookie[] cookies = req.getCookies();
//        String email = valCoockie.buscaValorCookie("login", cookies);
        String email = "";

        String erro= "";
        String saida= "";

        int codigo_venda;
        subTotal = 0;
        desconto = 0;
        total = 0;

        try {
            if (botao != null){
                if (botao.equalsIgnoreCase("Finalizar Compra")){
                    if (!produtosCarrinho.isEmpty()){
                        calcularTotal(email);
                        codigo_venda = carrinhoDAO.consultarVenda(email);
                        carrinhoDAO.finalizarCompra(codigo_venda, total, desconto);
                        saida = "Compra finalizada com sucesso!";

                        stringDesconto = "";
                        stringTotal = "";
                        stringSubTotal = "";

                        produtosCarrinho = null;
                    }
                    else {
                        erro = "Sem produtos no carrinho";
                    }
                }
            }
            else {
                carrinho = carrinhoDAO.getProdutosCarrinho(email);
                produtosCarrinho = carrinho.getItens();

                calcularTotal(email);
            }


        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
            model.addAttribute("produtosCarrinho", produtosCarrinho);
            model.addAttribute("subTotal", stringSubTotal);
            model.addAttribute("desconto", stringDesconto);
            model.addAttribute("total", stringTotal);
        }


        return new ModelAndView("carrinho");
    }

    private void calcularTotal(String email) throws SQLException, ClassNotFoundException {
        int total_livros= 0;
        for (ItemVenda item : produtosCarrinho){
            subTotal += item.getTotal();

            total_livros += item.getQuantidade();
        }

        if (!carrinhoDAO.verificarDescontoAno(email)){
            if (carrinhoDAO.verificarDescontoTotal(email)) {
                desconto = subTotal * 0.6;
            }

        }
        else {
            if (total_livros > 2){
                desconto = subTotal * 0.3;
            }
        }

        total = subTotal - desconto;

        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        stringSubTotal = formatoMoeda.format(subTotal);
        stringDesconto = formatoMoeda.format(desconto);
        stringTotal = formatoMoeda.format(total);
    }
}
