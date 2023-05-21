package com.ltdd.cringempone.ui.person;

import java.util.List;

public class CategoryPersonItem {
    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public void setPersonItems(List<PersonItem> personItems) {
        this.personItems = personItems;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public List<PersonItem> getPersonItems() {
        return personItems;
    }

    private String nameCategory;

    public CategoryPersonItem(String nameCategory, List<PersonItem> personItems) {
        this.nameCategory = nameCategory;
        this.personItems = personItems;
    }

    private List<PersonItem> personItems;


}
