package com.bytedance.pojo;

import javax.persistence.Entity;
import java.io.Serializable;


public class Bank implements Serializable{
    /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	private Integer account;

    private String password;

    private Integer money;

    private String realname;

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }
}