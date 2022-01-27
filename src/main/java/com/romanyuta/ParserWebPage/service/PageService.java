package com.romanyuta.ParserWebPage.service;

import com.romanyuta.ParserWebPage.model.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {
    public void save(Page page);
    public List<Page> getAllPage();
    public void deleteAll();
}
