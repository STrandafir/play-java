package controllers.forms;

public class UserForm {

    protected String userName;
    protected String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "UserMapper{" +
                "userName='" + this.userName + '\'' +
                ", passWord='" + this.passWord + '\'' +
                '}';
    }




}
