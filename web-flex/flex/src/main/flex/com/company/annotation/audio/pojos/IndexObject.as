package com.company.annotation.audio.pojos {
import mx.collections.ArrayCollection;

[RemoteClass(alias="com.company.annotation.audio.pojos.IndexObject")]
public class IndexObject extends IndexSummary {
    public var samples:Array;
    public var min:int;
    public var mix:int;
}
}