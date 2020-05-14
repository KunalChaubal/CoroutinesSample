package com.coroutines.sample.extensions


fun <T> lazyN(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)