package com.cmd.parser.config;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.springframework.stereotype.Component;

@Component
public class AsciiProgressBarConfig {

    private ProgressBarBuilder progressBarBuilder;
    private ProgressBar progressBar;

    // TODO: Finish adding a progressBar

    public AsciiProgressBarConfig() {
        progressBarBuilder = new ProgressBarBuilder();
    }

    public void readerProgressBar() {
        this.progressBarBuilder.setTaskName("Reading Data")
                .setInitialMax(100)
                .setStyle(ProgressBarStyle.COLORFUL_UNICODE_BLOCK)
                .showSpeed()
                .build();
    }
}
