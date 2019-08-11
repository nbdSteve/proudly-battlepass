package dev.nuer.proudly.utils;

public class ProgressBarUtil {

    public static String bar(double current, double total) {
        double percent = (current / total) * 50;
        StringBuilder bar = new StringBuilder();
        for (int i = 1; i <= 50; i++) {
            if (percent < 0) {
                bar.append(ColorUtil.colorize("&a|"));
            } else {
                if (i <= percent) {
                    bar.append(ColorUtil.colorize("&a|"));
                } else {
                    bar.append(ColorUtil.colorize("&c|"));
                }
            }
        }
        return bar.toString();
    }
}