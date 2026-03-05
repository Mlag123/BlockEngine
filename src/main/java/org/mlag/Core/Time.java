package org.mlag.Core;

public class Time {
    private long lastFrameNanos = System.nanoTime();
    private long lastFpsNanos = System.nanoTime();
    private int frames = 0;
    private int fps = 0;
    private float deltaTime = 0.0f;
    private final float maxDelta;

    public Time() {
        this(0.1f);
    }

    public Time(float maxDelta) {
        this.maxDelta = maxDelta;
    }

    public void update() {
        long now = System.nanoTime();
        deltaTime = (now - lastFrameNanos) / 1_000_000_000.0f;
        lastFrameNanos = now;

        if (deltaTime > maxDelta) {
            deltaTime = maxDelta;
        }

        frames++;
        if (now - lastFpsNanos >= 1_000_000_000L) {
            fps = frames;
            frames = 0;
            lastFpsNanos = now;
        }
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public int getFps() {
        return fps;
    }
}
