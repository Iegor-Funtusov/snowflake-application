package com.smartfoxpro.snowflake.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 11:56 AM
 */
public interface StorageFileService {

    void init();
    void store(MultipartFile file);
    Stream<Path> loadAll();
    Path load(String filename);
    Resource loadAsResource(String filename);
    void deleteAll();
    void delete(String filename);
}
