package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.dao.db.MpaDb;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaDb mpaDb;

    public Mpa getMpaById(int id) {
        try {
            return mpaDb.getMpaById(id);
        } catch (Exception e) {
            throw new NotFoundException("Мпа не найден");
        }
    }

    public List<Mpa> getAllMpa() {
        try {
            return mpaDb.getAllMpa()
                    .stream()
                    .sorted((o1, o2) -> o1.getId() - o2.getId())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new NotFoundException("Мпа не найден");
        }
    }
}
