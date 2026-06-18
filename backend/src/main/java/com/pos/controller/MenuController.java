package main.java.com.pos.controller;

import com.pos.service.MenuService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping
    public Object getMenu() {
        return service.getAllMenu();
    }
}