/*
 * Copyright 2016 higherfrequencytrading.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.openhft.chronicle.wire;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.core.io.IORuntimeException;
import net.openhft.chronicle.core.util.ReadResolvable;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnumTest {

    @Test
    public void testEnum() {
        TextWire wire = new TextWire(Bytes.elasticByteBuffer());
        wire.write("test")
                .object(TestEnum.INSTANCE);
        assertEquals("test: !net.openhft.chronicle.wire.EnumTest$TestEnum INSTANCE\n", wire.toString());
        TextWire wire2 = new TextWire(Bytes.from("test: !net.openhft.chronicle.wire.EnumTest$TestEnum {\n" +
                "}\n"));
        Object enumObject = wire2.read(() -> "test")
                .object();
        Assert.assertTrue(enumObject == TestEnum.INSTANCE);
    }

    public enum TestEnum implements Marshallable, ReadResolvable<TestEnum> {
        INSTANCE;

        @Override
        public void readMarshallable(@NotNull WireIn wire) throws IORuntimeException {
        }

        @Override
        public void writeMarshallable(@NotNull WireOut wire) {
        }

        @Override
        public EnumTest.TestEnum readResolve() {
            return INSTANCE;
        }
    }
}