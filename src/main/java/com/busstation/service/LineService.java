package com.busstation.service;

import com.busstation.model.Line;
import com.busstation.repository.LineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LineService {

    private final LineRepository lineRepository;

    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    public Line create(String start, String end) {
        return lineRepository.save(new Line(start, end));
    }

    public List<Line> all() {
        return lineRepository.findAll();
    }

    public Line get(Long id) {
        return lineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Line not found"));
    }

    public void delete(Long id) {
        lineRepository.deleteById(id);
    }
}
