package datamappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import controllers.forms.UserForm;
import play.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Singleton
public class UserMapper {

    private Database db;

    @Inject
    public UserMapper(Database db) {
        this.db = db;
    }


    public HashMap<Integer,HashMap<String,String>> getUsers(){
        Connection con = this.db.getConnection();
        try {
            PreparedStatement prepared = con.prepareStatement("SELECT id,user_name,password FROM play.user_account;");
            HashMap<Integer,HashMap<String,String>> result = new HashMap<>();
            ResultSet rs = prepared.executeQuery();
            while(rs.next()){
                HashMap<String, String> buff = new HashMap<>();
                buff.put(rs.getString("user_name"),rs.getString("password"));
                result.put(rs.getInt("id"), buff);
            }
            System.out.println(result);
            return result;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean addUser(UserForm adf){
        Connection con = this.db.getConnection();
        try {
            PreparedStatement prepared = con.prepareStatement("INSERT INTO  play.user_account (`user_name`,`password`) VALUES (?,?);");
            prepared.setString(1,adf.getUserName());
            prepared.setString(2,adf.getPassWord());
            prepared.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error");
            return false;
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public boolean deleteUser(Integer id){
        Connection con = this.db.getConnection();
        try {
            PreparedStatement prepared = con.prepareStatement("DELETE FROM play.user_account WHERE id = ?;");
            prepared.setInt(1,id);
            prepared.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error");
            return false;
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public boolean checkCredentials(UserForm adf){
        Connection con = this.db.getConnection();
        try {
            PreparedStatement prepared = con.prepareStatement("SELECT id,user_name,password FROM play.user_account WHERE user_name = ? and password = ?;");

            prepared.setString(1,adf.getUserName());
            prepared.setString(2,adf.getPassWord());
            HashMap<Integer,HashMap<String,String>> result = new HashMap<>();
            ResultSet rs = prepared.executeQuery();
            while(rs.next()){
                HashMap<String, String> buff = new HashMap<>();
                buff.put(rs.getString("user_name"),rs.getString("password"));
                result.put(rs.getInt("id"), buff);
            }
            System.out.println(prepared.toString());
            if(result.isEmpty()){
                return false;
            }


        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
