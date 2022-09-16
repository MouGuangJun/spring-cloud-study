package com.compose.controller;

import com.compose.entities.Reader;
import com.compose.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReaderController {
    @Autowired
    private ReaderService readerService;


    @GetMapping("getReaders")
    public List<Reader> getReaders() {
        return readerService.getReaders();
    }


    @GetMapping("newReader")
    public String newReader() {
        return readerService.newReader();
    }
}
