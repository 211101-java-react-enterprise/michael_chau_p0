package com.Revature.Upposit.models;

public class Account extends Object{

    private String acc_id;
    private double balance;
    private String creator;
    private String date_created;
    private String acc_type;


    public Account(String creator_id, String type, String bal){
        this.creator = creator_id;
        this.acc_type = type;
        this.balance = Double.parseDouble(bal);
    }

    public Account(){super();}

    public String getAcc_id() { return acc_id;}
    public void setId(String id) { this.acc_id = id;}

    public double getBalance() { return balance;}
    public void setBalance(Double bal) { this.balance = bal;}
    public void setBalance(String bal) { this.balance = Double.parseDouble(bal);}


    public String getCreatorId() { return creator;}
    public void setCreator(String creator) { this.creator = creator;}

    public String getDate_created() { return date_created;}
    public void setDate_created(String date) { this.date_created = date;}

    public String getAcc_type() { return acc_type;}
    public void setAcc_type(String type) { this.acc_type = type;}


}
