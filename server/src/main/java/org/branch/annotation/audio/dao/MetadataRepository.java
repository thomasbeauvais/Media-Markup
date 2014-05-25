package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.IndexSummary;
import org.branch.annotation.audio.model.dao.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 25.05.14
 */
public interface MetadataRepository extends JpaRepository<Metadata, String>
{
}
