package servlet;

import Data.Controller;
import Data.Partner;
import Data.Person;
import Data.Project;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SqlServlet", urlPatterns = {"/Dashboard"})
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

        // Determine what action will be executed by the Servlet
        String command = request.getParameter("command");

        // If there is no command, check for other parameters
        if (command == null) {
            if (accessAllowed(request, response, con)) {
                // Id - used for displaying details of individual projects
                String id = request.getParameter("id");
                if (id != null) {
                    showDetails(id, request, response, con);
                } // else go back to view.jsp 
                else {
                    showProjects(request, response, con);
                }
            } else {
                request.setAttribute("access", false);
                RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
                rq.forward(request, response);
            }
        } else {
            if (command.equals("login")) {
                logIn(request, response, con);
            } else {
                if (accessAllowed(request, response, con)) {
                    // Displays the main view.jsp with an overview of the projects
                    switch (command) {
                        // Creates a new project in the db and forwards to view.jsp
                        case "view":
                            showProjects(request, response, con);
                            break;
                        // checks the log in data and forwards accordingly
                        case "create":
                            createProject(request, response, con);
                            showProjects(request, response, con);
                            break;
                        case "showCreate":
                            showCreate(request, response, con);
                            break;
                        case "edit":
                            showEdit(request, response, con);
                            break;
                        case "save":
                            saveProject(request, response, con);
                            String id = request.getParameter("id");
                            showDetails(id, request, response, con);
                            break;
                        case "logout":
                            logOut(request, response, con);
                            break;
                        default:
                            response.sendRedirect("index.jsp");
                            break;
                    }
                } else {
                    request.setAttribute("access", false);
                    RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
                    rq.forward(request, response);
                }
            }

        }

        // If there is no command, check for other parameters
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

    private void updateProjectState(int direction, int id, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        Project project = getProject(id, request, response, con);

        int state = project.getFkProjetStateID();
        //next
        if (direction == 1) {
            if (state < 8) {
                state = state + direction;
                project.setFkProjetStateID(state);
            }
        }
        //back
        if (direction == -1) {
            if (state > 0) {
                state = state + direction;
                project.setFkProjetStateID(state);
            }
        }

        con.updateProjectState(project);
        updateStateChange(project.getId(), request, response, con);
    }

    private void saveProject(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        int partnerID = 1;
        if (request.getParameter("partnerID") != null) {
            partnerID = Integer.parseInt(request.getParameter("partnerID")); //should be partnerID
        }
        Long funds = null;

        funds = Long.parseLong(request.getParameter("funds"));

        System.out.println("FUNDS! :  " + funds);

        Project project = null;

        project = con.saveProject(Integer.parseInt(request.getParameter("id")), name, desc, partnerID, funds);

    }

    private void showEdit(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        getStateNames(request, response, con);
        getPartnerInfo(request, response, con);
        Project p = getProject(Integer.parseInt(request.getParameter("id")), request, response, con);
        System.out.println("SOUT PROJECT P: " + p);

        request.setAttribute("project", p);
        RequestDispatcher rq = request.getRequestDispatcher("update.jsp");
        rq.forward(request, response);
    }

    private void createProject(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        Boolean success = true;

        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        int partner = 1;
        if (request.getParameter("partnerID") != null) {
            partner = Integer.parseInt(request.getParameter("partnerID")); //should be partnerID
        }

        System.out.println("INT PARTNER SOUT: " + request.getParameter("partnerID"));

        Long funds = null;

        Project p = null;

        if (request.getParameter("funds").length() > 0) {
            funds = Long.parseLong(request.getParameter("funds"));
            p = con.createProject(name, desc, partner, funds);
        } else {
            p = con.createProject(name, desc, partner);
        }

        if (p == null) {
            success = false;
        } else {
            createStateChange(p.getId(), request, response, con);

        }

        request.setAttribute("pro", success);
        request.setAttribute("project", p);

    }

 

    private void showProjects(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getProjects(request, response, con);

        request.setAttribute("numberOfUsers", (int) getNumberOfUsers(request, response, con));
        request.setAttribute("numberOfPartners", (int) getNumberOfPartners(request, response, con));
        RequestDispatcher rq = request.getRequestDispatcher("view.jsp");
        rq.forward(request, response);
    }

    private ArrayList getProjects(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Project> projects = con.getProjects();
        if (projects.size() <= 0) {
            System.out.println("Empty List");
        }

        request.setAttribute("projects", projects);
        getStateNames(request, response, con);
        getPartnerInfo(request, response, con);

        return projects;
    }

    private ArrayList getStateNames(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<String> stateNames = con.getStateNames();

        request.setAttribute("stateNames", stateNames);
        return stateNames;
    }

    public ArrayList getPartnerInfo(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Partner> partnerInfo = con.getParnerInfo();

        request.setAttribute("partnerInfo", partnerInfo);
        return partnerInfo;
    }

       private boolean accessAllowed(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        HttpSession sessionObj = request.getSession();
        if (sessionObj.getAttribute("personID") == null) {
            return false;
        } else {
            return true;
        }
    }

    private void logOut(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        HttpSession sessionObj = request.getSession();
        sessionObj.invalidate();
        request.setAttribute("loggedOut", true);
        RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
        rq.forward(request, response);
        //response.sendRedirect("logOut.jsp");

    }
    private void logIn(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        Boolean success = false;

        Person person = con.logIn(userName, password);

        if (person == null) {
            request.setAttribute("success", success);
            RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
            rq.forward(request, response);

        } else {
            HttpSession sessionObj = request.getSession();
            sessionObj.setAttribute("personID", person.getID());
            success = true;
            request.setAttribute("success", success);
            showProjects(request, response, con);

        }

    }

    private void showDetails(String id, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        int pid = Integer.parseInt(id);

        getProjects(request, response, con);
        ArrayList<Project> projects = (ArrayList<Project>) request.getAttribute("projects");
        ArrayList<Project> pro = new ArrayList<>();
        Predicate<Project> predicate = (p) -> p.getId() == pid;

        projects.stream().filter(predicate).forEach(p -> pro.add(p));

        request.setAttribute("project", pro.get(0));

        RequestDispatcher rq = request.getRequestDispatcher("details.jsp");
        rq.forward(request, response);
    }

    private void showCreate(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        getPartnerInfo(request, response, con);

        RequestDispatcher rq = request.getRequestDispatcher("create.jsp");
        rq.forward(request, response);
    }

    private boolean createStateChange(int projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        boolean success = false;
        Project project = getLatestProject(projectID, request, response, con);

        success = con.createStateChange(project, (int) request.getSession().getAttribute("personID"));
        return success;
    }

    private boolean updateStateChange(int projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        boolean success = false;
        Project project = getProject(projectID, request, response, con);

        success = con.createStateChange(project, (int) request.getSession().getAttribute("personID"));
        return success;
    }

    private Project getProject(int projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        Project project = con.getProject(projectID);
        return project;
    }

    private Project getLatestProject(int projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        Project project = con.getLatestProject(projectID);
        return project;
    }

    private int getNumberOfUsers(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getNumberOfUsers();
    }

    private int getNumberOfPartners(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getNumberOfPartners();
    }
}
