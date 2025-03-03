package com.example.configservice.controllers;

import com.example.configservice.entities.Stream;
import com.example.configservice.services.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/config/streams")
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','TEACHER')")
public class StreamController {

    private final StreamService streamService;

    @Autowired
    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    // Get all streams
    @GetMapping
    public List<Stream> getAllStreams() {
        return streamService.getAllStreams();
    }

    // Get a specific stream by ID
    @GetMapping("/{id}")
    public Optional<Stream> getStreamById(@PathVariable Integer id) {
        return streamService.getStreamById(id);
    }

    // Create a new stream
    @PostMapping
    public Stream createStream(@RequestBody Stream stream) {
        return streamService.createStream(stream);
    }

    // Update an existing stream
    @PutMapping("/{id}")
    public Stream updateStream(@PathVariable Integer id, @RequestBody Stream stream) {
        stream.setId(id);
        return streamService.updateStream(stream);
    }

    // Delete a stream by ID
    @DeleteMapping("/{id}")
    public void deleteStream(@PathVariable Integer id) {
        streamService.deleteStream(id);
    }
}
