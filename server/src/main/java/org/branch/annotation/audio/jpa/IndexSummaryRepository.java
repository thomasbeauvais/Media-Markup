package org.branch.annotation.audio.jpa;

import org.branch.annotation.audio.model.jpa.IndexSummary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 25.05.14
 */
public interface IndexSummaryRepository<T extends IndexSummary> extends JpaRepository<T, String>
{

}
