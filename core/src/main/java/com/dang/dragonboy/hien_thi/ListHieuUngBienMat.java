package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;

public class ListHieuUngBienMat {

    private final ArrayList<HieuUngBienMat> list = new ArrayList<>();
    private Texture[] frames;
    private SpriteBatch batch = null;

    public ListHieuUngBienMat(Texture[] frames) {
        this.frames = frames;
    }

    /** Spawn hiệu ứng tại (x, y) với kích thước vùng snap */
    public void them(float x, float y, float rong, float cao, float delay) {
        list.add(new HieuUngBienMat(x, y, rong, cao, delay));
    }

    /** Gọi duy nhất 1 lần trong render() - tự vẽ + cập nhật + dọn rác */
    public void ve(SpriteBatch batch) {
        if (this.batch == null) this.batch = batch;
        Iterator<HieuUngBienMat> it = list.iterator();
        while (it.hasNext()) {
            HieuUngBienMat hu = it.next();
            hu.ve(this.batch, frames, Gdx.graphics.getDeltaTime());
            if (hu.daKetThuc) it.remove();
        }
    }
}

class HieuUngBienMat {
    public boolean daKetThuc;

    private float x, y, rong, cao, timer, delay;

    private static final float TIME_MAX = 0.3f;
    private static final float SCALE    = 0.45f;
    private static final int   SO_FRAME = 3;

    HieuUngBienMat(float x, float y, float rong, float cao, float delay) {
        this.x    = x;
        this.y    = y;
        this.rong = rong;
        this.cao  = cao;
        this.timer = TIME_MAX;
        this.delay = delay;
    }

    void ve(SpriteBatch batch, Texture[] frames, float delta) {
        if (daKetThuc) return;

        // Đang trong delay, chưa vẽ chưa trừ timer
        if (delay > 0) {
            delay -= delta;
            return;
        }

        float step = TIME_MAX / SO_FRAME;
        for (int i = 0; i < SO_FRAME; i++) {
            float start = TIME_MAX - i * step;
            float end   = TIME_MAX - (i + 1) * step;
            if (timer >= end && timer <= start) {
                Texture frame = frames[i];
                float w = frame.getWidth()  * SCALE;
                float h = frame.getHeight() * SCALE;
                batch.draw(frame, x + (rong - w) / 2f, y + (cao - h) / 2f, w, h);
                break;
            }
        }

        timer -= delta;
        if (timer <= 0) daKetThuc = true;
    }
}
