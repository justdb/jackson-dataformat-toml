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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 *
 * @author Wind Li
 */
public class TOMLFactory extends MappingJsonFactory {

    public TOMLFactory() {
        _inputDecorator = new TOMLInputDecorator();
        _outputDecorator = new TOMLOutputDecorator();
    }

    public TOMLFactory(ObjectMapper mapper) {
        super(mapper);
    }

    public TOMLFactory(JsonFactory src, ObjectMapper mapper) {
        super(src, mapper);
    }

    @Override
    public MatchStrength hasFormat(InputAccessor acc) throws IOException {
        return super.hasFormat(acc); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    @Override
    public String getFormatName() {
        return TOMLFormatSchema.TOML;
    }

}
