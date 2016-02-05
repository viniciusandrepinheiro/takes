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
package org.takes.rs;

import com.google.common.base.Joiner;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link RsWithType}.
 * @author Yohann Ferreira (yohann.ferreira@orange.fr)
 * @version $Id$
 * @since 0.16.9
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class RsWithTypeTest {

    /**
     * Carriage return constant.
     */
    private static final String CRLF = "\r\n";

    /**
     * Content type text/html.
     */
    private static final String TYPE_HTML = "text/html";

    /**
     * Content type text/xml.
     */
    private static final String TYPE_XML = "text/xml";

    /**
     * Content type text/plain.
     */
    private static final String TYPE_TEXT = "text/plain";

    /**
     * Content type application/json.
     */
    private static final String TYPE_JSON = "application/json";

    /**
     * HTTP Status OK.
     */
    private static final String HTTP_OK = "HTTP/1.1 200 OK";

    /**
     * Content-Type format.
     */
    private static final String CONTENT_TYPE = "Content-Type: %s";

    /**
     * Content-Type format with charset.
     */
    private static final String CONTENT_TYPE_WITH_CHARSET =
        "Content-Type: %s; charset=%s";

    /**
     * RsWithType can replace an existing type.
     * @throws Exception If a problem occurs.
     */
    @Test
    public void replaceTypeToResponse() throws Exception {
        final String type = TYPE_TEXT;
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType(new RsWithType(new RsEmpty(), TYPE_XML), type)
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(CONTENT_TYPE, type),
                    "",
                    ""
                )
            )
        );
    }

    /**
     * RsWithType does not replace response code.
     * @throws Exception If a problem occurs.
     */
    @Test
    public void doesNotReplaceResponseCode() throws Exception {
        final String body = "Error!";
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType(
                    new RsWithBody(
                        new RsWithStatus(HttpURLConnection.HTTP_INTERNAL_ERROR),
                        body
                    ),
                    TYPE_HTML
                )
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    "HTTP/1.1 500 Internal Error",
                    "Content-Length: 6",
                    "Content-Type: text/html",
                    "",
                    body
                )
            )
        );
    }

    /**
     * RsWithType.HTML can replace an existing type with text/html.
     * @throws Exception If a problem occurs.
     */
    @Test
    public void replacesTypeWithHtml() throws Exception {
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType.HTML(new RsWithType(new RsEmpty(), TYPE_XML))
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(CONTENT_TYPE, TYPE_HTML),
                    "",
                    ""
                )
            )
        );
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType.HTML(
                    new RsWithType(new RsEmpty(), TYPE_XML),
                    StandardCharsets.ISO_8859_1
                )
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(
                        CONTENT_TYPE_WITH_CHARSET,
                        TYPE_HTML,
                        StandardCharsets.ISO_8859_1
                    ),
                    "",
                    ""
                )
            )
        );
    }

    /**
     * RsWithType.JSON can replace an existing type with application/json.
     * @throws Exception If a problem occurs.
     */
    @Test
    public void replacesTypeWithJson() throws Exception {
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType.JSON(new RsWithType(new RsEmpty(), TYPE_XML))
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(CONTENT_TYPE, TYPE_JSON),
                    "",
                    ""
                )
            )
        );
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType.JSON(
                    new RsWithType(new RsEmpty(), TYPE_XML),
                    StandardCharsets.ISO_8859_1
                )
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(
                        CONTENT_TYPE_WITH_CHARSET,
                        TYPE_JSON,
                        StandardCharsets.ISO_8859_1
                    ),
                    "",
                    ""
                )
            )
        );
    }

    /**
     * RsWithType.XML can replace an existing type with text/xml.
     * @throws Exception If a problem occurs.
     */
    @Test
    public void replacesTypeWithXml() throws Exception {
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType.XML(new RsWithType(new RsEmpty(), TYPE_HTML))
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(CONTENT_TYPE, TYPE_XML),
                    "",
                    ""
                )
            )
        );
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType.XML(
                    new RsWithType(new RsEmpty(), TYPE_HTML),
                    StandardCharsets.ISO_8859_1
                )
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(
                        CONTENT_TYPE_WITH_CHARSET,
                        TYPE_XML,
                        StandardCharsets.ISO_8859_1
                    ),
                    "",
                    ""
                )
            )
        );
    }

    /**
     * RsWithType.Text can replace an existing type with text/plain.
     * @throws Exception If a problem occurs.
     */
    @Test
    public void replacesTypeWithText() throws Exception {
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType.Text(new RsWithType(new RsEmpty(), TYPE_HTML))
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(CONTENT_TYPE, TYPE_TEXT),
                    "",
                    ""
                )
            )
        );
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType.Text(
                    new RsWithType(new RsEmpty(), TYPE_HTML),
                    StandardCharsets.ISO_8859_1
                )
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(
                        CONTENT_TYPE_WITH_CHARSET,
                        TYPE_TEXT,
                        StandardCharsets.ISO_8859_1
                    ),
                    "",
                    ""
                )
            )
        );
    }

    /**
     * RsWithType can add the charset to the content type when it is explicitly
     * specified and can still provide the content type without the charset
     * when it is not explicitly specified.
     * @throws Exception If a problem occurs.
     */
    @Test
    public void addsCharsetToContentTypeIfNeeded() throws Exception {
        MatcherAssert.assertThat(
            new RsPrint(
                new RsWithType(
                    new RsEmpty(), TYPE_TEXT, StandardCharsets.ISO_8859_1
                )
            ).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(
                        CONTENT_TYPE_WITH_CHARSET,
                        TYPE_TEXT,
                        StandardCharsets.ISO_8859_1
                    ),
                    "",
                    ""
                )
            )
        );
        MatcherAssert.assertThat(
            new RsPrint(new RsWithType(new RsEmpty(), TYPE_TEXT)).print(),
            Matchers.equalTo(
                Joiner.on(CRLF).join(
                    HTTP_OK,
                    String.format(CONTENT_TYPE, TYPE_TEXT),
                    "",
                    ""
                )
            )
        );
    }
}
