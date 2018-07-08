package mvc.controller;

import javax.servlet.http.HttpServletRequest;

import mvc.ImController;
import mvc.ImRequestMapping;
import mvc.View;
import mvc.ViewData;
import mvc.WebContext;

@ImController
public class LoginController {
     //使用RequestMapping注解指明forward1方法的访问路径  
     @ImRequestMapping("Login/Login2")
     public View forward1(){
    	 System.out.println("进入Login/Login2");
         //执行完forward1方法之后返回的视图
         return new View("/login2.jsp");  
     }
     
     //使用RequestMapping注解指明forward2方法的访问路径  
     @ImRequestMapping("Login/Login3")
     public View forward2(){
         //执行完forward2方法之后返回的视图
         return new View("/login3.jsp");  
     } 
     
     @ImRequestMapping("Login/fix")
     public View fix() {
    	 ViewData viewData = new ViewData();
    	 HttpServletRequest request = WebContext.getRequest();
    	 String username = request.getParameter("username");
    	 String pwd = request.getParameter("pwd");
    	 if(username.equals("1")) {
    		 viewData.put("msg", "登陆成功");
    	 }else {
    		 viewData.put("msg", "错误");
    	 }
    	 return new View("/login2.jsp");
     }
}
