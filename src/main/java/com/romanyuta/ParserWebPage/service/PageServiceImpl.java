package com.romanyuta.ParserWebPage.service;

import com.romanyuta.ParserWebPage.model.Page;
import com.romanyuta.ParserWebPage.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    PageRepository pageRepository;

    @Override
    public void save(Page page) {
        pageRepository.save(page);
    }

    @Override
    public List<Page> getAllPage() {
        return pageRepository.findAll();
    }

    @Override
    public void deleteAll() {
        pageRepository.deleteAll();
    }
}
