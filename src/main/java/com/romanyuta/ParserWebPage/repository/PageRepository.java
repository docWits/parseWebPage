package com.romanyuta.ParserWebPage.repository;

import com.romanyuta.ParserWebPage.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
}
