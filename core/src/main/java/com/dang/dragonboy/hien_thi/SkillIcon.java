package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.graphics.Texture;

public class SkillIcon {
    public Texture icon;

    public SkillIcon(String path) {
        this.icon = new Texture(path);
    }

    public void dispose() {
        if (icon != null) icon.dispose();
    }
}
