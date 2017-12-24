package com.dobro.remind.server.repository;

import com.dobro.remind.server.entity.Remind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemindRepository extends JpaRepository<Remind, Long> {
}
