package servlet;

import Data.Comment;
import Data.Controller;
import Data.Partner;
import Data.Person;
import Data.PoeFile;
import Data.Project;
import Data.Report;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(name = "SqlServlet", urlPatterns = {"/Dashboard"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)

public class SqlServlet extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240;

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
                String partnerID = request.getParameter("partnerID");
                String personID = request.getParameter("personID");
                String id = request.getParameter("id");
                if (id != null) {
                    showDetails(id, request, response, con);
                } else if (personID != null) {
                    showPersonDetails(personID, request, response, con);
                } else if (partnerID != null) {
                    showPartnerDetails(partnerID, request, response, con);
                }// else go back to view.jsp 
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
                // Displays the main view.jsp with an overview of the projects
                if (accessAllowed(request, response, con)) {
                    
                    //This switch-case is used for navigation. 
                    //Cliking a link puts a command in the url. The switch-case then executes the right actions for the given command. 
                   switch (command) {
                        // Creates a new project in the db and forwards to view.jsp
                        case "comment":
                            String projectID = request.getParameter("projectID");
                            saveComment(projectID, request, response, con);
                            showDetails(projectID, request, response, con);
                            break;
                        case "deletePerson":
                            String personID = request.getParameter("personID");
                            deletePerson(personID, request, response, con);
                            showPersons(request, response, con);
                            break;
                        case "addPerson":
                            addPerson(request, response, con);
                            break;
                        case "showPartners":
                            showPartners(request, response, con);
                            break;
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
                            if (con.getCurrentUser().getFkPersonTypeID() == 5) {
                                showProjects(request, response, con);
                                break;
                            } else {
                                showCreate(request, response, con);
                                break;
                            }
                        case "editPartner":
                            String partnerID = request.getParameter("partnerID");
                            showPartnerEdit(partnerID, request, response, con);
                            break;
                        case "editPerson":
                            //personID = request.getParameter("personID");
                            showPersonEdit(Integer.parseInt(request.getParameter("personID")), request, response, con);
                            break;
                        case "edit":
                            showEdit(request, response, con);
                            break;
                        case "save":
                            saveProject(request, response, con);
                            String id = request.getParameter("id");
                            showDetails(id, request, response, con);
                            break;
                        case "savePerson":
                            savePerson(request, response, con);
                            personID = request.getParameter("personID");
                            showPersonDetails(personID, request, response, con);
                            break;
                        case "showReport":
                            showReport(request, response, con);
                            break;
                        case "savePartner":
                            savePartner(request, response, con);
                            partnerID = request.getParameter("partnerID");
                            showPartnerDetails(partnerID, request, response, con);
                            break;
                        case "logout":
                            logOut(request, response, con);
                            break;
                        case "password":
                            String current = request.getParameter("current");
                            String password = request.getParameter("new");
                            String retype = request.getParameter("retype");
                            changePassword(current, password, retype, request, response, con);
                            break;
                        case "next":
                            String id2 = request.getParameter("id");
                            updateProjectState(1, Integer.parseInt(id2), con);
                            showDetails(id2, request, response, con);
                            break;
                        case "settings":
                            showSettings(request, response, con);
                            break;
                        case "upload":
                            upload(request, con);
                            showProjects(request, response, con);
                            break;
                        case "getFile":
                            getFile(request, response, con);
                            break;
                        case "back":
                            String id3 = request.getParameter("id");
                            updateProjectState(-1, Integer.parseInt(id3), con);
                            showDetails(id3, request, response, con);
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

    
    //Gets an ArrayList of comment objects from the database. 
   private void getComments(String projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Comment> comments = con.getComments(Integer.parseInt(projectID));

        request.setAttribute("comments", comments);
    }
   
    //Saves comment from input box in the .jsp to the database
    private void saveComment(String pid, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        int projectID = Integer.parseInt(pid);
        System.out.println("Project ID: " + projectID);

        Project project = getProject(projectID, request, response, con);

        String comment = request.getParameter("comment");
        System.out.println("Comment: " + comment);

        con.saveComment(project, (int) request.getSession().getAttribute("personID"), comment);
    }

    //(Not in use) deletes selected person from database 
    private void deletePerson(String personID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        System.out.println("DELETING PERSONID: " + personID);
        con.deletePerson(Integer.parseInt(personID));
    }
    
    //(Not in use) add a new person to the database, then forwards to the PersonUpdate.jsp
    private void addPerson(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        Person person = con.addPerson();
        int personID = person.getID();
        showPersonEdit(personID, request, response, con);
    }
    
    //Updates project states (i.e. Fund Allocation, Reimburse)
    private void updateProjectState(int direction, int id, Controller con) throws ServletException, IOException {

        con.updateProjectState(id, direction);
        //updateStateChange(id, request, response, con);
    }
    
    //Used for updating partner information in the partnerUpdate.jsp
    private void savePartner(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        int zip = Integer.parseInt(request.getParameter("zip"));
        String country = request.getParameter("country");
        int partnerID = 1;
        if (request.getParameter("partnerID") != null) {
            partnerID = Integer.parseInt(request.getParameter("partnerID"));
        }
        con.savePartner(partnerID, name, address, zip, country);
    }

    
    private Report getReport(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getReport();
    }

    private void showReport(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        getProjects(request, response, con);
        getProjectsMyAction(request, response, con);
        getCountries(request, response, con);
        getReport(request, response, con);

        request.setAttribute("numberOfUsers", (int) getNumberOfUsers(con));
        request.setAttribute("numberOfPartners", (int) getNumberOfPartners(request, response, con));
        request.setAttribute("personName", con.getCurrentUser().getName());
        request.setAttribute("moneyLeft", (long) con.getFundsAllocated());
        request.setAttribute("report", getReport(request, response, con));
        RequestDispatcher rq = request.getRequestDispatcher("report.jsp");
        rq.forward(request, response);
    }

    //Gets an ArrayList of Strings containg countries from the database. 
    private ArrayList<String> getCountries(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<String> countries = con.getCountries();

        request.setAttribute("countries", countries);
        return countries;
    }

    //Used for updatng person info in the personUpdate.jsp. 
   private void savePerson(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        String name = request.getParameter("name");
        int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
        int personTypeID = Integer.parseInt(request.getParameter("personTypeID"));
        int partnerID = 1;
        if (request.getParameter("partnerID") != null) {
            partnerID = Integer.parseInt(request.getParameter("partnerID"));
        }
        String email = request.getParameter("email");
        con.savePerson(Integer.parseInt(request.getParameter("personID")), name, phoneNumber, personTypeID, partnerID, email);
    }

    //Used for updating project info in the projectUpdate.jsp.
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

    //Used in the viewPartners.jsp. Gets seleced partner from the Database, then forwards to partnerDetails.jsp
    private void showPartnerDetails(String partnerID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Partner> partners = getPartnerInfo(request, response, con);
        int pid = Integer.parseInt(partnerID);
        request.setAttribute("partner", partners.get(pid - 1));
        request.setAttribute("personName", con.getCurrentUser().getName());
        request.setAttribute("rights", con.getCurrentUser().getFkPersonTypeID());
        getProjects(request, response, con);

        RequestDispatcher rq = request.getRequestDispatcher("partnerDetails.jsp");
        rq.forward(request, response);
    }

    //Used in the viewPersons.jsp. Gets the selected person from the Database, then frowards to partnerDtails.jsp.
    private void showPersonDetails(String personID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        int pid = Integer.parseInt(personID);

        getProjects(request, response, con);
        request.setAttribute("personName", con.getCurrentUser().getName());
        request.setAttribute("person", con.getPerson(pid));
        request.setAttribute("rights", con.getCurrentUser().getFkPersonTypeID());
        RequestDispatcher rq = request.getRequestDispatcher("personDetails.jsp");
        rq.forward(request, response);
    }

    //Used in the partnerDetails.jsp for updating partner information. Forwards to partnerUpdate.jsp.
    private void showPartnerEdit(String partnerID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Partner> partners = getPartnerInfo(request, response, con);
        int pid = Integer.parseInt(partnerID);
        System.out.println("persons.get(personID): " + partners.get(pid).getPartnerName() + " Id " + pid);

        getProjects(request, response, con);
        request.setAttribute("personTypes", con.getPersonTypes());
        request.setAttribute("personName", con.getCurrentUser().getName());
        request.setAttribute("partner", partners.get(pid - 1));
        request.setAttribute("rights", con.getCurrentUser().getFkPersonTypeID());
        RequestDispatcher rq = request.getRequestDispatcher("partnerUpdate.jsp");
        rq.forward(request, response);
    }

    //Used in the personDetails.jsp for updating partner information. Forwards to personUpdate.jsp.
    private void showPersonEdit(int personID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getProjects(request, response, con);
        request.setAttribute("personTypes", con.getPersonTypes());
        request.setAttribute("personName", con.getCurrentUser().getName());
        request.setAttribute("person", con.getPerson(personID));
        request.setAttribute("rights", con.getCurrentUser().getFkPersonTypeID());
        RequestDispatcher rq = request.getRequestDispatcher("personUpdate.jsp");
        rq.forward(request, response);
    }

    //Used in the details.jsp for updating partner information. forwards to update.jsp 
   private void showEdit(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getStateNames(request, response, con);
        getPartnerInfo(request, response, con);

        request.setAttribute("project", getProject(Integer.parseInt(request.getParameter("id")), request, response, con));
        request.setAttribute("personName", con.getCurrentUser().getName());
        request.setAttribute("rights", con.getCurrentUser().getFkPersonTypeID());
        RequestDispatcher rq = request.getRequestDispatcher("update.jsp");
        rq.forward(request, response);
    }

    //Used in the create.jsp. Creates a new project from the input in the form and stores it in the database.
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
            if (p.getFkProjetStateID() != 9) {
                if (request.getParameter("id") == null) {
                    totalFundsAllocated = totalFundsAllocated + p.getFundsAllocated();
                } else {
                    if (p.getId() != Integer.parseInt(request.getParameter("id"))) {
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

    private void useFunds(int amount, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        con.useFunds(amount);
    }
    
    //The initail view after logging in, the showProject forwards to the view.jsp that shows all projects in the database.
    private void showProjects(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getProjects(request, response, con);
        getProjectsMyAction(request, response, con);

        request.setAttribute("numberOfUsers", (int) getNumberOfUsers(con));
        request.setAttribute("numberOfPartners", (int) getNumberOfPartners(request, response, con));
        request.setAttribute("personName", con.getCurrentUser().getName());
        request.setAttribute("moneyLeft", (long) con.getFundsAllocated());
        RequestDispatcher rq = request.getRequestDispatcher("view.jsp");
        rq.forward(request, response);
    }

    //Forwards to viewPersons.jsp, a list of all users in the database.
    private void showPersons(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getPersons(request, response, con);

        request.setAttribute("numberOfUsers", (int) getNumberOfUsers(con));
        request.setAttribute("numberOfPartners", (int) getNumberOfPartners(request, response, con));
        request.setAttribute("personName", con.getCurrentUser().getName());
        getPartnerInfo(request, response, con);
        getProjects(request, response, con);
        RequestDispatcher rq = request.getRequestDispatcher("viewPersons.jsp");
        rq.forward(request, response);
    }
    
    //Forwards to viewPartners.jsp, a list fo all partners in teh databse.
    private void showPartners(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        getPartnerInfo(request, response, con);

        request.setAttribute("numberOfUsers", (int) getNumberOfUsers(con));
        request.setAttribute("numberOfPartners", (int) getNumberOfPartners(request, response, con));
        request.setAttribute("personName", con.getCurrentUser().getName());
        getProjects(request, response, con);
        RequestDispatcher rq = request.getRequestDispatcher("viewPartners.jsp");
        rq.forward(request, response);
    }

    /**
     * Returns an ArrayList with all the projects that the current user has
     * permission to see.
     *
     * @param request
     * @param response
     * @param con
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private ArrayList<Project> getProjects(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<Project> projects = con.getProjects((int) request.getSession().getAttribute("partnerID"));

        request.setAttribute("partnerID", request.getSession().getAttribute("partnerID"));
        request.setAttribute("rights", con.getCurrentUser().getFkPersonTypeID());
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
    
    //Returns an ArrayList of person objects. Used for the viewPersons.jsp.
    private ArrayList<Person> getPersons(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        ArrayList<Person> persons = con.getPersons();

        if (persons.size() <= 0) {
            System.out.println("Persons: Empty List");
        }
        request.setAttribute("persons", persons);

        return persons;
    }

    //Returns an ArrayList of Strings with all the available states (I.E. Fund Allocation, Reimburse). Used when updating projects.
    private ArrayList<String> getStateNames(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        ArrayList<String> stateNames = con.getStateNames();

        request.setAttribute("stateNames", stateNames);
        return stateNames;
    }
    
    //Gets an ArrayList of Partner objects. Used for the viewPartners.jsp.
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
            sessionObj.setAttribute("personID", con.getCurrentUser().getID());
            sessionObj.setAttribute("partnerID", con.getCurrentUser().getFkPartnerID());
            request.setAttribute("success", true);
            showProjects(request, response, con);
        } else {
            request.setAttribute("success", false);
            RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
            rq.forward(request, response);
        }
    }

    private void changePassword(String current, String password, String retype, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        String message = null;
        if (con.checkPassword(current)) {
            System.out.println("Password checked ...");
            if (con.changePassword(password, retype)) {
                System.out.println("Password updated ...");
                message = "Password successfully updated!";
            } else {
                System.out.println("Update failed ...");
                message = "Update failed. Try again.";
            }
        } else {
            System.out.println("Check failed ...");
            message = "The entered password does not match your current one.";
        }

        request.setAttribute("message", message);

        showSettings(request, response, con);

    }

    // Retrieves the selected project, gets other essential data and forwards
    private void showDetails(String id, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        int pid = Integer.parseInt(id);

        getProjects(request, response, con);
        getComments(id, request, response, con);
        getNumberOfPoe(pid, request, con);
        getPoeFiles(pid, request, con);
        request.setAttribute("project", getProject(pid, request, response, con));
        request.setAttribute("personName", con.getCurrentUser().getName());
        RequestDispatcher rq = request.getRequestDispatcher("details.jsp");
        rq.forward(request, response);
    }

    // Gets the needed information to display the Create New Project jsp and forwards to it
    private void showCreate(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {

        getPartnerInfo(request, response, con);
        request.setAttribute("rights", con.getCurrentUser().getFkPersonTypeID());
        request.setAttribute("personName", con.getCurrentUser().getName());
        RequestDispatcher rq = request.getRequestDispatcher("create.jsp");
        rq.forward(request, response);
    }

    /**
     * Creates a StateChange entry in the DB for the latest project.
     *
     * @param request
     * @param response
     * @param con
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean createStateChange(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.createStateChange(con.getLatestProject(), (int) request.getSession().getAttribute("personID"));
    }

    private boolean updateStateChange(int projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        Project project = getProject(projectID, request, response, con);

        return con.createStateChange(project, (int) request.getSession().getAttribute("personID"));
    }

    private void showSettings(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        request.setAttribute("rights", con.getCurrentUser().getFkPersonTypeID());
        request.setAttribute("personName", con.getCurrentUser().getName());

        RequestDispatcher rq = request.getRequestDispatcher("settings.jsp");
        rq.forward(request, response);
    }

    // Returns a single project, selected by ID
    private Project getProject(int projectID, HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getProject(projectID);
    }

    private int getNumberOfUsers(Controller con) throws ServletException, IOException {
        return con.getNumberOfUsers();
    }

    private int getNumberOfPartners(HttpServletRequest request, HttpServletResponse response, Controller con) throws ServletException, IOException {
        return con.getNumberOfPartners();
    }

    private void getFile(HttpServletRequest request, HttpServletResponse response, Controller con) {
        int projectID = Integer.parseInt(request.getParameter("id"));
        int i = Integer.parseInt(request.getParameter("file"));

        System.out.println("Trying to get file for project id " + projectID + " and index " + i + ".");

        try (OutputStream output = response.getOutputStream();) {
            PoeFile file = con.getPoeFile(projectID, i);
            String contentType = getServletContext().getMimeType(file.getExtension());
            System.out.println("Content type: " + contentType);
            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setHeader("Content-Length", String.valueOf(file.getData().length));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + file.getExtension() + "\"");
            response.setContentType(contentType);

            output.write(con.getImage(projectID, i));
        } catch (IOException ex) {
            Logger.getLogger(SqlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getNumberOfPoe(int projectID, HttpServletRequest request, Controller con) {
        request.setAttribute("poe", con.getNumberOfPoe(projectID));
    }

    //Gets an ArrayList of POE files(Proof of Exection) corresponding to the projectID from the Database.
    private void getPoeFiles(int projectID, HttpServletRequest request, Controller con) {
        request.setAttribute("poeFiles", con.getPoeFileList(projectID));
    }

    private void upload(HttpServletRequest request, Controller con) throws ServletException, IOException {
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        int projectStateID = Integer.parseInt(request.getParameter("projectStateID"));
        Part filePart = request.getPart("file");
        
        if (filePart != null) {
            try (InputStream inputStream = filePart.getInputStream();) {
                con.upload(projectID, projectStateID, inputStream, filePart.getSubmittedFileName());
            }

        } else {
            System.out.println("filePart is NULL");
        }

        /*
         // gets absolute path of web app
         String appPath = "D:/";

         // upload directory
         String savePath = appPath + File.separator + SAVE_DIR;

         // create dir if non existent
         File fileSaveDir = new File(savePath);
         if (!fileSaveDir.exists()) {
         fileSaveDir.mkdir();
         }

         System.out.println("Save Path: " + savePath);
        
         for (Part part : request.getParts()) {
         if (part.getName().equals("file")) {
         String fileName = extractFileName(part);
         System.out.println("File name: " + fileName + " " + part.getName());
         try {
         part.write("/" + fileName);
         } catch (IOException e) {
         e.printStackTrace();
         }
         }

         }

         request.setAttribute("message", "Upload has been successfully done!");

         }

         private String extractFileName(Part part) {
         String contentDisp = part.getHeader("content-disposition");
         String[] items = contentDisp.split(";");
         for (String s : items) {
         if (s.trim().startsWith("filename")) {

         s = s.substring(s.indexOf("=") + 2, s.length() - 1);
         while (s.contains("\\")) {
         //int i = s.charAt(s.indexOf("\\"));
         s = s.substring(1);
         }
         return s;
         }
         }
         return "";
         */
    }

    private static int getIntFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Integer.parseInt(sb.toString());

    }
}
