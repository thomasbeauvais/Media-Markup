package org.branch.annotation.audio.model.dao;

import javax.persistence.*;
import java.util.List;
import java.util.Vector;

/**
 * Heavy weight representation of the {@link IndexSummary} that also includes the {@link org.branch.annotation.audio.model.Sample samples}.
 */
@Entity
public class IndexAnnotations extends IndexSummary
{
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ElementCollection(targetClass = Annotation.class)
    private List<Annotation> annotations = new Vector<Annotation>();

    public List<Annotation> getAnnotations()
    {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations)
    {
        this.annotations = annotations;
    }
}
