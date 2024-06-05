package com.ltdd.cringempone.data.dto;

import java.util.ArrayList;

public class GenreDTO {
    public String id;
    public String name;
    public String title;
    public String alias;
    public String link;
    public Parent parent;
    public ArrayList<Child> childs;
    public class Child{
        public String id;
        public String name;
        public String title;
        public String alias;
        public String link;
    }

    public class Parent{
        public String id;
        public String name;
        public String title;
        public String alias;
        public String link;
    }
}
