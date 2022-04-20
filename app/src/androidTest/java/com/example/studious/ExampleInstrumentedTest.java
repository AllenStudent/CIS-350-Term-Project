package com.example.studious;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.studious", appContext.getPackageName());
    }
}


/*
    coverage: all test have to pass first
    short version:
    add: debug { testCoverageEnabled true }
    to (if not already there): buildTypes {
    have virtual device running
    run in terminal: ./gradlew createDebugCoverageReport

    work around failed tests
    add: debug { testCoverageEnabled true }
    to (if not already there): buildTypes {

    add: project.gradle.taskGraph.whenReady {
            connectedDebugAndroidTest {
                ignoreFailures = true
            }
        }
    have virtual device running
    run in terminal: ./gradlew createDebugCoverageReport
    maybe: ./gradlew createDebugCoverageReport --continue
    report in:  \CIS-350-Term-Project\app\build\reports\coverage\androidTest\debug


    ./gradlew clean


    https://developer.android.com/studio/test/command-line
    ./gradlew test
    ./gradlew connectedAndroidTest

 */