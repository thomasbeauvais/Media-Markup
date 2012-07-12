package com.company.annotation.audio.pojos;

import com.company.common.dao.Identifiable;
import org.hibernate.annotations.GenericGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @Basic( optional = false )
    public SampleList getSampleList() {
        return this.sampleList;
    }

    public void setSampleList( SampleList sampleList ) {
        this.sampleList = sampleList;
    }
}
