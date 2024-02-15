package org.example.config.textXPath;

import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class TextXPath {
    private final XML xml;
    private final String node;
    @Override
    public String toString() {
        List<XML> nodes = this.xml.nodes(this.node);
        if (nodes.isEmpty()) {
            return null;
        } else {
            return nodes.get(0).xpath("text()").get(0);
        }
    }
}
