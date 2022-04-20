package com.example.studious;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuildConfigTest {

    @Test
    public void randomTest() {
        BuildConfig buildConfig = new BuildConfig();
        assertNotNull(buildConfig);
    }

}