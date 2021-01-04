package br.org.otus;

import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class LoggerTestsParent {

  protected Logger LOGGER;

  protected void setUpLogger(Class testClass) throws Exception {
    LOGGER = Mockito.mock(Logger.class);
    setFinalStatic(testClass.getDeclaredField("LOGGER"), LOGGER);
  }

  private static void setFinalStatic(Field field, Object newValue) throws Exception {
    field.setAccessible(true);
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    field.set(null, newValue);
  }

  protected void verifyLoggerSevereWasCalled(){
    verify(LOGGER, times(1)).severe(Mockito.anyString());
  }

  protected void verifyLoggerInfoWasCalled(){
    verify(LOGGER, times(1)).info(Mockito.anyString());
  }
}
