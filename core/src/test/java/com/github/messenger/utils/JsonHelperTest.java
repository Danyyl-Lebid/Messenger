package com.github.messenger.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.messenger.entity.Message;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class JsonHelperTest {

    private static String collectionString = "[{\"id\":1,\"userId\":1,\"chatId\":1,\"nickname\":\"nickname1\",\"text\":\"text1\",\"time\":1},{\"id\":2,\"userId\":2,\"chatId\":2,\"nickname\":\"nickname2\",\"text\":\"text2\",\"time\":2}]";

    private static Collection<Message> messageCollection = new ArrayList<>(
            Arrays.asList(
                    new Message(1L, 1L, 1L, "nickname1", "text1", 1L),
                    new Message(2L, 2L, 2L, "nickname2", "text2", 2L)
            )
    );

    @Test
    public void toJsonCollection(){
        Assert.assertEquals(collectionString, JsonHelper.toJson(messageCollection).orElseThrow());
    }

    @Test
    public void fromJsonCollection(){
        Assert.assertEquals(messageCollection,
                Arrays.asList(JsonHelper.fromJson(collectionString, Message[].class).orElseThrow())
        );
    }

}