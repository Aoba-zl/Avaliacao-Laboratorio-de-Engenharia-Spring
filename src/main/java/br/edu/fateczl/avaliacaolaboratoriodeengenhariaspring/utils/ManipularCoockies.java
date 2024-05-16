package br.edu.fateczl.avaliacaolaboratoriodeengenhariaspring.utils;

import jakarta.servlet.http.Cookie;

public class ManipularCoockies
{
    public String buscaValorCookie(String coockieName, Cookie[] cookies)
    {
        if (cookies.length > 2)
        {
            for (Cookie cookie : cookies)
            {
                String name = cookie.getName();
                if (name.equalsIgnoreCase(coockieName))
                    return cookie.getValue();
            }
        }
        return "";
    }
}