package org.example.backend.controller;

import org.example.backend.models.Watchable;
import org.example.backend.services.WatchableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{id}")
    public Watchable getById(@PathVariable String id) {
        return watchableService.getById(id);
    }

    // POST
    @PostMapping
    public ResponseEntity<Watchable> create(@RequestBody Watchable watchable) {
        Watchable createdWatchable = watchableService.create(watchable);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWatchable);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        watchableService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PUT
    @PutMapping("/{id}")
    public Watchable update(@PathVariable String id, @RequestBody Watchable watchable) {
        return watchableService.update(id, watchable);
    }
}
