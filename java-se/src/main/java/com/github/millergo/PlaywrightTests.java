package com.github.millergo;

import com.microsoft.playwright.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * /Applications/Google Chrome.app/Contents/MacOS/Google Chrome --flag-switches-begin --flag-switches-end --origin-trial-disabled-features=WebGPU
 * 可执行文件路径	/Applications/Google Chrome.app/Contents/MacOS/Google Chrome
 * 个人资料路径	/Users/miller/Library/Application Support/Google/Chrome/Default
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/6 20:43:07
 */
@DisabledOnOs(OS.WINDOWS)
@Tag(value = "UI")
@DisplayName(value = "Playwright测试端到端用例集")
public class PlaywrightTests {
    private final String url = "https://github.com/millergo";
    // 通过在浏览器输入：chrome://version/ 查看profilePath。注意: userDataDir 目录是 user-data-dir 目录的父目录
    private final String userDataDir = "/Users/miller/Library/Application Support/Google/Chrome/";

    @DisplayName("测试发布文章TesterHome")
    @Test
    public void testPublishToTesterHome() {
        // 启动浏览器之前需要先杀掉进程
        try (Playwright playwright = Playwright.create()) {
            // Given.
            // Playwright默认运行方式为浏览器无头模式，将浏览器设置为有头模式（如果不设置运行的时候浏览器不会弹出），并且设置执行速度降速
            BrowserType.LaunchPersistentContextOptions launchPersistentContextOptions = new BrowserType.LaunchPersistentContextOptions().setHeadless(false).setSlowMo(200);
            // 设置启动指定浏览器
            BrowserContext context = playwright.chromium().launchPersistentContext(Path.of(userDataDir), launchPersistentContextOptions);
            // 启用追踪功能，这样可以在运行自动化脚本之后查看整个执行的过程
            context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
            Page page = context.newPage();
            page.navigate(url);


            // When.


            // Then

            // 暂停启动调试、录制模式
            page.pause();

            context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
        }
    }
}
