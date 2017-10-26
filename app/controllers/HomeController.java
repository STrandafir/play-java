package controllers;

import controllers.composers.LoggedUser;
import controllers.forms.UserForm;
import datamappers.UserMapper;
import play.data.FormFactory;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    private FormFactory theForm;
    @Inject
    private FormFactory formFactory;
    @Inject
    private UserMapper um;

    public Result index() {
        return ok(index.render());
    }

    @With(LoggedUser.class)
    public Result stelica() {
        return ok(views.html.stelica.render(Arrays.asList("bla","tra","lala")));
    }

    @With(LoggedUser.class)
    public Result renderUserForm(){
        HashMap<Integer,HashMap<String,String>> availableUsers = um.getUsers();
        System.out.println(availableUsers);
        play.data.Form<UserForm> theForm = formFactory.form(UserForm.class);
        return ok(views.html.form.render(theForm,availableUsers));
    }

    @With(LoggedUser.class)
    public Result addUser(){
        play.data.Form<UserForm> theForm = formFactory.form(UserForm.class).bindFromRequest();
        if (theForm.hasErrors()) {
            return badRequest("not ok");
        } else {
            UserForm adf = theForm.get();
            boolean checkInsert = um.addUser(adf);
            if(checkInsert){
                return redirect("/userForm");
            }else{
                return redirect("/userForm");
            }

        }
    }
    @With(LoggedUser.class)
    public Result deleteUser(Integer id){
        um.deleteUser(id);
        return redirect("/userForm");
    }

    public Result checkCredentials(){
        play.data.Form<UserForm> theForm = formFactory.form(UserForm.class).bindFromRequest();
        if(theForm.hasErrors()){
            return badRequest("Aoleo, n ai cont :(");
        }
        else{
            UserForm input = theForm.get();
            boolean checkForm = um.checkCredentials(input);
            if(checkForm){
                session("user",input.getUserName());
                return redirect(routes.HomeController.renderUserForm());
            }else{
                return badRequest("Aoleo, n ai cont :(");
            }
        }
    }

    @With(LoggedUser.class)
    public Result logout(){
        session().remove("user");
        return redirect("/");
    }
}
