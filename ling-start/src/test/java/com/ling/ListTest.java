package com.ling;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2023-03-10 22:58
 **/
public class ListTest {

    @Test
    public void testSize() {
        Integer expected = 100;
        List list = Mockito.mock(List.class);
        Mockito.when(list.size()).thenReturn(100);
        Integer actual = list.size();
        Assertions.assertEquals(expected, actual, "返回值不相等");
    }
}
