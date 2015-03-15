/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.takes.tk;

import com.google.common.base.Joiner;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.takes.rs.RsPrint;

/**
 * Test case for {@link org.takes.tk.TkWithHeaders}.
 * @author Yegor Bugayenko (yegor@teamed.io)
 * @version $Id$
 * @since 0.1
 */
public final class TkWithHeadersTest {

    /**
     * TkWithHeaders can add headers.
     * @throws java.io.IOException If some problem inside
     */
    @Test
    public void testAddHeadersToTake() throws IOException {
        MatcherAssert.assertThat(
            new RsPrint(
                new TkWithHeaders(
                    new TkWithHeaders(
                        new TkEmpty()
                    ),
                    "Host: www.example.com ",
                    "Content-Type: text/xml "
                )
                    .with("host", "a.example.com")
                    .act()
            )
                .print(),
            Matchers.equalTo(
                Joiner.on("\r\n").join(
                    "HTTP/1.1 200 OK",
                    "Host: www.example.com",
                    "Content-Type: text/xml",
                    "host: a.example.com",
                    "",
                    ""
                )
            )
        );
    }
}
