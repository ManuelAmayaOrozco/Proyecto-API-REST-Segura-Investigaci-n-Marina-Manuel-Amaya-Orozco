package com.es.api_investigacion_marina.Repository;

import com.es.api_investigacion_marina.Model.Investigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestigacionRepository extends JpaRepository<Investigacion, Long> {}
