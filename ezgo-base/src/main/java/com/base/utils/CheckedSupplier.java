package com.base.utils;

public interface CheckedSupplier<R> {
    R get() throws Exception;
}
