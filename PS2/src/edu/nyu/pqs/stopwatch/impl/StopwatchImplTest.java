package edu.nyu.pqs.stopwatch.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import edu.nyu.pqs.stopwatch.api.Stopwatch;

class StopwatchImplTest {
  private StopwatchImpl stopwatch;
  private static final String id = "123";
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  void testGetId() {
    stopwatch = new StopwatchImpl(id);
    String watchID = stopwatch.getId();
    assertEquals(id, watchID);
  }
  
  @Test
  void testStart() {
    stopwatch = new StopwatchImpl(id);
    assertEquals(false, stopwatch.isRunning());
    stopwatch.start();
    assertEquals(true, stopwatch.isRunning());
  }
  
  @Test
  void testStartThrowIllegalStateException() {
    stopwatch = new StopwatchImpl(id);
    stopwatch.start();
    IllegalStateException e = assertThrows(IllegalStateException.class, stopwatch::start);
  }
  
  @Test
  void testLap() throws InterruptedException {
    stopwatch = new StopwatchImpl(id);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.lap();
    Thread.sleep(200);
    stopwatch.lap();
    assertEquals(2, stopwatch.getLapTimes().size());
  }
  
  @Test
  void testStop() throws InterruptedException {
    stopwatch = new StopwatchImpl(id);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.stop();
    assertEquals(false, stopwatch.isRunning());
  }
  
  @Test
  void testStopThenLap()  throws InterruptedException {
    stopwatch = new StopwatchImpl(id);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.stop();
    Thread.sleep(100);
    IllegalStateException e = assertThrows(IllegalStateException.class, stopwatch::lap);

  }
  
  @Test
  void testStopThenStart() throws InterruptedException {
    stopwatch = new StopwatchImpl(id);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.stop();
    Thread.sleep(100);
    stopwatch.start();
    Thread.sleep(100);
    assertEquals(1, stopwatch.getLapTimes().size());
  }
  
  @Test
  void testStopThenStartThenLap() throws InterruptedException {
    stopwatch = new StopwatchImpl(id);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.stop();
    Thread.sleep(100);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.lap();
    assertEquals(1, stopwatch.getLapTimes().size());
  }
  
  @Test
  void testStopThenStartThenLapThenStopThenStartThenLap() throws InterruptedException {
    stopwatch = new StopwatchImpl(id);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.stop();
    Thread.sleep(100);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.lap();
    Thread.sleep(100);
    stopwatch.stop();
    Thread.sleep(100);
    stopwatch.start();
    Thread.sleep(100);
    stopwatch.lap();
    assertEquals(2, stopwatch.getLapTimes().size());
  }
  
  @Test
  void testReset() {
    stopwatch = new StopwatchImpl(id);
    stopwatch.start();
    stopwatch.lap();
    stopwatch.lap();
    assertEquals(true, stopwatch.isRunning());
    assertEquals(2,stopwatch.getLapTimes().size());
    stopwatch.reset();
    assertEquals(false, stopwatch.isRunning());
    assertEquals(0,stopwatch.getLapTimes().size());
  }
}