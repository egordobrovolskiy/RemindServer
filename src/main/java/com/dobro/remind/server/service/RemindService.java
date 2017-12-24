package com.dobro.remind.server.service;

import com.dobro.remind.server.entity.Remind;

import java.util.List;

public interface RemindService {

    List<Remind> getAll();
    Remind getByID(long id);
    Remind save(Remind remind);
    void remove(long id);
}