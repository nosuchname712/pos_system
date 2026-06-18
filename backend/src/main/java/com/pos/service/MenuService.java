package main.java.com.pos.service;

import com.pos.repository.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final MenuRepository repo;

    public MenuService(MenuRepository repo) {
        this.repo = repo;
    }

    public Object getAllMenu() {
        return repo.findAll();
    }
}