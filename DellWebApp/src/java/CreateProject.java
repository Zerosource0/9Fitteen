/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Data.Controller;
import Data.Project;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marcj_000
 */
@WebServlet(urlPatterns = {"/CreateProject"})
public class CreateProject extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessionObj = request.getSession();
        Controller con = (Controller) sessionObj.getAttribute("Controller");
        if(con == null)
        {
            //session start
            con = Controller.getInstance();
            sessionObj.setAttribute("Controller", con);
        }
        else
        {
            con = (Controller) sessionObj.getAttribute("Controller");
        }
        
        String command = request.getParameter("command");
        if(command.equals("view")){
        getProjects(request, response, con);
        }
        else if(command.equals("create")){
        createProject(request, response, con);
        }

    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void createProject(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException
    {
        
        Boolean success = true;
        
        String name = request.getParameter("name");
        
        Project p = con.createProject(name);
        if(p == null){
            success = false;
        }
        
        request.setAttribute("pro", success);
        
        request.setAttribute("project", p);
        
        RequestDispatcher rq = request.getRequestDispatcher("Main.jsp");
        rq.forward(request, response);
    }
    
    private void getProjects(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException
    {
        ArrayList<Project> projects = con.getProjects();
        if(projects.size() >0)
        {
            System.out.println("Empty List");
        }
        if (projects == null) {
//            projects = new ArrayList<>();
//            projects.add(new Project("name"));
        }
        
        request.setAttribute("projects", projects);
        RequestDispatcher rq = request.getRequestDispatcher("view.jsp");
        rq.forward(request, response);
    }
    
}
