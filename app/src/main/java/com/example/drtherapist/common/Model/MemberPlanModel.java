package com.example.drtherapist.common.Model;

/**
 * Created by Raghvendra Sahu on 25/12/2019.
 */
public class MemberPlanModel {
    String id;
    String daytime_slot_name;
    String price;
    String plan_type;

    public MemberPlanModel(String id, String daytime_slot_name, String price, String plan_type) {
        this.id = id;
        this.daytime_slot_name = daytime_slot_name;
        this.price = price;
        this.plan_type = plan_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDaytime_slot_name() {
        return daytime_slot_name;
    }

    public void setDaytime_slot_name(String daytime_slot_name) {
        this.daytime_slot_name = daytime_slot_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }
}
