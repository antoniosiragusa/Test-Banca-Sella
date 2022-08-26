package com.app.testbancasella.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.testbancasella.model.Movimenti;

public interface MovimentiRepository extends JpaRepository<Movimenti, String> {

}
