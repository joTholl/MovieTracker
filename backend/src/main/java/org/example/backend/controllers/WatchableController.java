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

//    //
//    @GetMapping("/title/{title}")
//    public List<Watchable> getAllByTitle(@PathVariable String title) {
//        return watchableService.getAllByTitle(title);
//    }
//
//    // GET
//    @GetMapping("/actors/{actor}")
//    public List<Watchable> getAllByActor(@PathVariable String actor) {
//        return watchableService.getAllByActor(actor);
//    }
//
//    // GET
//    @GetMapping("/directors/{director}")
//    public List<Watchable> getAllByDirector(@PathVariable String director) {
//        return watchableService.getAllByDirector(director);
//    }
//
//    // GET
//    @GetMapping("/genres/{genre}")
//    public List<Watchable> getAllByGenre(@PathVariable String genre) {
//        return watchableService.getAllByGenre(genre);
//    }
//
//    // GET
//    @GetMapping("/year/{year}")
//    public List<Watchable> getAllByReleaseYear(@PathVariable int year) {
//        return watchableService.getAllByReleaseYear(year);
//    }

    // GET
    @GetMapping("/{id}")
    public Watchable getById(@PathVariable String id) {
        return watchableService.getById(id);
    }

    // POST
    @PostMapping
    public ResponseEntity<Watchable> create(@RequestBody WatchableInDto in) {
        Watchable createdWatchable = watchableService.create(in);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWatchable);
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
