package servlet;

import Data.Controller;
import Data.Partner;
import Data.Person;
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
                // personID - used for displaying details of individual persons
                String personID = request.getParameter("personID");
                String id = request.getParameter("id");
                if (id != null) {
                    showDetails(id, request, response, con);
                } else if (personID != null) {
                    showPersonDetails(personID, request, response, con);
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
                        case "showPersons":
                            showPersons(request, response, con);
                            break;
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
                        case "next":
                            String id2 = request.getParameter("id");
                            updateProjectState(1, Integer.parseInt(id2), request, response, con);
                            showDetails(id2, request, response, con);
                            break;
                        case "back":
                            String id3 = request.getParameter("id");
                            updateProjectState(-1, Integer.parseInt(id3), request, response, con);
                            showDetails(id3, request, response, con);
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
            if (state < 9) {
                state = state + direction;
                project.setFkProjetStateID(state);
                if (state==9)
            {
                useFunds((int) project.getFundsAllocated(), request, response, con);
            }
            }
        } //back
        else if (direction == -1) {
            if (state > 0) {
                state = state + direction;
                project.setFkProjetStateID(state);
                if(state==8)
            {
               useFunds(-(int) project.getFundsAllocated(), request, response, con); 
            }
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

        Long funds = Long.parseLong(request.getParameter("funds"));
        if (checkFunds(funds, request, response, con)) {

            con.saveProject(Integer.parseInt(request.getParameter("id")), name, desc, partnerID, funds);
        } else {
            request.setAttribute("enoughmoney", false);
            System.out.println("NOT ENOUGH MONEY MAN!");
            showEdit(request, response, con);
        }

    }

    private void showPersonDetails(String personID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Person> persons = getPersons(request, response, con);
        int pid = Integer.parseInt(personID);
        System.out.println("persons.get(personID): " + persons.get(pid).getName() + " Id " + pid);

        getProjects(request, response, con);
        request.setAttribute("personName", con.getPerson().getName());
        request.setAttribute("person", persons.get(pid - 1));
        RequestDispatcher rq = request.getRequestDispatcher("personDetails.jsp");
        rq.forward(request, response);
    }

    private void showEdit(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getStateNames(request, response, con);
        getPartnerInfo(request, response, con);

        request.setAttribute("project", getProject(Integer.parseInt(request.getParameter("id")), request, response, con));
        request.setAttribute("personName", con.getPerson().getName());
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

        Project p = null;

        if (request.getParameter("funds").length() > 0) {
            Long funds = Long.parseLong(request.getParameter("funds"));
            if (checkFunds(funds, request, response, con)) {
                p = con.createProject(name, desc, partner, funds);
            } else {
                request.setAttribute("enoughmoney", false);
                System.out.println("NOT ENOUGH MONEY MAN!");
                showCreate(request, response, con);
            }
        } else {
            p = con.createProject(name, desc, partner);
        }

        if (p == null) {
            success = false;
            request.setAttribute("pro", success);
            return;
        } else {
            createStateChange(request, response, con);

            request.setAttribute("project", p);
        }

        request.setAttribute("pro", success);
        request.setAttribute("project", p);

    }

    private boolean checkFunds(long funds, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Project> projects = getProjects(request, response, con);
        long totalFundsAllocated = funds;
        for (Project p : projects) {
            if(p.getFkProjetStateID()!=9)
            {
                if(request.getParameter("id")==null)
                {
                    totalFundsAllocated = totalFundsAllocated + p.getFundsAllocated();
                }
                else
                {
                    if(p.getId()!= Integer.parseInt(request.getParameter("id")))
                    {
                totalFundsAllocated = totalFundsAllocated + p.getFundsAllocated();
                        
                    }
                }
                
            }
        }

        System.out.println("Total funds from projects: " + totalFundsAllocated);
        if (totalFundsAllocated > getFundsAllocated(request, response, con)) {
            System.out.println("Total funds from db: " + getFundsAllocated(request, response, con));
            return false;
        } else {
            return true;
        }
    }

    private long getFundsAllocated(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getFundsAllocated();
    }
    
    private void useFunds(int amount, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException{
        con.useFunds(amount);
    }
    private void showProjects(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getProjects(request, response, con);
        getProjectsMyAction(request, response, con);
        
        request.setAttribute("numberOfUsers", (int) getNumberOfUsers(request, response, con));
        request.setAttribute("numberOfPartners", (int) getNumberOfPartners(request, response, con));
        request.setAttribute("personName", con.getPerson().getName());
        request.setAttribute("moneyLeft", (long)con.getFundsAllocated());
        RequestDispatcher rq = request.getRequestDispatcher("view.jsp");
        rq.forward(request, response);
    }

    private void showPersons(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getPersons(request, response, con);

        request.setAttribute("numberOfUsers", (int) getNumberOfUsers(request, response, con));
        request.setAttribute("numberOfPartners", (int) getNumberOfPartners(request, response, con));
        request.setAttribute("personName", con.getPerson().getName());
        getPartnerInfo(request, response, con);
        getProjects(request, response, con);
        RequestDispatcher rq = request.getRequestDispatcher("viewPersons.jsp");
        rq.forward(request, response);
    }

    private ArrayList<Project> getProjects(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Project> projects;
        if ((int) request.getSession().getAttribute("partnerID") != 1) {
            projects = con.getProjects(((int) request.getSession().getAttribute("partnerID")));
        } else {
            projects = con.getProjects();
        }

        if (projects.size() <= 0) {
            System.out.println("Empty List");
        }
        
        request.setAttribute("partnerID", request.getSession().getAttribute("partnerID"));
        request.setAttribute("projects", projects);
        getStateNames(request, response, con);
        getPartnerInfo(request, response, con);

        return projects;
    }
    private ArrayList<Project> getProjectsMyAction(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Project> projects;
        
            if ((int) request.getSession().getAttribute("partnerID") != 1) {
            projects = con.getProjectsMyAction(((int) request.getSession().getAttribute("partnerID")));
        } else {
            projects = con.getProjectsMyAction();
        }

        if (projects.size() <= 0) {
            System.out.println("My action - Empty List");
        }
        request.setAttribute("projectsMyAction", projects);
        getStateNames(request, response, con);
        getPartnerInfo(request, response, con);

        return projects;
    }

    private ArrayList<Person> getPersons(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        ArrayList<Person> persons = con.getPersons();

        if (persons.size() <= 0) {
            System.out.println("Persons: Empty List");
        }
        request.setAttribute("persons", persons);

        return persons;
    }

    private ArrayList<String> getStateNames(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<String> stateNames = con.getStateNames();

        request.setAttribute("stateNames", stateNames);
        return stateNames;
    }

    public ArrayList<Partner> getPartnerInfo(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Partner> partnerInfo = con.getPartnerInfo();

        request.setAttribute("partnerInfo", partnerInfo);
        return partnerInfo;
    }

    // returns true, if the personID is not null, else false
    private boolean accessAllowed(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return request.getSession().getAttribute("personID") != null;
    }

    private void logOut(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        HttpSession sessionObj = request.getSession();
        sessionObj.invalidate();
        con.logOut();
        request.setAttribute("loggedOut", true);
        RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
        rq.forward(request, response);
    }

    private void logIn(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        // If the log in succeeds, returns true
        if (con.logIn(userName, password)) {
            HttpSession sessionObj = request.getSession();
            sessionObj.setAttribute("personID", con.getPerson().getID());
            sessionObj.setAttribute("partnerID", con.getPerson().getFkPartnerID());
            request.setAttribute("success", true);
            showProjects(request, response, con);
        } else {
            request.setAttribute("success", false);
            RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
            rq.forward(request, response);
        }
    }

    // Retrieves the selected project, gets other essential data and forwards
    private void showDetails(String id, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        int pid = Integer.parseInt(id);

        getProjects(request, response, con);
        request.setAttribute("project", getProject(pid, request, response, con));
        request.setAttribute("personName", con.getPerson().getName());
        RequestDispatcher rq = request.getRequestDispatcher("details.jsp");
        rq.forward(request, response);
    }

    // Gets the needed information to display the Create New Project jsp and forwards to it
    private void showCreate(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        getPartnerInfo(request, response, con);
        request.setAttribute("personName", con.getPerson().getName());
        RequestDispatcher rq = request.getRequestDispatcher("create.jsp");
        rq.forward(request, response);
    }

    private boolean createStateChange(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        Project project = getLatestProject(request, response, con);

        return con.createStateChange(project, (int) request.getSession().getAttribute("personID"));
    }

    private boolean updateStateChange(int projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        Project project = getProject(projectID, request, response, con);

        return con.createStateChange(project, (int) request.getSession().getAttribute("personID"));
    }

    // Returns a single project, selected by ID
    private Project getProject(int projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getProject(projectID);
    }

    // Returns the last project that was added to the DB
    private Project getLatestProject(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getLatestProject();
    }

    private int getNumberOfUsers(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getNumberOfUsers();
    }

    private int getNumberOfPartners(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getNumberOfPartners();
    }
}
