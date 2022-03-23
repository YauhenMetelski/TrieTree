package com.metelski.prefixtree.controller;

import com.metelski.prefixtree.service.WordCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller returns count of the word in text
 */
@RequiredArgsConstructor
@RestController
public class WordCounterController {

    private final WordCounterService wordCounterService;

    /**
     * Returns count of the word in text
     *
     * @param word word for searching
     */
    @GetMapping
    public Integer getWordCount(@RequestParam("word") final String word) {
        return wordCounterService.countWords(word);
    }
}
