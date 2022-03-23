package com.metelski.prefixtree.service;

import com.metelski.prefixtree.storage.Trie;
import com.metelski.prefixtree.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Service for counting word in text
 */
@Slf4j
@Service
public class WordCounterService implements ApplicationRunner {
    @Value("${file.path}")
    private String filePath;
    public final Trie<Integer> trie = new Trie<>();

    /**
     * Returns count of the word in text
     *
     * @param word word for searching
     */
    public int countWords(final String word) {
        Integer wordCount = trie.find(word.toLowerCase(Locale.ROOT));
        return wordCount == null ? 0 : wordCount;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("File upload started");
        FileHelper.processTextFileByWords(filePath, s -> trie.computeOrInsert(s.toLowerCase(Locale.ROOT), 1, v -> ++v));
        log.info("File upload finished");
    }
}
