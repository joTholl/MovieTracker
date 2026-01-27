package org.example.backend.controller;


import org.example.backend.models.Watchable;
import org.example.backend.services.WatchableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/watchables")
public class WatchableController {

    private final WatchableService watchableService;

    public WatchableController(WatchableService watchableService) {
        this.watchableService = watchableService;
    }

    // GET
    @GetMapping
    public List<Watchable> getAll() {
        return watchableService.getAll();
    }
}
