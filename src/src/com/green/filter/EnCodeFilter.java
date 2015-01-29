package com.green.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EnCodeFilter implements Filter
{
  public void init(FilterConfig arg0) throws ServletException
  {
  }

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain filterChain) throws IOException, ServletException
  {
    try
    {
       request.setCharacterEncoding("utf-8");
       filterChain.doFilter(request, response);
    } catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    } 
  }

  public void destroy()
  {
  }

}
