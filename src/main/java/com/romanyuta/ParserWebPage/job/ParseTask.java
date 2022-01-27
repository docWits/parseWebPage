package com.romanyuta.ParserWebPage.job;

import com.romanyuta.ParserWebPage.model.Page;
import com.romanyuta.ParserWebPage.service.PageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ParseTask {

    @Autowired
    PageService pageService;

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
        Map<K,V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K,V>> st = map.entrySet().stream();

        st.sorted(Comparator.comparing(e -> e.getValue()))
                .forEach(e ->result.put(e.getKey(),e.getValue()));

        return result;
    }

    //@Scheduled(fixedDelay = 500000)
    public List<Page> parseAllText(String url){
        //String url = "https://www.simbirsoft.com/";
        ArrayList<String> listOfSeparators = new ArrayList<String>();
        listOfSeparators.add(" ");
        listOfSeparators.add(",");
        listOfSeparators.add(".");
        listOfSeparators.add("!");
        listOfSeparators.add("?");
        listOfSeparators.add("\"");
        listOfSeparators.add("«");
        listOfSeparators.add("»");
        listOfSeparators.add("(");
        listOfSeparators.add(")");
        listOfSeparators.add("[");
        listOfSeparators.add("]");
        listOfSeparators.add("-");
        listOfSeparators.add(";");
        listOfSeparators.add(":");
        listOfSeparators.add("\n");
        listOfSeparators.add("\r");
        listOfSeparators.add("\t");

        try {
            Document page = Jsoup.connect(url)
                    .timeout(3000)
                    .get();
            String pagetext = page.text();
            String separatorsString = String.join("|\\", listOfSeparators);
            Map<String, Word> countMap = new HashMap<String, Word>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(pagetext.getBytes(StandardCharsets.UTF_8))));
            String line;
            while ((line = reader.readLine()) != null){
                String [] words = line.split(separatorsString);
                for (String word : words){
                    if ("".equals(word)){
                        continue;
                    }

                    Word wordObj = countMap.get(word);
                    if (wordObj == null){
                        wordObj = new Word();
                        wordObj.word = word;
                        wordObj.count = 0;
                        countMap.put(word,wordObj);
                    }
                    wordObj.count++;
                }
            }
            reader.close();
//            SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
//
//            Map<String,Word> sortedWords = countMap.entrySet().stream()
//                    .sorted(Map.Entry.comparingByKey())
//                    .collect(Collectors.toMap(Map.Entry :: getKey, Map.Entry :: getValue,
//                            (oldValue,newValue) -> oldValue, LinkedHashMap::new));

            Map<String,Word> sortedWords = sortByValue(countMap);

            for (Word word : sortedWords.values()){
                Page obj = new Page();
                obj.setWord(word.word);
                obj.setCount(word.count);
                pageService.save(obj);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageService.getAllPage();
    }

    public static class Word implements Comparable<Word>{
        String word;
        int count;

        @Override
        public int hashCode(){
            return word.hashCode();
        }

        @Override
        public boolean equals(Object obj){
            return word.equals(((Word)obj).word);
        }

        @Override
        public int compareTo(Word b){
            return b.count - count;
        }
    }
}
