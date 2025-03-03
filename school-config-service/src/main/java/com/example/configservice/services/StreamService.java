package com.example.configservice.services;

import com.example.configservice.entities.Stream;
import com.example.configservice.repositories.StreamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StreamService {

    private final StreamRepository streamRepository;

    public StreamService(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    // Retrieve all streams
    public List<Stream> getAllStreams() {
        return streamRepository.findAll();
    }

    // Retrieve a specific stream by ID
    public Optional<Stream> getStreamById(Integer id) {
        return streamRepository.findById(id);
    }

    // Create a new stream
    public Stream createStream(Stream stream) {
        return streamRepository.save(stream);
    }

    // Update an existing stream
    public Stream updateStream(Stream stream) {
        return streamRepository.save(stream);
    }

    // Delete a stream by its ID
    public void deleteStream(Integer id) {
        streamRepository.deleteById(id);
    }
}
