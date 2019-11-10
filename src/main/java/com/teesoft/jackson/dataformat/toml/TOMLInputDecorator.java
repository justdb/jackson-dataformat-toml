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

import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.InputDecorator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

/**
 *
 * @author Wind Li
 */
public class TOMLInputDecorator extends InputDecorator {

    @Override
    public InputStream decorate(IOContext ctxt, InputStream in) throws IOException {
        TomlParseResult result = Toml.parse(in);
        in.close();
        return new ByteArrayInputStream(result.toJson().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public InputStream decorate(IOContext ctxt, byte[] src, int offset, int length) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(src, offset, length);
        return decorate(ctxt, in);
    }

    @Override
    public Reader decorate(IOContext ctxt, Reader r) throws IOException {
        TomlParseResult result = Toml.parse(r);
        r.close();
        return new java.io.StringReader(result.toJson());
    }

}
