package servlet;

import Data.Controller;
import Data.Partner;
import Data.Project;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/CreateProject"})
public class SqlServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessionObj = request.getSession();
        Controller con = (Controller) sessionObj.getAttribute("Controller");
        if (con == null) {
            //session start
            con = Controller.getInstance();
            sessionObj.setAttribute("Controller", con);
        } else {
            con = (Controller) sessionObj.getAttribute("Controller");
        }

        String command = request.getParameter("command");
        if (command == null) {
            getProjects(request, response, con);
        } else if (command.equals("view")) {
            getProjects(request, response, con);
        } else if (command.equals("create")) {
            createProject(request, response, con);
            getProjects(request, response, con);
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

    private void createProject(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        Boolean success = true;

        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        String partner = request.getParameter("partnerName"); //should be partnerID

        Long funds = null;

        Project p = null;
        if (request.getParameter("funds").length() > 0) {
            funds = Long.parseLong(request.getParameter("funds"));
            p = con.createProject(name, desc, 1, funds);
        }
        else {
            p = con.createProject(name, desc, 1);
        }
        
        if (p == null) {
            success = false;
        }

        request.setAttribute("pro", success);

        request.setAttribute("project", p);

    }

    private void getProjects(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Project> projects = con.getProjects();
        if (projects.size() <= 0) {
            System.out.println("Empty List");
        }
        if (projects == null) {
//            projects = new ArrayList<>();
//            projects.add(new Project("name"));
        }

        request.setAttribute("projects", projects);

        getStateNames(request, response, con);
        getPartnerInfo(request, response, con);

        RequestDispatcher rq = request.getRequestDispatcher("view.jsp");
        rq.forward(request, response);
    }

    private void getStateNames(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<String> stateNames = con.getStateNames();

        request.setAttribute("stateNames", stateNames);
    }
    
    private void getPartnerInfo(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Partner> partnerInfo = con.getParnerInfo();
        
        request.setAttribute("partnerInfo", partnerInfo);
    }

}
