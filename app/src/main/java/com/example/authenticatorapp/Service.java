package com.example.authenticatorapp;

public class Service {
    private String _ID;
    private String _name;
    private String _forms;
    private String _documents;
    private String _rate;
    private String _branch;

    public Service(){

    }
    public Service(String name,String forms, String documents,String rate,String branch,String ID){
        _ID = ID;
        _branch = branch;
        _name = name;
        _forms = forms;
        _documents= documents;
        _rate = rate;
    }

    public void setName(String name){
        _name = name;
    }

    public String getName(){
        return _name;
    }

    public void setForms(String forms){
        _forms=forms;
    }

    public String getForms(){
        return _forms;
    }

    public void setDocuments(String documents){
        _documents = documents;
    }

    public String getDocuments(){
        return _documents;
    }

    public void setRate(String rate){
        _rate = rate;
    }

    public String getRate(){
        return _rate;
    }

    public void setBranch(String branch){
        _branch = branch;
    }

    public String getBranch(){
        return _branch;
    }

    public void setID(String ID){
        _ID = ID;
    }

    public String getID(){
        return _ID;
    }

}
