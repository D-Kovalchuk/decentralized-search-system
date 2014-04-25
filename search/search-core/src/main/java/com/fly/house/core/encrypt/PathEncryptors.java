package com.fly.house.core.encrypt;

import com.fly.house.core.encrypt.exception.DecodePathException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static java.util.Objects.isNull;

/**
 * Created by dimon on 3/28/14.
 */
@Component
public class PathEncryptors {

    @Value("${salt}")
    private String salt;

    @Value("${depth}")
    private int depth;

    private Base64.Encoder encoder;
    private Base64.Decoder decoder;

    public PathEncryptors(String salt, int depth) {
        this();
        this.salt = salt;
        this.depth = depth;
    }

    public PathEncryptors() {
        encoder = getEncoder();
        decoder = getDecoder();
    }

    public String encode(Path path) {
        if (isNull(path)) {
            return null;
        }
        String pathString = path.toString();
        String s = salt.concat(pathString);
        for (int i = 0; i < depth; i++) {
            s = encoder.encodeToString(s.getBytes());
        }
        return s;
    }

    public Path decode(String securePath) {
        if (isNull(securePath)) {
            return null;
        }
        byte[] s;
        try {
            s = decoder.decode(securePath);
            for (int i = 0; i < depth - 1; i++) {
                s = decoder.decode(s);
            }
        } catch (IllegalArgumentException e) {
            throw new DecodePathException("cannot decode an hash", e);
        }

        String decodedPath = new String(s).substring(salt.length());
        return Paths.get(decodedPath);
    }

}
