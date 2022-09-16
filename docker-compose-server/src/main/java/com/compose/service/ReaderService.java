package com.compose.service;

import com.compose.entities.Reader;

import java.util.List;

public interface ReaderService {
    List<Reader> getReaders();

    String newReader();
}
