package org.example.backend.controllers;

import org.example.backend.dtos.FilterDto;
import org.example.backend.dtos.WatchableInDto;
import org.example.backend.models.Watchable;
import org.example.backend.services.WatchableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Filter;

@RestController
@RequestMapping("/api/watchables")
public class WatchableController {

    private final WatchableService watchableService;

    public WatchableController(WatchableService watchableService) {
        this.watchableService = watchableService;
    }

    // GET
    @GetMapping()
    public List<Watchable> getAll() {
        return watchableService.getAll();
    }

    // GET
    @GetMapping("/filtered")
    public List<Watchable> getAllByFilters(@RequestBody List<FilterDto> filters){
        return watchableService.getAllByMultipleFilters(filters);
    }

    // GET
    @GetMapping("/{id}")
    public Watchable getById(@PathVariable String id) {
        return watchableService.getById(id);
    }

    // POST
    @PostMapping
    public ResponseEntity<Watchable> create(@RequestBody WatchableInDto in) {
        return ResponseEntity.status(HttpStatus.CREATED).body(watchableService.create(in));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {

       if (watchableService.deleteById(id)){
           return ResponseEntity.noContent().build();
       }

       return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // PUT
    @PutMapping("/{id}")
    public Watchable update(@PathVariable String id, @RequestBody WatchableInDto in) {
        return watchableService.update(id, in);
    }
}
