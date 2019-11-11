/*
 * Copyright 2019 Wind Li.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.teesoft.jackson.dataformat.toml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TOMLMapperTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TOMLMapperTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TOMLMapperTest.class);
    }

    /**
     * Test toml
     */
    public void testToml() throws JsonProcessingException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        TOMLMapper mapper = new TOMLMapper();
        MyValue value = jsonMapper.readValue("{\"name\":\"Bob\", \"age\":13,\"subvalue\":{\"name\":\"justdb\", \"age\":1}}", MyValue.class);
        String toml = mapper.writeValueAsString(value);
        System.out.println(toml);
        assertEquals(toml,
                "name = \"Bob\"\n"
                + "age = 13\n"
                + "\n"
                + "[subvalue]\n"
                + "name = \"justdb\"\n"
                + "age = 1\n");
        MyValue valueNew = mapper.readValue(toml, MyValue.class);
        String toml2 = mapper.writeValueAsString(valueNew);
        assertEquals(toml, toml2);
        assertEquals(value.name, "Bob");
        assertEquals(value.age, 13);
        assertEquals(valueNew.name, "Bob");
        assertEquals(valueNew.age, 13);
        mapper.writeValue(new File("target/test.toml"), value);
        MyValue valueFile = mapper.readValue(new File("target/test.toml"), MyValue.class);
        String toml3 = mapper.writeValueAsString(valueFile);
        assertEquals(toml, toml3);
        JsonNode jsonNode = mapper.readTree(toml);
        String toml4 = mapper.writeValueAsString(jsonNode);
        MyValue valueNew4 = mapper.readValue(toml4, MyValue.class);
        String toml5 = mapper.writeValueAsString(valueNew4);
        assertEquals(toml, toml5);

    }

    public static class MyValue {

        public String name;
        public int age;
        public SubValue subvalue;
    }

    public static class SubValue {

        public String name;
        public int age;
    }
}
