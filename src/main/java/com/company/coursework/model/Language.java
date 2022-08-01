package com.company.coursework.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Language {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "language")
    private List<Word> wordList;


    public Language() {
    }

    public Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Word> getWordList() {
        return wordList;
    }
}
