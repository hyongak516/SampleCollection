package com.example.util;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
public @interface MyMaker {
    String classify();
    String memo();
	}