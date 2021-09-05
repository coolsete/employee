package com.io.framey.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Slf4j
@Component
public class ResourceReader {

    public String read(String fileNamePathFromClasspath) {
        try (Reader reader = getReader(fileNamePathFromClasspath)) {
            return readFromReader(reader);
        } catch (IOException e) {
            log.error("Exception during file load {};\n{}", fileNamePathFromClasspath, e);
        }
        return "";
    }

    protected Reader getReader(String fileNamePathFromClasspath) throws FileNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileNamePathFromClasspath);
        if (inputStream == null) {
            throw new FileNotFoundException("File was not found in classLoader");
        }
        return new InputStreamReader(inputStream);
    }

    protected String readFromReader(Reader reader) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
