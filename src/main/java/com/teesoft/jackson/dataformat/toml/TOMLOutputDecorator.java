/*
 * Copyright 2019 FasterXML.
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

import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.OutputDecorator;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Wind Li
 */
public class TOMLOutputDecorator extends OutputDecorator {

    private static TOML2JSON tomlInterface = null;

    @Override
    public OutputStream decorate(IOContext ctxt, final OutputStream out) throws IOException {
        return new java.io.ByteArrayOutputStream() {
            @Override
            public void close() throws IOException {
                super.close();
                String json = new String(this.toByteArray(),StandardCharsets.UTF_8);
                try {
                    String toml = getTomlInterface().jsonToToml(json);
                    out.write(toml.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    out.close();
                } catch (ScriptException ex) {
                    throw new IOException(ex.getMessage(),ex);
                }
            }
        };
    }

    @Override
    public Writer decorate(IOContext ctxt, final Writer w) throws IOException {
    return new java.io.StringWriter() {
            @Override
            public void close() throws IOException {
                super.close();
                String json = this.toString();
                try {
                    String toml = getTomlInterface().jsonToToml(json);
                    w.write(toml);
                    w.close();
                } catch (ScriptException ex) {
                    throw new IOException(ex.getMessage(),ex);
                }
            }
        };
    }

    public static TOML2JSON getTomlInterface() throws IOException, ScriptException {
        if (tomlInterface == null) {
            tomlInterface = createTOML2SJONInterface();
        }
        return tomlInterface;
    }

    public static TOML2JSON createTOML2SJONInterface() throws IOException, ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");//Nashorn
        engine.eval(
                "var GLOBAL;"
                + "var global;"
                + "(function(thisIsGlobal){"
                + "GLOBAL=thisIsGlobal;"
                + "global=thisIsGlobal;"
                + "})(this)"
        );
        // evaluate script
        try (Reader reader = new java.io.InputStreamReader(TOMLOutputDecorator.class.getResourceAsStream("/toml.js"))) {
            engine.eval(reader);
        }
        // javax.script.Invocable is an optional interface.
        // Check whether your script engine implements or not!
        // Note that the JavaScript engine implements Invocable interface.
        Invocable inv = (Invocable) engine;
        TOML2JSON toml = inv.getInterface(TOML2JSON.class);
        return toml;
    }

    public interface TOML2JSON {

        public String jsonToToml(String json);

        public String tomlToJson(String toml, int space);

    }
}
