package com.company.coursework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Dictionary {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String title;
    @OneToOne
    private Language language1;
    @OneToOne
    private Language language2;


    public Dictionary() {
    }

    public Dictionary(String title, Language language1, Language language2) {
        this.title = title;
        this.language1 = language1;
        this.language2 = language2;
    }

    public String getTitle() {
        return title;
    }

    public Language getLanguage1() {
        return language1;
    }

    public Language getLanguage2() {
        return language2;
    }

    public void setLanguage1(Language language1) {
        this.language1 = language1;
    }

    public void setLanguage2(Language language2) {
        this.language2 = language2;
    }
}
