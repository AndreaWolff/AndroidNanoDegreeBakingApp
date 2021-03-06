package com.andrea.bakingapp;

import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.MockitoAnnotations;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

public class BaseUnitTest {

    @BeforeClass
    public static void createMainThreadScheduler() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}
