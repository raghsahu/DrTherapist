package com.example.drtherapist.therapist.model;

import java.io.Serializable;

/**
 * Created by Ravindra Birla on 02/05/2019.
 */
public class CategoryListData implements Serializable {
    public String id;
    public String name;
    public String image;

    /*public CategoryListData(String string) {
        name = string;
        job_id = "-1";
    }*/

    @Override
    public String toString() {
        return name;
    }
}
