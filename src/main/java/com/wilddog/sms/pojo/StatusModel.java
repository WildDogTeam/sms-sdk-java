package com.wilddog.sms.pojo;

public class StatusModel {

        private int status;

        private String mobile;

        private String receiveTime;

        private String rrid;

        private String deliveryStatus;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getRrid() {
            return rrid;
        }

        public void setRrid(String rrid) {
            this.rrid = rrid;
        }

        public String getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(String deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }
    }