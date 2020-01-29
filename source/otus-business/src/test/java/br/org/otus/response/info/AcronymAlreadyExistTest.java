package br.org.otus.response.info;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcronymAlreadyExistTest {

  @Test
  public void method_build_should_return_instanceOf_AcronymAlreadyExist() {
    assertTrue(AcronymAlreadyExist.build() instanceof AcronymAlreadyExist);
  }
}
