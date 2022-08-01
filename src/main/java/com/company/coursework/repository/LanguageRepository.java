package com.company.coursework.repository;

import com.company.coursework.model.Language;
import com.company.coursework.model.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Integer> {
}
