package com.es.api_investigacion_marina.Repository;

import com.es.api_investigacion_marina.Model.Pez;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Repositorio encargado de la clase Pez.
 */
@Repository
public interface PezRepository extends JpaRepository<Pez, Long> {}
