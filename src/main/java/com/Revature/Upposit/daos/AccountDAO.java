package com.Revature.Upposit.daos;

import com.Revature.Upposit.models.Account;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.util.ArrayDeque;
import com.Revature.Upposit.util.ConnectionFactory;
import com.Revature.Upposit.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class AccountDAO implements CrudDAO<Account>{

    public ArrayDeque<Account> findAccountsByUserId(String user_id) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            // Returns a list of accounts based off user id.
            String sql = "select * from accounts where creator_id = ? order by date_created";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_id);
            ResultSet result = pstmt.executeQuery();
            ArrayDeque<Account>  listResult = new ArrayDeque<>();

            while (result.next()) {
                Account acc = new Account();
                acc.setId(result.getString("id"));
                acc.setBalance(result.getString("balance"));
                acc.setCreator(result.getString("creator_id"));
                acc.setDate_created(result.getString("date_created"));
                acc.setAcc_type(result.getString("acc_type"));

                listResult.add(acc);
            }

            return listResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Account save(Account newAcc) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            newAcc.setId(UUID.randomUUID().toString());

            String sql = "insert into accounts (id, balance, creator_id, acc_type) values (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAcc.getAcc_id());
            pstmt.setDouble(2, newAcc.getBalance());
            pstmt.setString(3, newAcc.getCreatorId());
            pstmt.setString(4, newAcc.getAcc_type());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return newAcc;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account findById(String user_id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from accounts where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_id);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) { //currently, only gets first
                Account acc = new Account();
                acc.setId(result.getString("id"));
                acc.setBalance(result.getString("balance"));
                acc.setCreator(result.getString("creator_id"));
                acc.setDate_created(result.getString("date_created"));
                acc.setAcc_type(result.getString("type"));
                return acc;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean update(Account acc) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "update accounts set balance = ? where id = ?;";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1,acc.getBalance());
            pstmt.setString(2,acc.getAcc_id());
            int rowsUpdated = pstmt.executeUpdate();

            if(rowsUpdated != 0) {
                return true;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

}
