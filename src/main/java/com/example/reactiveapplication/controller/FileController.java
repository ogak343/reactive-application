package com.example.reactiveapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private static final int CHUNK_SIZE = 100 * 1024;

    @GetMapping("/file/stream")
    public Flux<DataBuffer> streamFile() throws IOException {
        File file = new File("C:\\Users\\khamd\\IdeaProjects\\reactive-application\\src\\main\\resources\\static\\file.png");

        byte[] bytes = Files.readAllBytes(file.toPath());

        Flux<byte[]> chunkFlux = Flux.range(0, (int) Math.ceil((double) bytes.length / CHUNK_SIZE))
                .map(chunkIndex -> {
                    int start = chunkIndex * CHUNK_SIZE;
                    int end = Math.min(start + CHUNK_SIZE, bytes.length);
                    return new byte[end - start];
                });

        return chunkFlux.concatMap(bytesArray ->
                Flux.just(bytesArray)
                        .delayElements(Duration.ofSeconds(1))
                        .map(array -> new DefaultDataBufferFactory().wrap(array))
        );
    }
}
