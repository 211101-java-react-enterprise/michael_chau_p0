package com.Revature.Upposit.daos;

import com.Revature.Upposit.models.Account;
import com.Revature.Upposit.models.AppUser;
import com.Revature.Upposit.util.ConnectionFactory;
import com.Revature.Upposit.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class AccountDAO implements CrudDAO<Account>{

    public Account findAccountsByUserId(String user_id) {

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
    public Account save(Account newAcc) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            newAcc.setId(UUID.randomUUID().toString());

            String sql = "insert into accounts (id, balance, creator_id, type, date_created) values (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAcc.getAcc_id());
            pstmt.setString(2, String.valueOf(newAcc.getBalance()));
            pstmt.setString(3, newAcc.getCreatorId());
            pstmt.setString(4, newAcc.getAcc_type());
//            pstmt.setString(5, newAcc.getDate_created());

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
    public Account findById(String id) {
        return null;
    }

    @Override
    public boolean update(Account updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }
}