package com.company.coursework.repository;

import com.company.coursework.model.Dictionary;
import com.company.coursework.model.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends CrudRepository<Dictionary, Integer> {
}
