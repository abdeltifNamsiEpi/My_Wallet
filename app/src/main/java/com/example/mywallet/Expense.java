package com.example.mywallet;

public class Expense {
    String uid,uemail,expenseCategory,expenseMoney;

    public Expense(String uid, String uemail, String expenseCategory, String expenseMoney) {
        this.uid = uid;
        this.uemail = uemail;
        this.expenseCategory = expenseCategory;
        this.expenseMoney = expenseMoney;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseMoney() {
        return expenseMoney;
    }

    public void setExpenseMoney(String expenseMoney) {
        this.expenseMoney = expenseMoney;
    }

    public Expense() {
    }


}
