package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.Samples;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SamplesRepository extends JpaRepository<Samples, String>
{

}
