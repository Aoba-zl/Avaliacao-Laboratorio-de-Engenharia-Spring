package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.ItemVenda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.model.Venda;
import br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.persistence.CarrinhoDAO;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {
    @Autowired
    CarrinhoDAO carrinhoDAO;

    Venda carrinho = new Venda();
    List<ItemVenda> produtosCarrinho = new ArrayList<>();
    double subTotal= 0;
    double desconto= 0;
    double total = 0;
    String stringSubTotal= "";
    String stringDesconto= "";
    String stringTotal= "";


    @RequestMapping(name = "carrinho", value = "/carrinho", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model,
    		HttpServletRequest request) {
        String item_codigo= allRequestParam.get("codigo");
        String livro_codigo= allRequestParam.get("livro_codigo");
        String botao= allRequestParam.get("acao");
        String email = "";
        HttpSession session = request.getSession(false);

        if (session != null)
        {
        	email = (String) session.getAttribute("login_c");
        	email = email == null ? "" : email;
        }


        String erro= "";
        String saida= "";
        int codigo_venda;
        int quantidade= 0;
        double preco_livro = 0.0;
        subTotal = 0;
        desconto = 0;
        total = 0;

        try {
            carrinho = carrinhoDAO.getProdutosCarrinho(email);
            produtosCarrinho = carrinho.getItens();

            atualizarQuantidade();

            if (botao != null){
                if (botao.equals("Remover")){
                    carrinhoDAO.removerProdutoCarrinho(Integer.parseInt(item_codigo));

                    carrinho = carrinhoDAO.getProdutosCarrinho(email);
                    produtosCarrinho = carrinho.getItens();

                    if (produtosCarrinho.isEmpty()){
                        codigo_venda = carrinhoDAO.consultarVenda(email);
                        carrinhoDAO.deletarCarrinho(codigo_venda);
                    }

                    calcularTotal(email);

                }
                if (botao.equals("Aumentar")){
                    for (ItemVenda item : produtosCarrinho){
                        if (item.getLivro().getCodigo() == Integer.parseInt(livro_codigo)){
                            quantidade = item.getQuantidade() + 1;
                            preco_livro = item.getLivro().getPreco();
                        }
                    }

                    if (carrinhoDAO.verificarEstoque(Integer.parseInt(livro_codigo), quantidade)){
                        carrinhoDAO.alterarQuantidade(Integer.parseInt(livro_codigo), Integer.parseInt(item_codigo), quantidade, preco_livro);
                        carrinho = carrinhoDAO.getProdutosCarrinho(email);
                        produtosCarrinho = carrinho.getItens();

                        calcularTotal(email);
                    }
                    else {
                        erro = "Este produto não possui essa quantidade no estoque";
                    }

                }
                if (botao.equals("Diminuir")){
                    for (ItemVenda item : produtosCarrinho){
                        if (item.getLivro().getCodigo() == Integer.parseInt(livro_codigo)){
                            quantidade = item.getQuantidade() - 1;
                            preco_livro = item.getLivro().getPreco();
                        }

                    }

                    if (quantidade != 0){
                        carrinhoDAO.alterarQuantidade(Integer.parseInt(livro_codigo), Integer.parseInt(item_codigo), quantidade, preco_livro);
                        carrinho = carrinhoDAO.getProdutosCarrinho(email);
                        produtosCarrinho = carrinho.getItens();

                        calcularTotal(email);
                    }
                    else {
                        erro = "Quantidade não pode ser abaixo de 1";
                    }
                }
            }
            else {
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

    @RequestMapping(name = "carrinho", value = "/carrinho", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model,
    		HttpServletRequest request) {
        String botao= allRequestParam.get("botao");
        String email = "";
        HttpSession session = request.getSession(false);

        if (session != null)
        {
            email = (String) session.getAttribute("login_c");
            email = email == null ? "" : email;
        }

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
                        // Diminuir estoque dos produtos comprados
                        carrinhoDAO.diminuirEstoque(produtosCarrinho);
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

    /**
     * Atualiza os preços do carrinho de um email
     * @param email o email do cliente
     * @throws SQLException Trata erros de SQL
     * @throws ClassNotFoundException Trata erros de Classe não encontrada
     */
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

    private void atualizarQuantidade() throws SQLException, ClassNotFoundException {
        int estoque;
        for (ItemVenda item : produtosCarrinho){
            estoque = carrinhoDAO.retornaEstoque(item.getLivro().getCodigo());

            if (estoque == 0){
                produtosCarrinho.remove(item);
                carrinhoDAO.removerProdutoCarrinho(item.getId());
            }
            else if (item.getQuantidade() > estoque) {
                item.setQuantidade(estoque);
                carrinhoDAO.atualizarQuantidade(item.getLivro().getCodigo(), item.getId(), estoque);
            }
        }
    }

}
