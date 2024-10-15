package com.example.crudtable;

public class userData {
    private String id;
    private String name;
    private String courseYr;
    private String department;

    public userData(){

    }

    public userData(String id, String name, String courseYr, String department){
        this.id = id;
        this.name = name;
        this.courseYr = courseYr;
        this.department = department;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getCourseYr(){
        return courseYr;
    }

    public String getDepartment(){
        return department;
    }
}
