package org.branch.annotation.audio.pojos;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/5/11
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class IndexWithSamples extends IndexSummary {
    private SampleList sampleList;

    public IndexWithSamples() {

    }

    @Lob
    @Basic( fetch = FetchType.LAZY )
    @Transactional( readOnly = true )
    public SampleList getSampleList() {
        return this.sampleList;
    }

    public void setSampleList( SampleList sampleList ) {
        this.sampleList = sampleList;
    }
}
