package com.company.coursework.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
public class Word {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String text;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    private Language language;
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    private Word translation;

    public Word() {
    }

    public Word(String text, Language language) {
        this.text = text;
        this.language = language;
    }

    public Word(String text, Word translation, Language language) {
        this.text = text;
        this.language = language;
        this.translation = translation;
    }

    public String getText() {
        return text;
    }

    public Word getTranslation() {
        return translation;
    }

    public void setTranslation(Word translation) {
        this.translation = translation;
    }
}
