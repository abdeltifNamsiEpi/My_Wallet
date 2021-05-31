package com.example.mywallet;

public class Income {
    String uid,uemail,incomeCategory,incomeMoney;

    public Income(String uid, String uemail, String incomeCategory, String incomeMoney) {
        this.uid = uid;
        this.uemail = uemail;
        this.incomeCategory = incomeCategory;
        this.incomeMoney = incomeMoney;
    }

    public String getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(String incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public String getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(String incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public Income() {
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


}
