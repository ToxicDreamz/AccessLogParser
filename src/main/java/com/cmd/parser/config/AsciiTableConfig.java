package com.cmd.parser.config;

import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.cmd.parser.models.AccessLogComments;
import com.cmd.parser.models.command.ParseParameters;
import de.vandermeer.asciitable.*;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiColors;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AsciiTableConfig extends AsciiTable {

    private AsciiTable asciiTable;

    public AsciiTableConfig() {
        asciiTable = new AsciiTable();
    }

    public void configureTable() {
        asciiTable.getContext().setFrameTopMargin(2);
        asciiTable.getContext().setFrameLeftMargin(50);
        asciiTable.addRule();
        AT_Row headerRow = asciiTable.addRow("IP Address", "Date Interval", "Reason");
        headerRow.setTextAlignment(TextAlignment.CENTER);
        asciiTable.addRule();
    }

    public void displayAccessLogComments(List<AccessLogComments> comments, ParseParameters parseParameters) {
        comments.forEach(comment -> {
            AT_Row row = asciiTable.addRow(comment.getIp(),
                    (parseParameters.getStartDate() + " \n " + parseParameters.getEndDate()),
                    comment.getComment());
            row.setTextAlignment(TextAlignment.CENTER);
            asciiTable.addRule();
        });

        asciiTable.getRenderer().setCWC(new CWC_LongestWordMin(new int[]{-1, 30, 90}));
        System.out.println(asciiTable.render());
    }
}
