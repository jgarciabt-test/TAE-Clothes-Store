package com.tae.store;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;




@Config(emulateSdk=18 ,reportSdk = 10)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

	private MainActivity activity;

	@Before
	public void setup() {
		activity = Robolectric.buildActivity(MainActivity.class)
        .create().start().resume().get();
	}

	@Test
	public void checkActivityNotNull() throws Exception {
		assertNotNull(activity);
	}
	
//  @Test
//  public void shouldHaveNewFragment() throws Exception
//  {
//    MainActivity newActivity = new MainActivity();
//      assertNotNull(newActivity.getFragmentManager().findFragmentById(R.id.new_fragment));
//  }

}
