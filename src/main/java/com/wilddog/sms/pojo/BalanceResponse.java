package com.wilddog.sms.pojo;

/**
 * Created by wilddog on 17/5/9.
 */
public class BalanceResponse extends BaseResponse{

    private String status;

    private long balance;

    private long voucherBalance;

    public BalanceResponse(boolean success, WilddogError wilddogError) {
        super(success, wilddogError);
    }

    public BalanceResponse(boolean success, WilddogError wilddogError, String status, long balance, long voucherBalance) {
        super(success, wilddogError);
        this.status = status;
        this.balance = balance;
        this.voucherBalance = voucherBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getVoucherBalance() {
        return voucherBalance;
    }

    public void setVoucherBalance(long voucherBalance) {
        this.voucherBalance = voucherBalance;
    }
}
