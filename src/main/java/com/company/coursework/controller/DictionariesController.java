package com.company.coursework.controller;

import com.company.coursework.model.Dictionary;
import com.company.coursework.model.Language;
import com.company.coursework.model.User;
import com.company.coursework.model.Word;
import com.company.coursework.repository.DictionaryRepository;
import com.company.coursework.repository.LanguageRepository;
import com.company.coursework.repository.UserRepository;
import com.company.coursework.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Executable;
import java.util.Comparator;
import java.util.Optional;

@SessionAttributes({"language", "isAdmin"})
@Controller
public class DictionariesController {


    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Language english1 = new Language("English");
        Language english2 = new Language("English");
        Language russian = new Language("Russian");
        Language ukrainian = new Language("Ukrainian");
        languageRepository.save(english1);
        languageRepository.save(english2);
        languageRepository.save(ukrainian);
        languageRepository.save(russian);
        dictionaryRepository.save(new Dictionary("Eng-Ukr dict", english1, ukrainian));
        dictionaryRepository.save(new Dictionary("Eng-Rus dict", english2, russian));

        addWord("Anxiety", "Тривога", english1, ukrainian);
        addWord("Baby", "Дитина", english1, ukrainian);
        addWord("Content", "Зміст", english1, ukrainian);
        addWord("Data", "Дані", english1, ukrainian);
        addWord("Empty", "Порожній", english1, ukrainian);
        addWord("Far", "Далеко", english1, ukrainian);
        addWord("Guy", "Хлопець", english1, ukrainian);
        addWord("Hair", "Волосся", english1, ukrainian);
        addWord("Ice", "Лід", english1, ukrainian);
        addWord("Job", "Робота", english1, ukrainian);
        addWord("Kiss", "Поцілунок", english1, ukrainian);
        addWord("Long", "Довго", english1, ukrainian);
        addWord("Man", "Людина", english1, ukrainian);
        addWord("Night", "Ніч", english1, ukrainian);
        addWord("Our", "Наші", english1, ukrainian);
        addWord("Program", "Програма", english1, ukrainian);
        addWord("Queue", "Черга", english1, ukrainian);
        addWord("Rhyth", "Ритм", english1, ukrainian);
        addWord("Soap", "Мило", english1, ukrainian);
        addWord("Tail", "Хвіст", english1, ukrainian);
        addWord("Union", "Союз", english1, ukrainian);
        addWord("Vessel", "Судно", english1, ukrainian);
        addWord("Wall", "Стіна", english1, ukrainian);
        addWord("Xenon", "Ксенон", english1, ukrainian);
        addWord("Yard", "Двір", english1, ukrainian);
        addWord("Zero", "Нуль", english1, ukrainian);

