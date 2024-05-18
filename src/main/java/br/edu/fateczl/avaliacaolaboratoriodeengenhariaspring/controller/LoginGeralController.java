package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginGeralController 
{
	@RequestMapping(name = "login_geral", value = "/login_geral", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model)
    {
		return new ModelAndView("login_geral");
    }
	
	@RequestMapping(name = "login_geral", value = "/login_geral", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model)
    {
		return new ModelAndView("login_geral");
    }
}
