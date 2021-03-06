package com.cmd.parser.providers;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CommandPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("acl-parser: -> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }


}