        addWord("Anxiety", "Беспокойство", english2, russian);
        addWord("Baby", "Малыш", english2, russian);
        addWord("Content", "Содержание", english2, russian);
        addWord("Data", "Данные", english2, russian);
        addWord("Empty", "Пустой", english2, russian);
        addWord("Far", "Далеко", english2, russian);
        addWord("Guy", "Парень", english2, russian);
        addWord("Hair", "Волосы", english2, russian);
        addWord("Ice", "Лед", english2, russian);
        addWord("Job", "Работа", english2, russian);
        addWord("Kiss", "Поцелуй", english2, russian);
        addWord("Long", "Длинный", english2, russian);
        addWord("Man", "Мужчина", english2, russian);
        addWord("Night", "Ночь", english2, russian);
        addWord("Our", "Наш", english2, russian);
        addWord("Program", "Программа", english2, russian);
        addWord("Queue", "Очередь", english2, russian);
        addWord("Rhyth", "Ритм", english2, russian);
        addWord("Soap", "Мыло", english2, russian);
        addWord("Tail", "Хвост", english2, russian);
        addWord("Union", "Союз", english2, russian);
        addWord("Vessel", "Cосуд", english2, russian);
        addWord("Wall", "Cтена", english2, russian);
        addWord("Xenon", "Ксенон", english2, russian);
        addWord("Yard", "Площадка", english2, russian);
        addWord("Zero", "Нуль", english2, russian);

    }

    @GetMapping("/")
    public String index(Model model) {
        User admin;
        Optional<User> optionalUser = userRepository.findByUsername("root");
        if (optionalUser.isPresent()) {
            admin = optionalUser.get();
        } else {
            admin = new User("root", "password", true);
            userRepository.save(admin);
        }

        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @PostMapping("/logIn")
    public String logIn(Model model,
                        @RequestParam String username,
                        @RequestParam String password) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                Boolean isAdmin = user.isAdmin();
                model.addAttribute("isAdmin", isAdmin);
                return "redirect:/dictionaries";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/continueAsGuest")
    public String continueAsGuest(Model model) {
        model.addAttribute("isAdmin", false);
        return "redirect:/dictionaries";
    }

    @GetMapping("/dictionaries")
    public String dictionaries(Model model,
                               @ModelAttribute("isAdmin") Boolean isAdmin) {

        model.addAttribute("dictionaries", dictionaryRepository.findAll());
        model.addAttribute("isAdmin", isAdmin);
        return "dictionaries";
    }

    @GetMapping("/dictionaries/editDictionaries")
    public String editDictionaries(Model model) {
        model.addAttribute("editDictionaries", dictionaryRepository.findAll());
        return "editDictionaries";
    }

    @PostMapping("/dictionaries/editDictionaries")
    public String createDictionary(@RequestParam String title,
                                   @RequestParam String languageName1,
                                   @RequestParam String languageName2) {
        Language language1 = new Language(languageName1);
        Language language2 = new Language(languageName2);
        languageRepository.save(language1);
        languageRepository.save(language2);
        dictionaryRepository.save(new Dictionary(title, language1, language2));
        return "redirect:/dictionaries/editDictionaries";
    }

    @PostMapping("/dictionaries/editDictionaries/delete/{dictionaryId}")
    public String deleteDictionary(@PathVariable int dictionaryId) {
        dictionaryRepository.deleteById(dictionaryId);
        return "redirect:/dictionaries/editDictionaries";
    }

    @GetMapping("/dictionaries/{dictionaryId}")
    public String dictionary(Model model,
                             @PathVariable int dictionaryId,
                             @RequestParam(required = false) String languageName,
                             @ModelAttribute("isAdmin") Boolean isAdmin) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId).get();
        Language language;
        if (dictionary.getLanguage2().getName().equals(languageName)) {
            language = dictionary.getLanguage2();
        } else {
            language = dictionary.getLanguage1();
        }
        language.getWordList().sort(Comparator.comparing(Word::getText));
        model.addAttribute("dictionary", dictionary);
        model.addAttribute("language", language);
        model.addAttribute("isAdmin", isAdmin);
        return "dictionary";
    }

    @GetMapping("/dictionaries/{dictionaryId}/editDictionary")
    public String editDictionary(Model model,
                                 @PathVariable int dictionaryId,
                                 @RequestParam(required = false) String languageName) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId).get();
        Language language;
        if (dictionary.getLanguage2().getName().equals(languageName)) {
            language = dictionary.getLanguage2();
        } else {
            language = dictionary.getLanguage1();
        }
        language.getWordList().sort(Comparator.comparing(Word::getText));
        model.addAttribute("editDictionary", dictionary);
        model.addAttribute("language", language);
        return "editDictionary";
    }

    @PostMapping("/dictionaries/{dictionaryId}/editDictionary")
    public String addWord(@PathVariable int dictionaryId,
                          @RequestParam String text,
                          @RequestParam String translation,
                          @ModelAttribute("language") Language language) {
        Language language2;
        Dictionary dictionary = dictionaryRepository.findById(dictionaryId).get();
        if (dictionary.getLanguage1().getName().equals(language.getName())) {
            language2 = dictionary.getLanguage2();
        } else {
            language2 = dictionary.getLanguage1();
        }
        Word word = new Word(text, language);
        Word wordTranslation = new Word(translation, word, language2);
        word.setTranslation(wordTranslation);
        wordRepository.save(word);
        wordRepository.save(wordTranslation);
        return "redirect:/dictionaries/" + dictionaryId + "/editDictionary";
    }

    @PostMapping("/dictionaries/{dictionaryId}/editDictionary/delete/{wordId}")
    public String deleteWord(@PathVariable int dictionaryId,
                             @PathVariable int wordId) {
        wordRepository.deleteById(wordId);
        return "redirect:/dictionaries/" + dictionaryId + "/editDictionary";
    }

    private void addWord(String text, String translation, Language language, Language language2) {
        Word word = new Word(text, language);
        Word wordTranslation = new Word(translation, word, language2);
        word.setTranslation(wordTranslation);
        wordRepository.save(word);
        wordRepository.save(wordTranslation);
    }
}
