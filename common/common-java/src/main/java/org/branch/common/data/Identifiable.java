package org.branch.common.data;

import java.io.Serializable;

public interface Identifiable<T> extends Serializable {
    T getId();
}
