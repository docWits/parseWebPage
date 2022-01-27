package com.romanyuta.ParserWebPage.api;

import com.romanyuta.ParserWebPage.job.ParseTask;
import com.romanyuta.ParserWebPage.model.Page;
import com.romanyuta.ParserWebPage.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PageController {

    @Autowired
    PageService pageService;
    @Autowired
    ParseTask parseTask;

    @GetMapping(value = "/page")
    public List<Page> getAllPage(@RequestParam(value = "url",required = false,defaultValue = "https://www.simbirsoft.com/")String url){
        pageService.deleteAll();
        return parseTask.parseAllText(url);
    }
}
